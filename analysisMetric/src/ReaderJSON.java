import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
public class ReaderJSON {
	private static JsonArray nodes;
	private static JsonArray edges;
	private static int nodesSize;
	public static void read(){
		JsonParser parser = new JsonParser();
		File file = null;
		FileWriter fw = null;
		BufferedWriter writer = null;
		try{
			file = new File("inputfile");
			JsonObject json = (JsonObject)parser.parse(new FileReader("/data/input"));
			String content = json.get("content").getAsString();
			JsonObject cont = parser.parse(content).getAsJsonObject();
			//System.out.println(cont.nodes.length);
			nodes = cont.getAsJsonArray("nodes");
			edges = cont.getAsJsonArray("edges");
		}
		catch(JsonIOException e){
			e.printStackTrace();
		}
		catch(JsonSyntaxException e){
			e.printStackTrace();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		try{
			fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			writer.write("nodes");
			writer.newLine();
			for(int i=0;i< nodes.size();i++){
				JsonObject node = nodes.get(i).getAsJsonObject();
				String id = node.get("id").getAsString();
				String info = node.get("info").getAsString();
				String type = node.get("type").getAsString();
				String initial = node.get("initial").getAsString();
				writer.write(id+"\t"+info+"\t"+type+"\t"+initial);
				writer.newLine();
			}
			writer.write("edges");
			writer.newLine();
			for(int i=0;i< edges.size();i++){
				JsonObject edge = edges.get(i).getAsJsonObject();
				String source = edge.get("source").getAsString();
				String target = edge.get("target").getAsString();
				writer.write(source+"\t"+target);
				writer.newLine();
			}
			nodesSize = nodes.size();
		}
		catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				writer.close();
				fw.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}			
	}
	public static int getNodesSize(){
		return nodesSize;
	}
}
