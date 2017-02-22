package ontology;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ontology.model.TextVerb;

public class Dictionary {
	
	public String getGender(String name){
		sleep(1);
		Document namepedia = null;
		try {
			namepedia = Jsoup.connect("http://www.namepedia.org/en/firstname/" + name).get();
			return namepedia.select("#gender").get(0).text();
		} catch (Exception e) {
		}
		return "unknown";
	}
	
	public TextVerb findVerb(String input) {
		TextVerb verb = new TextVerb();
		verb.originalVerb = input;
		sleep(1);
		Document wiktionary = null;
		try {
			wiktionary = Jsoup.connect("http://conjugator.reverso.net/conjugation-english-verb-"+input+".html").get();
			Element element = wiktionary.select("#ch_lblVerb").get(0);
			if (element != null) {
				String name = element.text();
				System.out.println(">>" + name + "<< was identified as the infinitive of " + input);
				
				verb.infinitive =name;
				
				return verb;
			}else throw new Exception();
		} catch (Exception e) {
			System.out.println(">>" + input + "<< is not a verb");
			System.out.println(e.getMessage());
		}
		return verb;
	}
	
	public static void sleep(int seconds){
		try {
			Thread.sleep(seconds*100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}