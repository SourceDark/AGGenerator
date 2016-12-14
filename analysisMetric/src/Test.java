import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReaderJSON.read();
		Graph graph = new Graph();
		graph.create();
		//graph.traverse();
		System.out.println("cvssAverage= "+graph.getCvssAverage());
		System.out.println("cvssMax= "+graph.getCvssMax());
		System.out.println("cvssMin= "+graph.getCvssMin());
		int NodesCount = graph.nodesSize;
		Tarjan tarjan = new Tarjan(graph);
		int stronglyConnectedCount = tarjan.getStronglyConnectedCount();
		//System.out.println("stronglyConnectedCount= "+stronglyConnectedCount);
		UnionFind unionfind = new UnionFind(graph);
		int weeklyConnectedCount = unionfind.getWeeklyConnectedCount();
		//System.out.println("weeklyConnectedCount= "+weeklyConnectedCount);
		float connectivityMetric = 10*(1-( weeklyConnectedCount-1)/(NodesCount-1));
		float cycleMetric = 10*(1-(stronglyConnectedCount-1)/(NodesCount-1));
		System.out.println("connectivityMetric= "+connectivityMetric+"\n"+"cycleMetric= "+cycleMetric);
		ShortestPath shortestPath = new ShortestPath(graph,unionfind.getWeeklyConnectedWithFather());
		String DepthMetric = shortestPath.getDepthMetric();
		System.out.println("DepthMetric= "+DepthMetric);
	}

}
