package ontology;

import java.util.ArrayList;

public class TextToOntologyWeaver {

	public ArrayList<TextSentence> sentences;
	public OntologyHelper ontology;
	
	public TextToOntologyWeaver(String input){
//		this.ontology = new OntologyHelper();
		this.sentences = TextParser.splitSentences(input); //kann bis jetzt nur einen Satz
	}
}
