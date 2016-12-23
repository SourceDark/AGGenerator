#include <cstring>
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <map>
#include "include/rapidjson/document.h"
#include "include/rapidjson/writer.h"
#include "include/rapidjson/stringbuffer.h"
#include "include/rapidjson/pointer.h"
using namespace std;
using namespace rapidjson;

char *input_file;
char *output_file;
char *error_file;

void setFiles() {
		
}


int n, m;

template<class T> T stoi(const char *str) {
	T ret = 0;
	for (int i = 0; i < strlen(str); i++) {
		ret = ret * 10 + str[i] - '0';
	}
	return ret; 
}

template<class T> string itos(T i) {
	string ret = "";
	bool neg = false;
	if (i == 0) return "0";
	if (i < 0) {
		neg = true;
		i = -i;
	}
	while (i > 0) {
		ret = (char)('0' + (i % 10)) + ret;
		i = i / 10;
	}
	if (neg) ret = '-' + ret;
	return ret;
}

class edge;
class PathList;

class node {
public:
	int no;
	string des;
	string type;
	int value;
	edge *link;
	int status; // 0:none, 1:visiting, 2:visited
	PathList *pl;
	
	node() {
		link = NULL;
		status = 0;
		pl = NULL;
	} 
	string toString() {
		string str = "";
		str += itos<int>(no);
		str += "," + des;
		str += "," + type;
		str += "," + itos<int>(value);	
		return str;
	}
};

class edge {
public:
	node *src;
	node *dst;
	edge *next;
	edge(node *src_, node *dst_, edge *next_):src(src_), dst(dst_), next(next_) {}
};

class Path {
public:
	set<node *> nodes;
	Path(int size = 0, node *t = NULL) {
		if (size > 0) nodes.insert(t);
	}
	void push(node *t) {
		nodes.insert(t);
	}
	Path operator + (Path p) {
		Path ret;
		ret.nodes.insert(nodes.begin(), nodes.end());
		ret.nodes.insert(p.nodes.begin(), p.nodes.end());
		return ret;
	}
	void output(ofstream &ouf) {
		set<node *>::iterator it;
		ouf << '[';
		for (it = nodes.begin(); it != nodes.end(); it ++) {
		    if (it != nodes.begin()) ouf << ',';
			ouf << (*it) -> no;
		}
		ouf << ']';
	}
};

class PathList {
public:
	vector<Path> paths;
	PathList(int size = 0, int ps = 0, node *t = NULL) {
		paths = vector<Path>();
		for (int i = 0; i < size; i++) {
			paths.push_back(Path(ps, t));
		}
	}
	PathList(const PathList &pl) {
		paths = pl.paths;
	}
	unsigned int size() {
		return paths.size();
	}
	void push(Path p) {
		paths.push_back(p);
	}
	Path operator [] (int i) {
		return paths[i];
	}
	PathList operator * (PathList pl) {
		//cout << this -> size() << ' ' << pl.size() << endl;
		PathList ret;
		for (int i = 0; i < paths.size(); i++) {
			for (int j = 0; j < pl.size(); j++) {
				ret.push(paths[i] + pl[j]);
			}
		}
		return ret;
	}
	PathList operator + (PathList pl) {
		PathList ret;
		for (int i = 0; i < paths.size(); i++) ret.push(paths[i]);
		for (int i = 0; i < pl.size(); i++) ret.push(pl[i]);
		return ret;
	}
	void output(ofstream &ouf) {
	    ouf << '[';
		for (int i = 0; i < paths.size(); i++) {
			if (i > 0) ouf << ',';
			paths[i].output(ouf);
		}
		ouf << ']';
	}
};

map<int, node> nodes;

void throw_error(string str) {
	ofstream erf(error_file);
	erf << str;
	erf.close();
	exit(0);
}

void input() {
	// Read json-string from input
	ifstream inf(input_file);
	string jsonString;
	getline(inf, jsonString);
	inf.close();
		
	// Parse object from json-string
	Document d;
	d.Parse(jsonString.c_str());
	
	// Validate and transfer document
	if (!d.IsObject()) {
		throw_error("Input Invalid.");
	}
	if (!d.HasMember("nodes")) {
		throw_error("Input \"Nodes\" Not Found.");
	}
	if (!d["nodes"].IsArray()) {
		throw_error("Input \"Nodes\" Invalid.");
	}
	const Value& dnodes = d["nodes"];
	n = dnodes.Size();
	for (int i = 0; i < n; i++) {
		const Value& dnode = dnodes[i];
		node new_node;
		new_node.no = stoi<int>(string(dnode["id"].GetString()).c_str());
		new_node.des = dnode["info"].GetString();
		new_node.type = dnode["type"].GetString();
		new_node.value = stoi<int>(string(dnode["initial"].GetString()).c_str());
		nodes[new_node.no] = new_node;
	}
	if (!d.HasMember("edges")) {
		throw_error("Input \"Nodes\" Not Found.");
	}
	if (!d["edges"].IsArray()) {
		throw_error("Input \"Edges\" Invalid.");
	}
	const Value& dedges = d["edges"];
	m = dedges.Size();
	for (int i = 0; i < m; i++) {
		const Value& dedge = dedges[i];
		int src = stoi<int>(string(dedge["target"].GetString()).c_str());
		int dst = stoi<int>(string(dedge["source"].GetString()).c_str());
		nodes[src].link = new edge(&nodes[src], &nodes[dst], nodes[src].link);
	}
}

PathList* search(node *t) {
	//cout << t -> no << endl;
	//cout << t -> no << ' ' << t -> type << endl;
	if (t -> status == 2) {
		return t -> pl;
	}
	if (t -> status == 1) {
		return new PathList();
	}
	t -> status = 1;
	PathList *pl = new PathList();
	if (t -> type == "LEAF") {
		if (t -> value == 1) {
			pl = new PathList(1, 1, t);
		}
	}
	if (t -> type == "AND") {
		pl = new PathList(1, 1, t);
		for (edge *e = t -> link; e != NULL; e = e -> next) {
			PathList *pl2 = search(e -> dst);
			*pl = *pl * *pl2;
		}
	}
	if (t -> type == "OR") {
		PathList tmp = PathList(1, 1, t);
		for (edge *e = t -> link; e != NULL; e = e -> next) {
			PathList *pl2 = search(e -> dst);
			*pl = *pl + (tmp * *pl2);
		}
	}
	t -> pl = pl;
	t -> status = 2;
	return t -> pl;
}

void analysis() {
	PathList *pl = search(&nodes[1]);
	ofstream ouf(output_file);
	pl -> output(ouf);
	ouf.close();
}

int main(int argc, char *argv[]) {
	input_file = argv[1];
	output_file = argv[2];
	error_file = argv[3];
	input();
	analysis();
	return 0;
}
