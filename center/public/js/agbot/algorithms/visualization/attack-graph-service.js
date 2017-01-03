/**
 * Created by Nettle on 2016/12/28.
 */

agbotApp.service('attackGraphService', [function () {

    var r = 30, size = 60;
    var gap = 20;
    var attackGraphService = this;

    this.setNodeSize = function (value) {
        size = value;
        r = value / 2;
    };

    this.setGapSize = function (value) {
        gap = value;
    };

    this.connectLinks = function (nodes, links) {
        var nodePool = {};
        nodes.forEach(function (node) {
            nodePool[node.id] = node;
        });

        links.forEach(function (link) {
            link.sourceNode = nodePool[link.source];
            link.targetNode = nodePool[link.target];
        });
        return nodePool;
    };

    this.simplify = function (nodes, links) {
        attackGraphService.connectLinks(nodes, links);
        var newLinks = [];
        var hashTable = {};
        nodes.forEach(function (d) {
            if (d.type == 'OR' || d.nodeType == 'attacker') {
                d.hold = true;
                d.mark = d.id;
            }
            else d.hold = false;
        });
        links.forEach(function (d) {
            if (d.sourceNode.type == 'AND')
                d.sourceNode.mark = d.target;
        });
        links.forEach(function (d) {
            if (d.targetNode.type == 'AND') {
                var linkId =  d.source + '-' + d.targetNode.mark;
                if (!hashTable[linkId]) {
                    newLinks.push({
                        source: d.source,
                        target: d.targetNode.mark
                    });
                    hashTable[linkId] = true;
                }
            }
        });

        return {
            nodes: nodes.filter(function (node) {
                return node.hold;
            }),
            links: newLinks
        };
    };

    this.calculateCoordinates = function (nodes, links, dstNodes, startNodes) {
        if (!dstNodes)  {
            console.error('No destination nodes');
            return ;
        }

        var nodePool = attackGraphService.connectLinks(nodes, links);

        var cnt = new Array();
        for (var i = 0; i <= nodes.length; ++i)
            cnt[i] = 0;

        dstNodes
            .map(function (id) {
                return nodePool[id]
            })
            .forEach(function (node) {
                node.lv = 1;
                node.offset = cnt[1] ++;
            });

        if (startNodes)
            startNodes
                .map(function (id) {
                    return nodePool[id];
                })
                .forEach(function (node) {
                    node.lv = nodes.length;
                    node.offset = cnt[nodes.length] ++;
                });

        var seq = dstNodes.map(function (id) {
            return nodePool[id]
        });

        for (var i = 0; i < seq.length; ++i) {
            var v = seq[i];
            for (var j = 0; j < links.length; ++j)
                if (links[j].target == v.id && !links[j].sourceNode.lv) {
                    links[j].sourceNode.lv = v.lv + 1;
                    links[j].sourceNode.offset = cnt[ links[j].sourceNode.lv ]++;
                    seq.push(links[j].sourceNode);
                }
        }

        var maxOffset = Math.max.apply(null, cnt);
        var maxLv = cnt.filter(function (value) {
            return value > 0;
        }).length;

        if (startNodes)
            startNodes
                .map(function (id) {
                    return nodePool[id];
                })
                .forEach(function (node) {
                    node.lv = maxLv;
                    node.offset = cnt[maxLv] ++;
                });

        nodes.forEach(function (node) {
            var offset = (maxOffset - cnt[ node.lv ]) * (size + gap) / 2;
            node.y = (maxLv - node.lv) * (size + gap * 2) + r;
            node.x = offset + node.offset * (size + gap) + r;
        });

        return {
            height: (maxLv * (r + gap) - gap) * 2,
            width: maxOffset * (size + gap) - gap
        }
    };

    this.getColor = function (node) {
        if (node.goal) return '#FF3030';
        if (node.nodeType == 'attacker') return '#d65222';
        if (node.type == 'OR') return '#EEEE00';
        if (node.type == 'AND') return '#66ccff';
        return '#7CCD7C';
    };

    this.drawLinks = function (links, svg) {
        return svg.append("g")
            .attr("class", "links")
            .selectAll("line")
            .data(links)
            .enter()
            .append("line")
            .attr("x1", function(d) { return d.sourceNode.x; })
            .attr("y1", function(d) { return d.sourceNode.y; })
            .attr("x2", function(d) { return d.targetNode.x; })
            .attr("y2", function(d) { return d.targetNode.y; });
    }

    this.drawNodes = function (nodes, svg) {
        var node = svg
            .append("g")
            .attr("class", "nodes")
            .selectAll('g')
            .data(nodes)
            .enter()
            .append('g')
            .attr('class', 'node')
            .attr('transform', function(d) {
                return 'translate(' + d.x + ' ' + d.y + ')';
            })
            .attr("fill", function(d) {
                return attackGraphService.getColor(d);
            });

        // add render
        node
            .append('use')
            .classed('render', true)
            .attr('xlink:href', function (d) {
                return '#' + (d.type == 'LEAF' ? 'leaf' : 'nonleaf');
            });

        // add body
        node
            .append('use')
            .classed('body', true)
            .attr('xlink:href', function (d) {
                return '#' + (d.type == 'LEAF' ? 'leaf' : 'nonleaf');
            });

        // add inner
        node
            .append('use')
            .classed('inner', true)
            .attr('xlink:href', function (d) {
                return '#' + (d.type == 'LEAF' ? 'leaf' : 'nonleaf') + '_inner';
            });

        return node;
    }
}]);
