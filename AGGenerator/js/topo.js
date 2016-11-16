var topo_svg = d3.select("#topo_graph");
var width = $('.content').width();
var height = $('.content').height();
	topo_svg.attr("width", width);  
	topo_svg.attr("height", height);

var color = d3.scaleOrdinal(d3.schemeCategory20);
var topo_simulation = d3.forceSimulation()
    .force("link", d3.forceLink().id(function(d) { return d.id; }).distance(function(d) {return 200;}))
    .force("charge", d3.forceManyBody().strength(function(d) {return -500;}))
    .force("center", d3.forceCenter(width / 2, height / 2));
	
var topo_link = topo_svg.append("g")
	.attr("class", "links")
	.selectAll("line")
	.data(topoData.links)
	.enter().append("line")
	.attr("stroke-width", function(d) { return d.value; });	
	
var topo_node = topo_svg.append("g")
	.attr("class", "nodes")
	.selectAll("circle")
	.data(topoData.nodes)
	.enter().append("circle")
		.attr("r", function(d) { return d.size; })
		.attr("fill", function(d) { return color(d.group); })
		.call(d3.drag()
			.on("start", dragstarted)
			.on("drag", dragged)
			.on("end", dragended));
var topo_node_text = topo_svg.append("g")
	.attr("class", "nodes_text")
	.selectAll(".node_text")
	.data(topoData.nodes)
	.enter().append("text")
	.text(function(d) { return d.id; });

topo_node.append("title")
	.text(function(d) { return d.id; });

topo_simulation
	.nodes(topoData.nodes)
	.on("tick", ticked);

topo_simulation.force("link")
	.links(topoData.links);
	
function ticked() {
	topo_link
		.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });

	topo_node
		.attr("cx", function(d) { return d.x; })
		.attr("cy", function(d) { return d.y; });

	topo_node_text
		.attr("x", function(d) { return d.x; })
		.attr("y", function(d) { return d.y+d.size+15; });
}
  
function dragstarted(d) {
	if (!d3.event.active) topo_simulation.alphaTarget(0.3).restart();
	d.fx = d.x;
	d.fy = d.y;
}

function dragged(d) {
	d.fx = d3.event.x;
	d.fy = d3.event.y;
}

function dragended(d) {
	if (!d3.event.active) topo_simulation.alphaTarget(0);
	d.fx = null;
	d.fy = null;
}