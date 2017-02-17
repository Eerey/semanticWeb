package ontology;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WordAnalyzer {
	
	public static void main(String[] args){
		connect();
	}
	
	public static void connect(){
		try{
//			URL url = new URL("http://www.evemarketeer.com/api/orders/10000043/30119/xml");
			
			URLConnection connection = new URL("https://de.wiktionary.org/wiki/l%C3%A4uft").openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			String cookie = connection.getHeaderField( "Set-Cookie").split(";")[0];
//			connection.setRequestProperty("Cookie", cookie );
			connection.connect();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(connection.getURL().openStream());
			NodeList nodes = doc.getElementsByTagName("a");
//			System.out.println(nodes.getLength() + " nodes found");
			ArrayList<Node> nodeList = new ArrayList<Node>();
			for (int i=0; i<nodes.getLength();i++){
				nodeList.add(nodes.item(i));
			}

			for (Node node : nodeList){	
				doSomething(node);
			}
			} catch(Exception e){
				e.printStackTrace();
			}
			
	}
	
	public static void doSomething(Node node) {
	    // do something with the current node instead of System.out
//	    System.out.println(node.getNodeName());

	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        
            
	        if (currentNode.getTextContent().contains("Flexion:")){
	        	String infinitive = currentNode.getTextContent().split(":")[1];
	        	System.out.println(infinitive);
	        	
	        }
 	        
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	        	 
	            doSomething(currentNode);

	        }
	    }
	}
}
