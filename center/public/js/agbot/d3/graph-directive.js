angular.module('agbotApp')
	.directive('treeGraph', function() {
		return {
			restrict: 'E',
			replace: true,
			transclude: true,
			scope: {
				nodes   : '=nodes',
				links   : '=links',
				options : '=options'
			},
			template: '<svg id="tree-graph"></svg>',
			link : function(scope, element, attrs) {	
				var height = element[0].offsetHeight;
				var width = element[0].offsetWidth;
				console.log(element[0]);
				scope.svg = d3.select('#tree-graph');
				//scope.svg.attr('height', height);
				//scope.svg.attr('width', width);
				
				scope.update = function() {
					scope.color = d3.scaleOrdinal(d3.schemeCategory20);
					scope.simulation = d3.forceSimulation()
						.force("link", d3.forceLink().id(function(d) { return d.id; }).distance(function(d) {return scope.options.distance;}))
						.force("charge", d3.forceManyBody().strength(function(d) {return scope.options.charge;}))
						.force("center", d3.forceCenter(width / 2, height / 2));
					scope.svg.html('');
					scope.link = scope.svg.append("g")
						.attr("class", "links")
						.selectAll("line")
						.data(scope.links)
						.enter().append("line")
							.attr("stroke-width", function(d) { return d.value; });
							
					scope.node = scope.svg.append("g")
						.attr("class", "nodes")
						.selectAll("circle")
						.data(scope.nodes)
						.enter().append("circle")
							.attr("r", function(d) { return d.size; })
							.attr("fill", function(d) { return scope.color(d.group); })
							.call(d3.drag()
								.on("start", dragstarted)
								.on("drag", dragged)
								.on("end", dragended));
								
					scope.node_text = scope.svg.append("g")
						.attr("class", "nodes_text")
						.selectAll(".node_text")
						.data(scope.nodes)
						.enter().append("text")
							.text(function(d) { return d.id; });	
							
					scope.node.append("title")
						.text(function(d) { return d.id; });

					scope.simulation
						.nodes(scope.nodes)
						.on("tick", ticked);

					scope.simulation.force("link")
							.links(scope.links);
				}
				scope.$watchGroup(['nodes','links'], function() {
					console.log('updated');
					scope.update();
				});
				
				function ticked() {
					scope.link
						.attr("x1", function(d) { return d.source.x; })
						.attr("y1", function(d) { return d.source.y; })
						.attr("x2", function(d) { return d.target.x; })
						.attr("y2", function(d) { return d.target.y; });

					scope.node
						.attr("cx", function(d) { return d.x; })
						.attr("cy", function(d) { return d.y; });

					scope.node_text
						.attr("x", function(d) { return d.x; })
						.attr("y", function(d) { return d.y+d.size+15; });
				}
				  
				function dragstarted(d) {
					if (!d3.event.active) scope.simulation.alphaTarget(0.3).restart();
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
		};
	});