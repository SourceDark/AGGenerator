#include <cstring>
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <map>
using namespace std;

int n, m;

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

class node {
public:
	int no;
	string des;
	string type;
	int value;
	edge *link;
	node() {
		link = NULL;
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
	Path() {}
	void push(node *t) {
		nodes.insert(t);
	}
	Path operator + (Path &p) {
		Path ret;
		ret.nodes.insert(nodes.begin(), nodes.end());
		ret.nodes.insert(p.nodes.begin(), p.nodes.end());
		return ret;
	}
	void output() {
		set<node *>::iterator it;
		for (it = nodes.begin(); it != nodes.end(); it ++) {
			cout << (*it) -> no;
			cout << ' ';
		}
	}
};

class PathList {
	vector<Path> paths;
public:
	PathList(int size = 0) {
		for (int i = 0; i < size; i++) {
			paths.push_back(Path());
		}
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
	PathList operator * (PathList &pl) {
		PathList ret;
		for (int i = 0; i < paths.size(); i++) {
			for (int j = 0; j < pl.size(); j++) {
				ret.push(paths[i] + pl[j]);
			}
		}
	}
	void output() {
		for (int i = 0; i < paths.size(); i++) {
			paths[i].output();
			cout << endl;
		}
	}
};

map<int, node> nodes;

void input() {
	ifstream fin("input");
	fin >> n >> m;
	for (int i = 1; i <= n; i++) {
		string str;
		node new_node;
		fin >> new_node.no;
		getline(fin, str);
		getline(fin, new_node.des);
		getline(fin, new_node.type);
		fin >> new_node.value;
		nodes[new_node.no] = new_node;
	}
	for (int i = 1; i <= m; i++) {
		int src, dst;
		fin >> src >> dst;
		nodes[src].link = new edge(&nodes[src], &nodes[dst], nodes[src].link);
	}
}

PathList search(node *t) {
	//cout << t -> no << endl;
	if (t -> type == "LEAF") {
		PathList pl;
		if (t -> value == 1) {
			Path p;
			p.push(t);
			pl.push(p);
		}
		return pl;
	}
	if (t -> type == "AND") {
		PathList pl(1);
		Path p;
		pl.push(p);
		for (edge *e = t -> link; e != NULL; e = e -> next) {
			PathList p2 = search(e -> dst);
			p1 = p1 * p2;
		}
		return p1;
	}
	if (t -> type == "OR") {
		PathList pl;
		bool valid = false;
		for (edge *p = t -> link; p != NULL; p = p -> next) {
			vector<string> s;
			if (search(p -> dst, s)) {
				for (int i = 0; i < s.size(); i++) steps.push_back(s[i]);
				valid = true;
				break;
			}
		}
		steps.push_back(itos<int>(t -> no));
		return valid;
	}
}

void analysis() {
	PathList pl = search(&nodes[59]);
	if (pl.size() > 0) {
		pl.output();
	}
	else {
		cout << "There is no attack path exists." << endl;
	}
}

int main() {
	input();
	analysis();
	return 0;
}
