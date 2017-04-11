#include <cstring>
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <queue>
#include <map>
#include "include/rapidjson/document.h"
#include "include/rapidjson/writer.h"
#include "include/rapidjson/stringbuffer.h"
#include "include/rapidjson/pointer.h"
using namespace std;
using namespace rapidjson;

const int MAX_INF=1000000000;

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

void throw_error(string str) {
	ofstream erf(error_file);
	erf << str;
	erf.close();
	exit(0);
}

struct edge {
	int u, v, rst;
	edge *next, *rev;	
	edge(int u_, int v_, int rst_, edge *next_):u(u_), v(v_), rst(rst_), next(next_) {}
};

vector<edge *> link;
vector<int> last;
vector<edge *> road;

void ins(int u, int v, int e) {
	cout << u << ' ' << v << ' ' << e << endl;
	link[u] = new edge(u, v, e, link[u]);
	link[v] = new edge(v, u, 0, link[v]);
	link[u] -> rev = link[v]; link[v] -> rev = link[u];
}

void input() {
	// Read json-string from input
	ifstream inf(input_file);
	string jsonString, str;
	while (getline(inf, str)) jsonString += str;
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
	link = vector<edge *>(2 * n + 3, NULL);
	last = vector<int>(2 * n + 3);
	road = vector<edge *>(2 * n + 3);
	vector<int> poss(n, 0);
	
	for (int i = 0; i < n; i++) {
		const Value& dnode = dnodes[i];
		string nodeType = dnode["type"].GetString();
		if (nodeType == "AND") {
			poss[i] = 1;
		}
		else if (nodeType == "OR") {
			if (dnode["target"].GetBool() || i == 0) {
				ins(n + i, 2 * n + 2, MAX_INF);
				cout << "target" << endl;
			}
		}
		else if (nodeType == "LEAF") {
			if (dnode["attacker"].GetBool()) {
				ins(2 * n + 1, i, MAX_INF);
				cout << "attacker" << endl;
			}
		}
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
		int src = stoi<int>(string(dedge["source"].GetString()).c_str()) - 1;
		int dst = stoi<int>(string(dedge["target"].GetString()).c_str()) - 1;
		ins(n + src, dst, MAX_INF);
		if (poss[dst] >= 1) {
			const Value& dnode = dnodes[src];
			string nodeType = dnode["type"].GetString();
			if (nodeType == "LEAF") {
				poss[dst] ++;
			}
		}
	}
	cout << "Possible fix nodes:" << endl;
	for (int i = 0; i < n; i++) {
		if (poss[i] > 2) {
			ins(i, n + i, 1);	
			cout << i + 1 << endl;
		}
		else {
			ins(i, n + i, MAX_INF);
		}
	}
	cout << "Parsed " << n << " nodes and " << m << " edges" << endl;
	cout << endl;
}

bool bfs(int u, int v) {
	queue<int> Q;
	vector<bool> bo(2 * n + 3, false);
	Q.push(u);
	while (!Q.empty()) {
		int i = Q.front(); Q.pop();
		if (i == v) {
			cout << "Road!" << endl;
			return true;
		}
		for (edge *p = link[i]; p != NULL; p = p -> next) if (p -> rst > 0) {
			if (!bo[p -> v]) {
				bo[p -> v] = true;
				road[p -> v] = p;
				last[p -> v] = i;
				Q.push(p -> v);	
			}
		}
	}
	return false;
}

int ans = 0;

void improve(int u, int v) {
	int j = MAX_INF;
	for (int i = v; i != u; i = last[i]) {
		j = min(j, road[i] -> rst);	
		cout << road[i] -> u << ' ' << road[i] -> v << ' ' << road[i] -> rst << ' ' << last[i] << endl;
	}
	ans += j;
	for (int i = v; i != u; i = last[i]) {
		road[i] -> rst -= j;
		road[i] -> rev -> rst += j;
	}
}

void analysis() {
	ofstream ouf(output_file);
	while (bfs(2 * n + 1, 2 * n + 2)) improve(2 * n + 1, 2 * n + 2);
	cout << "Minimum Cut Size: " << ans << endl;
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
