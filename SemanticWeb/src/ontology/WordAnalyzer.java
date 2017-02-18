package ontology;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WordAnalyzer {

	public boolean checkIfExistingName(String input) {
		sleep(1);
		Document namepedia = null;
		try {
			namepedia = Jsoup.connect("http://www.namepedia.org/en/firstname/" + input).get();
		} catch (Exception e) {
		}
		try {
			String gender = namepedia.select("#gender").get(0).text();
			String name = namepedia.select(".namerecord").get(0).text().split("First name: ")[1];
			System.out.println(">>" + name + "("+gender+")<< was identified as a name");
//			TextConcept concept = new TextConcept("Person");
//			concept.isIndividual = true;
//			concept.properties.add(new TextConceptDatatypeProperty("sex", concept.className, "String"));
//			return concept;
			return true;
		} catch (Exception e) {
			System.out.println(">>" + input + "<< is NOT a name or was NOT found");
		}
		return false;
	}
	
	public TextVerb findVerb(String input) {
		if (input.length()==1) return null;
		sleep(1);
		Document wiktionary = null;
		try {
			wiktionary = Jsoup.connect("http://conjugator.reverso.net/conjugation-english-verb-"+input+".html").get();
			Element element = wiktionary.select("#ch_lblVerb").get(0);
			if (element != null) {
				String name = element.text();
				System.out.println(">>" + name + "<< was identified as the infinitive of " + input);
				TextVerb verb = new TextVerb();
				verb.infinitive =name;
				return verb;
			}else throw new Exception();
		} catch (Exception e) {
			System.out.println(">>" + input + "<< is not a verb");
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static void sleep(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}