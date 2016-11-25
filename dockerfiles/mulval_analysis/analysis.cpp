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
	void output() {
		set<node *>::iterator it;
		for (it = nodes.begin(); it != nodes.end(); it ++) {
			cout << (*it) -> no;
			cout << ',';
		}
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
		cout << this -> size() << ' ' << pl.size() << endl;
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

PathList* search(node *t) {
	//cout << t -> no << endl;
	cout << t -> no << ' ' << t -> type << endl;
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
	if (pl -> size() > 0) {
		cout << "Routes" << endl;
		pl -> output();
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
