import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
public class UnionFind {
	private Graph graph;
	private List<List<Integer>> ans;
	private Set<Entry<Integer,List<Integer>>> weeklyConnectedWithFather;
	public UnionFind(Graph graph){
		this.graph = graph;
		ans = new ArrayList<List<Integer>>();
	}
	public void connectedSet(ArrayList<Node> nodes){
		Map<Integer,Integer> father = new HashMap<Integer,Integer>();
		for(Node node:nodes){
			//System.out.println("node"+node.getId());
			for(String nodeNID:node.next){
				//System.out.println(nodeNID);
				int curN = Integer.parseInt(node.getId());
				int curNN = Integer.parseInt(nodeNID);
				int curP = find(father,curN);
				int curNP = find(father,curNN);
				if(curP != curNP){
					if(curP>curNP){
						father.put(curP, curNP);
					}
					else{
						father.put(curNP, curP);
					}
				}
			}
		}
		Map<Integer,List<Integer>>tMap  = new HashMap<Integer,List<Integer>>();
		for(Node node:nodes){
			int curNode = Integer.parseInt(node.id);
			int curF = find(father,curNode);
			if(!tMap.containsKey(curF)){
				List<Integer>tempList = new ArrayList<Integer>();
				tempList.add(curNode);
				tMap.put(curF, tempList);
			}
			else{
				tMap.get(curF).add(curNode);
			}
		}
		
		weeklyConnectedWithFather = tMap.entrySet();
		int i = 0;
		for(Entry<Integer,List<Integer>>curEntry :weeklyConnectedWithFather){
			List<Integer> weaklyConnected = curEntry.getValue();
			/*System.out.println("第"+(++i)+"个弱连通分量"+"根是"+curEntry.getKey());
			for(int j=0;j<weaklyConnected.size();j++){
				System.out.println(weaklyConnected.get(j));
			}*/
			ans.add(weaklyConnected);
			
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
		return ans.size();
	}
	
	public Set<Entry<Integer,List<Integer>>> getWeeklyConnectedWithFather(){
		
		if(this.weeklyConnectedWithFather==null){
			connectedSet(graph.nodes);
		}
		return this.weeklyConnectedWithFather;
	}
	
	private int find(Map<Integer,Integer>father,int cur){
		if(!father.containsKey(cur)){
			father.put(cur, cur);
			return cur;
		}
		while(father.get(cur)!=cur){
			cur = father.get(cur);
		}
		return cur;
	}

}
