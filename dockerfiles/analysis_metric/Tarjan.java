import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

public class Tarjan {
	//private int numOfNode;
	private Graph graph;
	//private List<ArrayList<Integer>> result;
	private List<ArrayList<String>> result;
	//private boolean[] inStack;//栈中元素值为true表示该节点在栈中
	private HashMap<String,Boolean>inStack;//栈中元素值为true表示该节点在栈中 
	private Stack<String> stack;
	//private int[] dfn;//代表某结点是否被访问过
	private HashMap<String,Integer>dfn;//代表某结点是否被访问过
	//private int[] low;//代表某结点能够追溯到的最早的栈中节点的次序号
	private HashMap<String,Integer>low;//代表某结点能够追溯到的最早的栈中节点的次序号
	private int time;
	
	public Tarjan(Graph graph){
		this.graph = graph;
		//this.numOfNode = graph.nodes.size();
		//this.inStack = new boolean[numOfNode];
		this.inStack = new HashMap<String,Boolean>();
		this.stack = new Stack<String>();
		time = 0;
		dfn = new HashMap<String,Integer>();
		low = new HashMap<String,Integer>();
		//Arrays.fill(dfn, -1);
		//Arrays.fill(low,-1);
		Set<Entry<String,Node>>nodesSet = graph.nodes.entrySet();
		for(Entry<String,Node>nodeEntry:nodesSet){
			Node node = nodeEntry.getValue();
			dfn.put(node.getId(), -1);
			low.put(node.getId(), -1);
		}
		/*Set<Entry<String,Integer>>dfnEntry =dfn.entrySet();
		System.out.println("dfn:");
		for(Entry<String,Integer>entry :dfnEntry){
			System.out.println("key="+entry.getKey()+"value="+entry.getValue());
		}*/
		result = new ArrayList<ArrayList<String>>();
	}
	
	public List<ArrayList<String>> run(){
		/*for(int i=0;i<numOfNode;i++){
			if(dfn[i]==-1){
				tarjan(i);
			}
		}*/
		Iterator<String> it = graph.nodes.keySet().iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			if(dfn.get(id)==-1){
				tarjan(id);
			}
		}
		return result;
	}
	
	public int getStronglyConnectedCount(){
		/*for(int i=0;i<numOfNode;i++){
			if(dfn[i]==-1){
				tarjan(i);
			}
		}*/
		Iterator<String> it = graph.nodes.keySet().iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			if(dfn.get(id)==-1){
				tarjan(id);
			}
		}
		/*Set<Entry<String,Integer>>dfnEntry =dfn.entrySet();
		System.out.println("dfn:");
		for(Entry<String,Integer>entry :dfnEntry){
			System.out.println("key="+entry.getKey()+"value="+entry.getValue());
		}
		Set<Entry<String,Integer>>lowEntry =low.entrySet();
		System.out.println("low:");
		for(Entry<String,Integer>entry :lowEntry){
			System.out.println("key="+entry.getKey()+"value="+entry.getValue());
		}*/
		
		return result.size();
	}
	
	public void tarjan(String id){
		//dfn[id]=low[id]=time++;
		dfn.put(id, time);
		low.put(id, time);
		time = time+1;
		//inStack[id]=true;
		inStack.put(id, true);
		stack.push(id);
		//System.out.println(id+"入栈了");
		Node node = graph.nodes.get(id);
		for(int i=0;i<node.getNext().size();i++){
			String nextNode = node.getNext().get(i);
			//int next = Integer.parseInt(nextNode);
			if(dfn.get(nextNode)==-1){//如果邻接节点没有被访问过
				tarjan(nextNode);
				//low[id] = Math.min(low[id], low[next]);
				low.put(id, Math.min(low.get(id),low.get(nextNode)));
			}
			else if(inStack.get(nextNode)){//如果邻接节点在栈中，代表它是前面已经存在的强连通分量的一部分
				//low[id] = Math.min(low[id], dfn[next]);
				low.put(id, Math.min(low.get(id),dfn.get(nextNode)));
			}
		}
		/*if(low[id]==dfn[id]){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			int j = -1;
			while(id!=j){
				j = stack.pop();
				inStack[j]=false;
				temp.add(j);
			}
			result.add(temp);
		}*/
		if(low.get(id)==dfn.get(id)){
			ArrayList<String> temp = new ArrayList<String>();
			String j;
			do{
				j = stack.pop();
				inStack.put(j,false);
				temp.add(j);
				//System.out.println(j+"出栈了");
			}while(!id.equals(j));
			//System.out.println("---------------------------------");
			result.add(temp);
		}
	}
	
	public String getCycleMetric(){
		double NodesCount = graph.nodesSize;
		double stronglyConnectedCount = getStronglyConnectedCount();
		//System.out.println("NodesCount= "+NodesCount+" stronglyConnectedCount= "+stronglyConnectedCount);
		double cycleMetric = 10.0*(1-(stronglyConnectedCount-1)/(NodesCount-1));
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(cycleMetric);
	}
	
 
}
