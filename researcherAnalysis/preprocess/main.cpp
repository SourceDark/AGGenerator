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
int score[1000], edge[1000][1000], group[1000];
string namevalue[1000];
vector<string> paperlist[1000];
vector<string> papers[1000][1000];

int main()
{
    freopen("data.in", "r", stdin);
    freopen("data.out", "w", stdout);
    int line = 0, type;
    int src = 6, id = 0;
    bool article = false;
    string name, art;
    do {
        name = getNext(type);
        if (!article) {
            art = name;
            article = true;
            continue;
        }
        //cerr << name << ' ';
        if (name.size() > 0) {
            //cerr << namelist[name] << endl;
            if (namelist[name] < 1) {
                namelist[name] = ++id;
                namevalue[id] = name;
            }
            score[ namelist[name] ] += src;
            string no = "[0]";
            no[1] += 7 - src;
            paperlist[ namelist[name] ].push_back( no + art);
            for (int i = 0; i != (int) thisPaper.size(); ++i) {
                edge[namelist[name]][namelist[thisPaper[i]]] ++, edge[namelist[thisPaper[i]]][namelist[name]] ++;
                papers[namelist[name]][namelist[thisPaper[i]]].push_back(art);
                papers[namelist[thisPaper[i]]][namelist[name]].push_back(art);
            }
        }
        if (type) {
            src = 6;
            thisPaper.clear();
            ++line;
            article = false;
        }   else {
            src --;
            thisPaper.push_back(name);
        }
    }   while (line < 193);
    cerr << "persons: " << id  << endl;
    printf("var data = {\n\"nodes\": [\n");
    for (int i = 1; i <= id; ++i) {
        if (score[i] > 10) group[i] = 2;
        if (score[i] > 15) group[i] = 4;
        if (score[i] > 25) group[i] = 5;
    }
    for (int i = 1; i <= id; ++i) {
        printf("\t{\"id\": \"%s\", \"group\": %d, size: %d, \"papers\": [", namevalue[i].c_str(), group[i], score[i]);
        for (int j = 0; j != (int) paperlist[i].size(); ++j)
            printf("\"%s\",", paperlist[i][j].c_str());
        printf("]},\n");
    }
        //cout << namevalue[i] << ' ' << score[i] << endl;
    printf("],\n\"links\": [\n");
    for (int i = 1; i <= id; ++i)
        for (int j = i + 1; j <= id; ++j)
            if (edge[i][j]) {
                printf("\t{\"source\": \"%s\", \"target\": \"%s\",\"group\": 0, \"value\": %d, \"papers\": [", namevalue[i].c_str(), namevalue[j].c_str(), edge[i][j]);
                for (int k = 0; k != (int) papers[i][j].size(); ++k)
                    printf("\"%s\",", papers[i][j][k].c_str());
                printf("]},\n");
            }
                //cout << namevalue[i] << ' ' << namevalue[j] << ' ' << edge[i][j] << endl;
    printf("]};\n");
    return 0;
}
