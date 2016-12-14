import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Graph {
	public ArrayList<Node> nodes; 
	public int nodesSize;
	public void create(){
		File file = new File("inputfile"); 
		FileReader fr = null;
		BufferedReader br = null;
		try{
			nodesSize = ReaderJSON.getNodesSize();
			nodes = new ArrayList<Node>(nodesSize);
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
					if(nodeinfo[1].contains("vulExists")){
						String cveid = nodeinfo[1].substring(nodeinfo[1].indexOf("\'C")+1, nodeinfo[1].indexOf("\',"));
						String cvss = getCvssScore(cveid);
						VulnerabilityNode node = new VulnerabilityNode();
						node.setId(nodeinfo[0]);
						node.setInfo(nodeinfo[1]);
						node.setType(nodeinfo[2]);
						node.setInitial(nodeinfo[3]);
						node.setCvssScore(Float.parseFloat(cvss));
						node.setCveid(cveid);
						node.setVulNode();
						nodes.add(node);
					}
					else{
						Node node = new Node();
						node.setId(nodeinfo[0]);
						node.setInfo(nodeinfo[1]);
						node.setType(nodeinfo[2]);
						node.setInitial(nodeinfo[3]);
						if(nodeinfo[1].contains("attackerLocated")){
							node.setAttackerLocated();
						}
						nodes.add(node);
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
		Node node = nodes.get(Integer.parseInt(id)-1);
		return node;
	}
	
	public String getCvssScore(String cveID){
		String url = "http://162.105.30.65:9015/";
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
	
	public void traverse(){
		Iterator<Node> it = nodes.iterator();
		while(it.hasNext()){
			System.out.println("µã£º");
			Node node = it.next();
			System.out.println(node.getId());
			ArrayList<String> next = node.getNext();
			Iterator<String> it1 = next.iterator();
			System.out.println("±ß£º");
			while(it1.hasNext()){
				System.out.println(it1.next());
			}
		}
	}
	
	public float getCvssAverage(){
		Iterator<Node> it = nodes.iterator();
		float cvssSum = 0;
		int cvssCount = 0;
		while(it.hasNext()){
			Node node = it.next();
			if(node.isVulNode){
				cvssCount++;
				VulnerabilityNode vulNode = (VulnerabilityNode)node;
				cvssSum +=  vulNode.getCvssScore();
			}
		}
		return cvssSum/cvssCount;
	}
	
	public float getCvssMax(){
		Iterator<Node> it = nodes.iterator();
		float cvssMax = 0;
		float cvss;
		while(it.hasNext()){
			Node node = it.next();
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
		Iterator<Node> it = nodes.iterator();
		float cvssMin = 0;
		float cvss = 0;
		while(it.hasNext()){
			Node node = it.next();
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
