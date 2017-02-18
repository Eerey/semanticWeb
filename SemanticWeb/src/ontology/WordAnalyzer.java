package ontology;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WordAnalyzer {

	public static void main(String[] args) {
		// checkIfHumanName("Marwin");
		// findVerb("tanzten");
	}

	public static TextConcept checkIfHumanName(String input) {
		Document namepedia = null;
		try {
			namepedia = Jsoup.connect("http://www.namepedia.org/en/firstname/" + input).get();
		} catch (Exception e) {
		}
		try {
			String gender = namepedia.select("#gender").get(0).text();
			String name = namepedia.select(".namerecord").get(0).text().split("First name: ")[1];
			System.out.println(">>" + gender + "<< was identified as the gender");
			System.out.println(">>" + name + "<< was identified as a name");
			TextConcept concept = new TextConcept("Person");
			concept.isIndividual = true;
			concept.properties.add(new TextConceptDatatypeProperty("sex", concept.className, "String"));
			return concept;
		} catch (Exception e) {
			System.out.println(">>" + input + "<< is NOT a named or was NOT found");
		}
		return null;

	}

	public static TextVerb findVerb(String input) {
		// TODO: statt String ein TextVerb mit Konjunktion etc.
		
		Document wiktionary = null;
		try {
			wiktionary = Jsoup.connect("https://en.wiktionary.org/wiki/" + input).get();
		} catch (Exception e) {
		}
		try {
			// String gender = namepedia.select("#gender").get(0).text();
			// String name =
			// wiktionary.select("a:contains(Flexion)").get(0).text().split("Flexion:")[1];

			// search id "Verb"
			// gib parent
			// gib zweitnächste Element
			// selektiere a
//			Element element = wiktionary.select("ol li a").get(0);
			Element element = wiktionary.select("#Verb").parents().next().next().select("ol").select("a").get(0);
			if (element != null) {
				String name = element.text();
				System.out.println(">>" + name + "<< was identified as the infinitive of " + input);
				TextVerb verb = new TextVerb();
			}else throw new Exception();
		} catch (Exception e) {
			System.out.print("EXCEPTION (probably NullPointer) ");
			System.out.println(">>" + input + "<< is not a verb");
		}
		return null;

	}
}