angular.module('myApp', [])
	.controller('mainCtrl', ['$scope', function($scope) {
		$scope.nodes = attackGraphData.nodes;
		$scope.links = attackGraphData.edges;
	}]).directive('attackGraph', function () {
		return {
			restrict: 'E',
			replace: true,
			transclude: true,
			scope: {
				nodes: '=nodes',
				links: '=links'
			},
			template: '<svg id="attack-graph"></svg>',
			link: function (scope, element, attrs) {
				scope.r = 30;
				scope.svg = d3.select('#attack-graph');

				var height = scope.height ? scope.height : window.innerHeight;
				var width = scope.width ? scope.width : window.innerWidth;

				scope.simulation = d3.forceSimulation()
					.force("link", d3.forceLink().id(function(d) { return d.id; }).distance(function(d) {return 150;}))
					.force("charge", d3.forceManyBody().strength(function(d) {return -300;}))
					.force("center", d3.forceCenter(width / 2, height / 2));

				var svg = d3.select("body").append("svg")
					.attr("width", width)
					.attr("height", height);

				var defs = scope.svg.append("defs");

				var arrowMarker = defs.append("marker")
					.attr("id","arrow")
					.attr("markerUnits","strokeWidth")
					.attr("markerWidth","12")
					.attr("markerHeight","12")
					.attr("viewBox","0 0 12 12")
					.attr("refX","25")
					.attr("refY","6")
					.attr("orient","auto");

				var arrow_path = "M2,2 L10,6 L2,10 L6,6 L2,2";

				arrowMarker.append("path")
					.attr("d",arrow_path)
					.attr("fill","#000");

				scope.getColor = function (d) {
					if (d.id == 1) return '#FF3030';
					if (d.type == 'OR') return '#EEEE00';
					if (d.type == 'AND') return '#66ccff';
					return '#7CCD7C';
				};

				scope.link = scope.svg.append("g")
					.attr("class", "links")
					.selectAll("line")
					.data(scope.links)
					.enter().append("line")
					.attr("stroke","black")
					.attr("stroke-width", 2)
					.attr("marker-end","url(#arrow)");

				scope.node_g = scope.svg.append("g")
					.attr("class", "nodes")
					.attr("stroke","black")
					.attr("stroke-width",2);

				scope.typeOP = scope.node_g
					.selectAll("circle")
					.data(scope.nodes)
					.enter().filter(function (d) {
						return !(d.type == "LEAF");
					})
					.append("circle")
					.attr('class', 'node')
					.attr("r", function(d) { return scope.r; })
					.attr("fill", function(d) { return scope.getColor(d); })
					.on('click', function(d,i) {
						if (d3.select(this).attr("stroke") == "red")
							d3.selectAll('.node').attr("stroke","black");
						else {
							d3.selectAll('.node').attr("stroke","black");
							d3.select(this).attr("stroke", "red");
						}
					}).call(d3.drag()
						.on("start", dragstarted)
						.on("drag", dragged)
						.on("end", dragended));

				scope.typeLEAF = scope.node_g
					.selectAll("rect")
					.data(scope.nodes)
					.enter().filter(function (d) {
						return d.type == "LEAF"
					})
					.append("rect")
					.attr('class', 'node')
					.attr("width", function(d) { return scope.r*2; })
					.attr("height", function(d) { return scope.r*2; })
					.attr("fill", function(d) { return scope.getColor(d); })
					.on('click', function(d,i) {
						if (d3.select(this).attr("stroke") == "red")
							d3.selectAll('.node').attr("stroke","black");
						else {
							d3.selectAll('.node').attr("stroke","black");
							d3.select(this).attr("stroke", "red");
						}
					}).call(d3.drag()
						.on("start", dragstarted)
						.on("drag", dragged)
						.on("end", dragended));

				scope.opText = scope.svg.append('g')
					.attr('class', 'info')
					.selectAll("circle")
					.data(scope.nodes)
					.enter().filter(function (d) {
						return !(d.type == "LEAF");
					})
					.append("text")
					.attr('class', 'type_info')
					.text(function (d) {
						return d.type;
					});

				scope.simulation
					.nodes(scope.nodes)
					.on("tick", ticked);

				scope.simulation.force("link")
					.links(scope.links);

				function ticked() {
					 scope.link
					 	.attr("x1", function(d) { return d.source.x; })
					 	.attr("y1", function(d) { return d.source.y; })
					 	.attr("x2", function(d) { return d.target.x; })
					 	.attr("y2", function(d) { return d.target.y; });

					 scope.typeLEAF
						 .attr('x', function (d) {return d.x - scope.r})
						 .attr('y', function (d) {return d.y - scope.r});

					 scope.typeOP
						 .attr('cx', function (d) {return d.x})
						 .attr('cy', function (d) {return d.y});

					scope.opText
						.attr('x', function (d) {return d.x - (d.type == 'OR' ? 12 : 17)})
						.attr('y', function (d) {return d.y + 6});
				}

				function dragstarted(d) {
					if (!d3.event.active) scope.simulation.alphaTarget(0.1).restart();
					d.fx = d.x;
					d.fy = d.y;
				}

				function dragged(d) {
					d.fx = d3.event.x;
					d.fy = d3.event.y;
				}

				function dragended(d) {
					if (!d3.event.active) scope.simulation.alphaTarget(0);
					d.fx = null;
					d.fy = null;
				}
			}
		}
	});
