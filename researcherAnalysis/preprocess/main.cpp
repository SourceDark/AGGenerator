#include <iostream>
#include <stdio.h>
#include <string.h>
#include <map>
#include <vector>
using namespace std;

string getNext(int &type) {
    string ret = "";
    char c;
    do {
        scanf("%c", &c);
        if (c != ',' && c != '\n') ret += c;
    }   while (c != ',' && c != '\n');
    type = c == ',' ? 0 : 1;
    return ret;
}

vector<string> thisPaper;
map<string,int> namelist;
int score[50], edge[50][50], group[50];
string namevalue[50];

int main()
{
    freopen("data.in", "r", stdin);
    freopen("data.out", "w", stdout);
    int line = 0, type;
    int src = 6, id = 0;
    string name;
    do {
        name = getNext(type);
        //cerr << name << ' ';
        if (name.size() > 0) {
            cerr << namelist[name] << endl;
            if (namelist[name] < 1) {
                namelist[name] = ++id;
                namevalue[id] = name;
            }
            score[ namelist[name] ] += src;
            for (int i = 0; i != (int) thisPaper.size(); ++i)
                edge[namelist[name]][namelist[thisPaper[i]]] ++, edge[namelist[thisPaper[i]]][namelist[name]] ++;
        }
        if (type) {
            src = 6;
            thisPaper.clear();
            ++line;
        }   else {
            src --;
            thisPaper.push_back(name);
        }
    }   while (line < 26);
    cerr << "persons: " << id  << endl;
    printf("var data = {\n\"nodes\": [\n");
    for (int i = 1; i <= id; ++i) {
        if (score[i] > 10) group[i] = 2;
        if (score[i] > 15) group[i] = 4;
        if (score[i] > 25) group[i] = 5;
    }
    for (int i = 1; i <= id; ++i)
        printf("\t{\"id\": \"%s\", \"group\": %d, size: %d},\n", namevalue[i].c_str(), group[i], score[i]);
        //cout << namevalue[i] << ' ' << score[i] << endl;
    printf("],\"links\": [\n");
    for (int i = 1; i <= id; ++i)
        for (int j = i + 1; j <= id; ++j)
            if (edge[i][j])
                printf("{\"source\": \"%s\", \"target\": \"%s\",\"group\": 0, \"value\": %d},\n", namevalue[i].c_str(), namevalue[j].c_str(), edge[i][j]);
                //cout << namevalue[i] << ' ' << namevalue[j] << ' ' << edge[i][j] << endl;
    printf("]};\n");
    return 0;
}
