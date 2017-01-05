import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
public class UnionFind {
	private Graph graph;
	//private List<List<Integer>> ans;
	//private Set<Entry<Integer,List<Integer>>> weeklyConnectedWithFather;
	private List<List<String>>  weeklyConnected;
	private Set<Entry<String,List<String>>> weeklyConnectedWithFather;
	public UnionFind(Graph graph){
		this.graph = graph;
		//ans = new ArrayList<List<Integer>>();
		 weeklyConnected = new ArrayList<List<String>>();
	}
	public void connectedSet(HashMap<String,Node>nodes){
		//Map<Integer,Integer> father = new HashMap<Integer,Integer>();
		Map<String,String> father = new HashMap<String,String>();
		//for(Node node:nodes){
			//System.out.println("node"+node.getId());
		Set<Entry<String,Node>>nodesSet = nodes.entrySet();
		for(Entry<String,Node>nodeEntry:nodesSet){
			Node node = nodeEntry.getValue();
			for(String nodeNID:node.next){
				//System.out.println(nodeNID);
				//int curN = Integer.parseInt(node.getId());
				//int curNN = Integer.parseInt(nodeNID);
				String curN = node.getId();
				String curNN = nodeNID;
				String curP = find(father,curN);
				String curNP = find(father,curNN);
				if(Integer.parseInt(curP) != Integer.parseInt(curNP)){
					if(Integer.parseInt(curP)>Integer.parseInt(curNP)){
						father.put(curP, curNP);
					}
					else{
						father.put(curNP, curP);
					}
				}
			}
		}
		Map<String,List<String>>tMap  = new HashMap<String,List<String>>();
		//for(Node node:nodes){
		nodesSet = nodes.entrySet();
		for(Entry<String,Node>nodeEntry:nodesSet){
			Node node = nodeEntry.getValue();
			//int curNode = Integer.parseInt(node.id);
			//int curF = find(father,curNode);
			String curNode = node.getId();
			String curF = find(father,curNode);
			if(!tMap.containsKey(curF)){
				List<String>tempList = new ArrayList<String>();
				tempList.add(curNode);
				tMap.put(curF, tempList);
			}
			else{
				tMap.get(curF).add(curNode);
			}
		}
		
		weeklyConnectedWithFather = tMap.entrySet();
		int i = 0;
		for(Entry<String,List<String>>curEntry :weeklyConnectedWithFather){
			List<String> tWeaklyConnected = curEntry.getValue();
			/*System.out.println("第"+(++i)+"个弱连通分量"+"根是"+curEntry.getKey());
			for(int j=0;j<weaklyConnected.size();j++){
				System.out.println(weaklyConnected.get(j));
			}*/
			 weeklyConnected.add(tWeaklyConnected);
			
		}
		/*for(int i=0;i<ans.size();i++){
			List<Integer> list = ans.get(i);
			System.out.println("第"+i+"个弱连通分量：");
			for(int j=0;j<list.size();j++){
				System.out.println(list.get(j));
			}
		}*/
	}
	
	public int getWeeklyConnectedCount(){
		connectedSet(graph.nodes);
		return  weeklyConnected.size();
	}
	
	public Set<Entry<String,List<String>>> getWeeklyConnectedWithFather(){
		
		if(this.weeklyConnectedWithFather==null){
			connectedSet(graph.nodes);
		}
		return this.weeklyConnectedWithFather;
	}
	
	public List<List<String>> getWeeklyConnected(){
		if(this.weeklyConnected==null){
			connectedSet(graph.nodes);
		}
		return this.weeklyConnected;
	}
	
	private String find(Map<String,String>father,String cur){
		if(!father.containsKey(cur)){
			father.put(cur, cur);
			return cur;
		}
		while(father.get(cur)!=cur){
			cur = father.get(cur);
		}
		return cur;
	}
	
	public String getConnectivityMetric(){
		double weeklyConnectedCount = getWeeklyConnectedCount();
		double NodesCount = graph.nodesSize;
		//System.out.println("NodesCount= "+NodesCount+" weeklyConnectedCount= "+weeklyConnectedCount);
		double connectivityMetric = 10.0*(1-( weeklyConnectedCount-1)/(NodesCount-1));
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(connectivityMetric);
	}
}
