import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Tarjan {
	private int numOfNode;
	private Graph graph;
	private List<ArrayList<Integer>> result;
	private boolean[] inStack;//ջ��Ԫ��ֵΪtrue��ʾ�ýڵ���ջ��
	private Stack<Integer> stack;
	private int[] dfn;//����ĳ����Ƿ񱻷��ʹ�
	private int[] low;//����ĳ����ܹ�׷�ݵ��������ջ�нڵ�Ĵ����
	private int time;
	
	public Tarjan(Graph graph){
		this.graph = graph;
		this.numOfNode = graph.nodes.size();
		this.inStack = new boolean[numOfNode];
		this.stack = new Stack<Integer>();
		time = 0;
		dfn = new int[numOfNode];
		low = new int[numOfNode];
		Arrays.fill(dfn, -1);
		Arrays.fill(low,-1);
		result = new ArrayList<ArrayList<Integer>>();
	}
	
	public List<ArrayList<Integer>> run(){
		for(int i=0;i<numOfNode;i++){
			if(dfn[i]==-1){
				tarjan(i);
			}
		}
		return result;
	}
	
	public int getStronglyConnectedCount(){
		for(int i=0;i<numOfNode;i++){
			if(dfn[i]==-1){
				tarjan(i);
			}
		}
		return result.size();
	}
	
	public void tarjan(int current){
		dfn[current]=low[current]=time++;
		inStack[current]=true;
		stack.push(current);
		Node node = graph.nodes.get(current);
		for(int i=0;i<node.getNext().size();i++){
			String nextNode = node.getNext().get(i);
			int next = Integer.parseInt(nextNode);
			if(dfn[next]==-1){//����ڽӽڵ�û�б����ʹ�
				tarjan(next);
				low[current] = Math.min(low[current], low[current]);
			}
			else if(inStack[next]){//����ڽӽڵ���ջ�У���������ǰ���Ѿ����ڵ�ǿ��ͨ������һ����
				low[current] = Math.min(low[current], dfn[next]);
			}
		}
		if(low[current]==dfn[current]){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			int j = -1;
			while(current!=j){
				j = stack.pop();
				inStack[j]=false;
				temp.add(j);
			}
			result.add(temp);
		}
	}
	
 
}
