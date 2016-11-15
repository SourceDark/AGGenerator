#include <iostream>
#include <fstream>
#include <string>
#include <map>
using namespace std;


/**
 * Global Variables
 */
string scan_targets;
ofstream logger;

void close_program() {
	logger.close();
	exit(0);
}

/**
 * Configurations
 */
static string CONFIG_SCAN_TARGETS_FILE = "SCAN_TARGETS_FILE";
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
	stf.open(configs[CONFIG_SCAN_TARGETS_FILE]);
	stf >> scan_targets;
	stf.close();
	cout << scan_targets;
}

int main() {
	logger.open("scan.log");
	load_configs();
	load_scan_targets();
	close_program();
	logger.close();
}
