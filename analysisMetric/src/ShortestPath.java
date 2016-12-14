import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ShortestPath {
	private Graph graph;
	Set<Entry<Integer,List<Integer>>> weaklyConnectedWithFather;
	
	public ShortestPath(Graph graph,Set<Entry<Integer,List<Integer>>> weaklyConnectedWithFather){
		this.graph = graph;
		this.weaklyConnectedWithFather = weaklyConnectedWithFather;
	}
	
	/*����Depthָ��ֵ*/
	public String getDepthMetric(){
		int nodeSize = graph.nodesSize;
		int weaklyConnectedCount = weaklyConnectedWithFather.size();
		double sum = 0;
		double depthMetric = 10.0 /(nodeSize * weaklyConnectedCount);
		//System.out.println("nodeSize="+nodeSize+"weaklyConnectedCount="+weaklyConnectedCount+"depthMetric="+depthMetric);
		for(Entry<Integer,List<Integer>>curEntry :weaklyConnectedWithFather){
			int father = curEntry.getKey();
			List<Integer> weaklyConnected = curEntry.getValue();
			double weaklyConnectedSize = weaklyConnected.size();
			double maxShortestPath = 0.0;
			Iterator<Integer> it = weaklyConnected.iterator();
			while(it.hasNext()){
				int curNode = it.next();
				if(graph.findNodeByID(Integer.toString(curNode)).isAttackerLocated){
					int curShortesrPath = dijkstraAlgorithm(curNode,father,weaklyConnected);
					if(curShortesrPath > maxShortestPath){
						maxShortestPath = curShortesrPath;
					}
				}
			}
			sum = sum + weaklyConnectedSize*(1-(maxShortestPath/(weaklyConnectedSize-1)));
		}
		depthMetric = depthMetric * sum;
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(depthMetric);
	}
	
	/*�����ָ��Դ�㵽ָ���յ�����·����weaklyConnected���������ͨ��ͼ*/
	public int dijkstraAlgorithm(int startVec,int endVec,List<Integer> weaklyConnected){
		int weaklyConnectedSize = weaklyConnected.size();
		Map<Integer,Integer>shortestPath = new HashMap<Integer,Integer>(weaklyConnectedSize);
		Map<Integer,Integer>pathVecs = new HashMap<Integer,Integer>(weaklyConnectedSize);
		List<Integer> remanderList = new ArrayList<Integer>(weaklyConnected);
		remanderList.remove(remanderList.indexOf(startVec));
		Iterator<Integer> it = remanderList.iterator();
		while(it.hasNext()){
			int curRemanderNode = it.next();
			shortestPath.put(curRemanderNode, getDist(startVec,curRemanderNode));
			pathVecs.put(curRemanderNode,startVec);
		}
		while(!remanderList.isEmpty()){
			int minVec = remanderList.get(0);//��ѡ�еĵ�
			int minArc = Integer.MAX_VALUE;//��ѡ�еĵ�ľ���
			Iterator<Integer> ir = remanderList.iterator();
			while(ir.hasNext()){
				int remander = ir.next();
				//System.out.println("remander="+remander);
				if(shortestPath.get(remander)<minArc){
					//System.out.println("��ǰminArc="+minArc);
					minArc = shortestPath.get(remander);
					minVec = remander;
					//System.out.println("minArc="+minArc+"minVec="+minVec);
				}
			}
			remanderList.remove(remanderList.indexOf(minVec));
			//System.out.println("��ѡ�еĵ��ǣ�"+minVec);
			if(minVec == endVec){
				break;
			}
			Iterator<Integer>ir2 =  remanderList.iterator();
			while(ir2.hasNext()){
				int curRemander = ir2.next();
				if(getDist(minVec,curRemander)!=Integer.MAX_VALUE){
					if(shortestPath.get(curRemander)> (shortestPath.get(minVec)+getDist(minVec,curRemander))){
						shortestPath.put(curRemander,(shortestPath.get(minVec)+getDist(minVec,curRemander)));
						pathVecs.put(curRemander, minVec);
					}
				}
			}
		}
		//System.out.println("The distance of the shortest path form "+startVec+" "+"to "+endVec+" "+"is "+shortestPath.get(endVec));
		
		/*Set<Entry<Integer,Integer>> a = shortestPath.entrySet();
		for(Entry e : a){
			System.out.println("��㣺"+e.getKey()+"���·��:"+e.getValue());
		}
		
		Set<Entry<Integer,Integer>> b = pathVecs.entrySet();
		for(Entry e : b){
			System.out.println("��㣺"+e.getKey()+"ǰ����㣺"+e.getValue());
		}*/
		
		return shortestPath.get(endVec);
	}
	public int getDist(int start_vec,int end_vec){
		if(graph.findNodeByID(Integer.toString(start_vec)).next.contains(Integer.toString(end_vec))){
			return 1;
		}
		else 
			return Integer.MAX_VALUE;
	}
	
	
	
	
}
