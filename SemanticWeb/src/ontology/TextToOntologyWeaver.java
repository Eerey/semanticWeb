package ontology;

import java.util.ArrayList;

public class TextToOntologyWeaver {

	public ArrayList<TextSentence> sentences;
	public OntologyHelper ontology;
	
	public TextToOntologyWeaver(String input){
		TextParser textParser = new TextParser();
//		this.ontology = new OntologyHelper();
		this.sentences = textParser.splitSentences(input); //kann bis jetzt nur einen Satz
	}
	
}
