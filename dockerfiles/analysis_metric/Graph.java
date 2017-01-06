import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Graph {
	//public ArrayList<Node> nodes;
	HashMap<String,Node>nodes;
	public int nodesSize;
	public void create(){
		File file = new File("inputfile"); 
		FileReader fr = null;
		BufferedReader br = null;
		try{
			nodesSize = ReaderJSON.getNodesSize();
			nodes = new HashMap<String,Node>();
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			int flag = 0;
			while((line = br.readLine())!=null){
				if(line.equals("nodes")){
					continue;
				}
				if(line.equals("edges")){
					flag = 1;
					continue;
				}
				if(flag==0){
					String[]nodeinfo = new String[4];
					nodeinfo = line.split("\t");
					if(nodeinfo[5].equals("true")){
						String cveid = nodeinfo[8];
						String cvss = getCvssScore(cveid);
						VulnerabilityNode node = new VulnerabilityNode();
						node.setId(nodeinfo[0]);
						node.setInfo(nodeinfo[1]);
						node.setType(nodeinfo[2]);
						node.setInitial(nodeinfo[3]);
						node.setNodeType(nodeinfo[4]);
						if(nodeinfo[5].equals("true")){
							node.setVulNode();
						}
						if(nodeinfo[6].equals("true")){
							node.setAttackerLocated();
						}
						if(nodeinfo[7].equals("true")){
							node.setAttackGoal();
						}
						node.setCvssScore(Float.parseFloat(cvss));
						node.setCveid(cveid);
						node.setVulNode();
						nodes.put(nodeinfo[0],node);
					}
					else{
						Node node = new Node();
						node.setId(nodeinfo[0]);
						node.setInfo(nodeinfo[1]);
						node.setType(nodeinfo[2]);
						node.setInitial(nodeinfo[3]);
						node.setNodeType(nodeinfo[4]);
						if(nodeinfo[5].equals("true")){
							node.setVulNode();
						}
						if(nodeinfo[6].equals("true")){
							node.setAttackerLocated();
						}
						if(nodeinfo[7].equals("true")){
							node.setAttackGoal();
						}
						nodes.put(nodeinfo[0], node);
					}					
				}
				if(flag==1){
					String[]edge = new String[2];
					edge = line.split("\t");
					Node node = findNodeByID(edge[0]);
					node.addNext(edge[1]);
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				fr.close();
				br.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
		}
	}
	public Node findNodeByID(String id){
		Node node = nodes.get(id);
		return node;
	}
	
	public String getCvssScore(String cveID){
		String url = "http://162.105.30.71:9015/";
		url= url+cveID;
		String json = null;
		try {
			json = Jsoup.connect(url).ignoreContentType(true).execute().body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonParser parser = new JsonParser();
		JsonObject response = parser.parse(json).getAsJsonObject();
		String cvssScore = response.get("cvssScore").getAsString();
		return cvssScore;
	}	
	
	public float getCvssAverage(){
		Set<Entry<String,Node>>nodesSet = nodes.entrySet();
		float cvssSum = 0;
		int cvssCount = 0;
		for(Entry<String,Node>nodeEntry:nodesSet){
			Node node = nodeEntry.getValue();
			if(node.isVulNode){
				cvssCount++;
				VulnerabilityNode vulNode = (VulnerabilityNode)node;
				cvssSum +=  vulNode.getCvssScore();
			}
		}
		return cvssSum/cvssCount;
	}
	
	public float getCvssMax(){
		Set<Entry<String,Node>>nodesSet = nodes.entrySet();
		float cvssMax = 0;
		float cvss;
		for(Entry<String,Node>nodeEntry:nodesSet){
			Node node = nodeEntry.getValue();
			if(node.isVulNode){
				VulnerabilityNode vulNode = (VulnerabilityNode)node;
				cvss = vulNode.getCvssScore();
				if(cvss>cvssMax){
					cvssMax = cvss;
				}
			}
		}
		return cvssMax;
	}
	
	public float getCvssMin(){
		Set<Entry<String,Node>>nodesSet = nodes.entrySet();
		float cvssMin = 0;
		float cvss = 0;
		for(Entry<String,Node>nodeEntry:nodesSet){
			Node node = nodeEntry.getValue();
			if(node.isVulNode){
				VulnerabilityNode vulNode = (VulnerabilityNode)node;
				cvss = vulNode.getCvssScore();
				if(cvssMin==0){
					cvssMin = cvss;
				}
				else if(cvss<cvssMin){
					cvssMin = cvss;
				}
			}
		}
		return cvssMin;
	}
	
}
