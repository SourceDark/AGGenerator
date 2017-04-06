var rf=require("fs");  
var graph=rf.readFileSync("AttackGraph.txt","utf-8");
var nodeAndEdges = graph.split('\n');

var nodes = [], edges = [];

function addEdge(edgeString) {
	var edge = edgeString.split(',');
	edges.push({
		source: edge[1],
		target: edge[0]
	});
}

function getAttribute(nodeStrings) {
	var nodeString = nodeStrings[0];
	var attribute;
	if (nodeString.split(',').length === 1) {
		attribute = nodeString;
		nodeString = "";
	} else if(nodeString.startsWith('"')){
		attribute = nodeString.substr(1, nodeString.indexOf('",') - 1);
		nodeString = nodeString.substr(nodeString.indexOf('",') + 2);
	} else if(nodeString.startsWith("\'")) {
		attribute = nodeString.substr(1, nodeString.indexOf("',") - 1);
		nodeString = nodeString.substr(nodeString.indexOf("',") + 2);
	} else {
		attribute = nodeString.substr(0, nodeString.indexOf(","));
		nodeString = nodeString.substr(nodeString.indexOf(",") + 1);
	}
	nodeStrings[0] = nodeString;
	return attribute;
}

function addNode(nodeString) {
	var nodeStrings = [nodeString];
	nodes.push({
		id: getAttribute(nodeStrings),
		info: getAttribute(nodeStrings),
		type: getAttribute(nodeStrings),
		initial: getAttribute(nodeStrings),
	});
}

nodeAndEdges.forEach(function(nodeOrEdge) {
	if(nodeOrEdge.trim() == "") {
		return;
	}
	if(nodeOrEdge.split(',').length > 3) {
		addNode(nodeOrEdge);
	} else {
		addEdge(nodeOrEdge);
	}
});
// console.log(nodes.length);
// console.log(edges.length);
// console.log(JSON.stringify({
// 	nodes: nodes,
// 	edges: edges
// }));

console.log(nodes.length + " " + edges.length);

nodes.forEach(function(node) {
	console.log(node.id);
	console.log(node.info);
	console.log(node.type);
	console.log(node.initial);
});

edges.forEach(function(edge) {
	console.log(edge.target);
	console.log(edge.source);
});
