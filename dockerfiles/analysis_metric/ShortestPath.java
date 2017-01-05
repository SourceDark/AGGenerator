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
	List<List<String>> weaklyConnected;
	
	public ShortestPath(Graph graph,List<List<String>> weaklyConnected){
		this.graph = graph;
		this.weaklyConnected = weaklyConnected;
	}
	
	/*计算Depth指标值*/
	public String getDepthMetric(){
		double nodeSize = graph.nodesSize;
		double weaklyConnectedCount = weaklyConnected.size();
		double sum = 0;
		double depthMetric = 10.0 /(nodeSize * weaklyConnectedCount);
		//System.out.println("nodeSize="+nodeSize+"weaklyConnectedCount="+weaklyConnectedCount+"depthMetric="+depthMetric);
		//for(Entry<String,List<String>>curEntry :weaklyConnected){
		Iterator<List<String>> it = weaklyConnected.iterator();
		while(it.hasNext()){
			List<String>attackerNodes = new ArrayList<String>();
			List<String>goalNodes = new ArrayList<String>();
			List<String> curWeaklyConnected = it.next();
			//int father = curEntry.getKey();
			//List<Integer> weaklyConnected = curEntry.getValue();
			double weaklyConnectedSize = curWeaklyConnected.size();
			double maxShortestPath = 0.0;
			Iterator<String> ir = curWeaklyConnected.iterator();
			while(ir.hasNext()){
				String curNode = ir.next();
				if(graph.findNodeByID(curNode).isAttackerLocated){
					attackerNodes.add(curNode);
				}
				else if(graph.findNodeByID(curNode).isAttackGoal){
					goalNodes.add(curNode);
				}
			}
			Iterator<String>ite = attackerNodes.iterator();
			while(ite.hasNext()){
				String attacker =ite.next();
				Iterator<String> ite2 = goalNodes.iterator();
				while(ite2.hasNext()){
					String goal = ite2.next();
					int curShortesrPath = dijkstraAlgorithm(attacker,goal,curWeaklyConnected);
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
	
	/*计算从指定源点到指定终点的最短路径，weaklyConnected是最大弱连通子图*/
	public int dijkstraAlgorithm(String startVec,String endVec,List<String> weaklyConnected){
		int weaklyConnectedSize = weaklyConnected.size();
		Map<String,Integer>shortestPath = new HashMap<String,Integer>(weaklyConnectedSize);
		Map<String,String>pathVecs = new HashMap<String,String>(weaklyConnectedSize);
		List<String> remanderList = new ArrayList<String>(weaklyConnected);
		remanderList.remove(remanderList.indexOf(startVec));
		Iterator<String> it = remanderList.iterator();
		while(it.hasNext()){
			String curRemanderNode = it.next();
			shortestPath.put(curRemanderNode, getDist(startVec,curRemanderNode));
			pathVecs.put(curRemanderNode,startVec);
		}
		while(!remanderList.isEmpty()){
			String minVec = remanderList.get(0);//被选中的点
			int minArc = Integer.MAX_VALUE;//被选中的点的距离
			Iterator<String> ir = remanderList.iterator();
			while(ir.hasNext()){
				String remander = ir.next();
				//System.out.println("remander="+remander);
				if(shortestPath.get(remander)<minArc){
					//System.out.println("当前minArc="+minArc);
					minArc = shortestPath.get(remander);
					minVec = remander;
					//System.out.println("minArc="+minArc+"minVec="+minVec);
				}
			}
			remanderList.remove(remanderList.indexOf(minVec));
			//System.out.println("被选中的点是："+minVec);
			if(minVec == endVec){
				break;
			}
			Iterator<String>ir2 =  remanderList.iterator();
			while(ir2.hasNext()){
				String curRemander = ir2.next();
				if(getDist(minVec,curRemander)!=Integer.MAX_VALUE){
					if(shortestPath.get(curRemander)> (shortestPath.get(minVec)+getDist(minVec,curRemander))){
						shortestPath.put(curRemander,(shortestPath.get(minVec)+getDist(minVec,curRemander)));
						pathVecs.put(curRemander, minVec);
					}
				}
			}
		}
		/*System.out.println("The distance of the shortest path form "+startVec+" "+"to "+endVec+" "+"is "+shortestPath.get(endVec));
		
		Set<Entry<String,Integer>> a = shortestPath.entrySet();
		for(Entry e : a){
			System.out.println("结点："+e.getKey()+"最短路径:"+e.getValue());
		}
		
		Set<Entry<String,String>> b = pathVecs.entrySet();
		for(Entry e : b){
			System.out.println("结点："+e.getKey()+"前驱结点："+e.getValue());
		}*/
		
		return shortestPath.get(endVec);
	}
	public int getDist(String start_vec,String end_vec){
		if(graph.findNodeByID(start_vec).next.contains(end_vec)){
			return 1;
		}
		else 
			return Integer.MAX_VALUE;
	}
	
	
	
	
}
