#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <map>
#include <vector>
using namespace std;

/**
 * Global Variables
 */
string scan_targets;
string scan_type;
ofstream logger;

void close_program() {
	logger.close();
	exit(0);
}

/**
 * Configurations
 */
static string CONFIG_SCAN_CONFIGS_FILE = "SCAN_CONFIGS_FILE";
static string CONFIG_SCAN_RESULTS_FILE = "SCAN_RESULTS_FILE";

map<string, string> configs;
void load_configs() {
	ifstream conf; 
	conf.open("config.ini");
	char s[101];
	int line_number = 0;
	while (conf.getline(s, 100)) {
		line_number ++;
		string str(s);
		int i = str.find("=");
		if (i == string::npos) {
			logger << "Wrong config file at line " << line_number << ": " << str;
			close_program();
		}
		string key = str.substr(0, i);
		string value = str.substr(i + 1, str.length() - i - 1);
		configs[key] = value;
	}
	conf.close();
}

void load_scan_targets() {
	ifstream stf; 
	stf.open(configs[CONFIG_SCAN_CONFIGS_FILE].c_str());
	stf >> scan_targets;
	stf >> scan_type;
	stf.close();
}

void initial_program() {
	logger.open("scan.log");
	load_configs();
	load_scan_targets();
}

vector<string> analyze_light_probe_outputs() {
	vector<string> ips;
	ifstream nof("data/nmap.output");
	string str, cip;
	while (getline(nof, str)) {
		if (str.find("Nmap scan report for ") != string::npos) {
			string tmp = str.substr(21, str.length() - 1);
			stringstream ss;
			ss << tmp;
			ss >> cip;
		}
		if (str.find("Host is up") != string::npos) {
			ips.push_back(cip);	
		}
	}
	nof.close();
	return ips;
}

void main_work() {
	if (scan_type == "light") {
		string cmd = "nmap -v -sn " + scan_targets + " > data/nmap.output";
		system(cmd.c_str());
		vector<string> ips = analyze_light_probe_outputs();
		ofstream srf(configs[CONFIG_SCAN_RESULTS_FILE].c_str());
		srf << ips.size() << endl;
		for (int i = 0; i < ips.size(); i++) srf << ips[i] << endl;
		close_program(); 
	}
	if (scan_type == "deep") {
		
	}
}

int main() {
	initial_program();
	main_work();
	close_program();
}
