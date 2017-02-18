package ontology;

import java.util.ArrayList;

import ontology.model.TextSentence;
import ontology.opennlp.NameFinder;

public class TextToOntologyWeaver {

	public ArrayList<TextSentence> sentences;
	public OntologyHelper ontology;
	
	private NameFinder personNameFinder;
	private NameFinder locationNameFinder;
	private NameFinder organizationNameFinder;
	
	public TextToOntologyWeaver(String input){
//		this.ontology = new OntologyHelper();
		
		personNameFinder = NameFinder.getPersonNameFinder();
		locationNameFinder = NameFinder.getLocationNameFinder();
		organizationNameFinder = NameFinder.getOrganizationNameFinder();
		
		TextSentenceProcessor processor = new TextSentenceProcessor();
		processor.process(input);
	
		
		this.sentences = processor.textSentences; //kann bis jetzt nur einen Satz
		
		for(TextSentence sentence : sentences){
			for(int i = 0; i<sentence.taggedWords.size(); i++)
				System.out.println(sentence.taggedWords.get(i).word+": "+personNameFinder.isName(sentence,i));
		}
	}
	
}
