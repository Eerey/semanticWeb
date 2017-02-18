package ontology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
//import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WordAnalyzer {

	public static void main(String[] args) {
//		 findVerbInfinitive("ist");
		findFirstName("Marwin");
	}

	public static void findVerbInfinitive(String input) {
		try {
			// URL url = new
			// URL("http://www.evemarketeer.com/api/orders/10000043/30119/xml");

			URLConnection connection = new URL("https://de.wiktionary.org/wiki/" + input).openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			String cookie = connection.getHeaderField("Set-Cookie").split(";")[0];
			// connection.setRequestProperty("Cookie", cookie );
			connection.connect();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(connection.getURL().openStream());
			NodeList nodes = doc.getElementsByTagName("a");
			// System.out.println(nodes.getLength() + " nodes found");
			ArrayList<Node> nodeList = new ArrayList<Node>();
			for (int i = 0; i < nodes.getLength(); i++) {
				nodeList.add(nodes.item(i));
			}

			for (Node node : nodeList) {
				NodeList nodeL = node.getChildNodes();
				for (int i = 0; i < nodeL.getLength(); i++) {
					Node currentNode = nodeL.item(i);

					if (currentNode.getTextContent().contains("Flexion:")) {
						String infinitive = currentNode.getTextContent().split(":")[1];
						System.out.println(infinitive);

					}

					if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
						// calls this method for all the children which is
						// Element

						// doSomething(currentNode);

					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

	}

	public static String findFirstName(String input) {
		Document namepedia = null;
		try {
			namepedia = Jsoup.connect("http://www.namepedia.org/en/firstname/"+input).get();
		} catch (Exception e) {
		}
		try{
		String newIP = namepedia.select(".namerecord").get(0).text().split("First name: ")[1];
		System.out.println(">>"+ newIP +"<< was identified as a name");
		} catch(Exception e){
			System.out.println(">>"+ input +"<< is NOT a named or was NOT found");
		}
		return null;

	}
}