#include <cstring>
#include <iostream>
#include <fstream>
#include <vector>
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
	edge(node *src_, node *dst_, edge *next_):src(src_), dst(dst_), next(next_) {
		//cout << src -> no << ' ' << dst -> no << endl;
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

bool search(node *t, vector<string> &steps) {
	//cout << t -> no << endl;
	if (t -> type == "LEAF") {
		if (t -> value == 1) {
			//steps.push_back(itos<int>(t -> no) + ":" + t -> des);
			//steps.push_back("\twas initially accquired.");
			steps.push_back(itos<int>(t -> no));
			return true;
		}
		else {
			return false;
		}
	}
	if (t -> type == "AND") {
		for (edge *p = t -> link; p != NULL; p = p -> next) {
			if (!search(p -> dst, steps)) {
				return false;
			}
		}
		steps.push_back(itos<int>(t -> no));
		return true;
	}
	if (t -> type == "OR") {
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
	vector<string> steps;
	if (search(&nodes[1], steps)) {
		for (int i = 0; i < steps.size(); i++) cout << steps[i] << endl;
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
