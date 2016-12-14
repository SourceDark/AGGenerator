import java.util.ArrayList;

public class Node {
	protected String id;
	protected String info;
	protected String type;
	protected String initial;
	protected ArrayList<String> next = null;
	protected boolean isVisited = false;
	protected boolean isVulNode = false;
	protected boolean isAttackerLocated = false;
	public Node(){
		next = new ArrayList<String>();
	}
	public void addNext(String node){
		next.add(node);
	}
	public ArrayList<String> getNext(){
		return next;
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public void setVisited(){
		isVisited = true;
	}
	public boolean isVisited() {
		return isVisited;
	}
	public boolean isVulNode() {
		return isVulNode;
	}
	public void setVulNode() {
		this.isVulNode = true;
	}
	public boolean isAttackerLocated(){
		return this.isAttackerLocated;
	}
	public void setAttackerLocated(){
		this.isAttackerLocated = true;
	}
}
