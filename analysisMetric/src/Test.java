import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReaderJSON.read();
		Graph graph = new Graph();
		graph.create();
		//graph.traverse();
		File file = new File("output");
		FileWriter fw;
		BufferedWriter writer;
		int NodesCount = graph.nodesSize;
		Tarjan tarjan = new Tarjan(graph);
		int stronglyConnectedCount = tarjan.getStronglyConnectedCount();
		//System.out.println("stronglyConnectedCount= "+stronglyConnectedCount);
		UnionFind unionfind = new UnionFind(graph);
		int weeklyConnectedCount = unionfind.getWeeklyConnectedCount();
		//System.out.println("weeklyConnectedCount= "+weeklyConnectedCount);
		float connectivityMetric = 10*(1-( weeklyConnectedCount-1)/(NodesCount-1));
		float cycleMetric = 10*(1-(stronglyConnectedCount-1)/(NodesCount-1));
		ShortestPath shortestPath = new ShortestPath(graph,unionfind.getWeeklyConnectedWithFather());
		String DepthMetric = shortestPath.getDepthMetric();
		try {
			 fw = new FileWriter(file);
			 writer = new BufferedWriter(fw);
			 writer.write("cvssAverage\t"+graph.getCvssAverage());
			 writer.newLine();
			 writer.write("cvssMax\t"+graph.getCvssMax());
			 writer.newLine();
			 writer.write("cvssMin\t"+graph.getCvssMin());
			 writer.newLine();
			 writer.write("connectivityMetric\t"+connectivityMetric);
			 writer.newLine();
			 writer.write("cycleMetric\t"+cycleMetric);
			 writer.newLine();
			 writer.write("DepthMetric\t"+DepthMetric);
			 writer.newLine();
			 writer.close();
			 fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.println("cvssAverage= "+graph.getCvssAverage());
		System.out.println("cvssMax= "+graph.getCvssMax());
		System.out.println("cvssMin= "+graph.getCvssMin());
		System.out.println("connectivityMetric= "+connectivityMetric+"\n"+"cycleMetric= "+cycleMetric);
		System.out.println("DepthMetric= "+DepthMetric);*/
	}

}
