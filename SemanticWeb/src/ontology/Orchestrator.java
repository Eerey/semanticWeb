package ontology;

import java.util.ArrayList;

import ontology.model.TextSentence;

public class Orchestrator {

	private TextProcessor processor;
	private OntologyWeaver ontologyWeaver;
	
	public Orchestrator(String ontologyName){
		processor = new TextProcessor();
		ontologyWeaver = new OntologyWeaver(ontologyName);
	}
	
	public void process(String input){
		processor.process(input);
		ArrayList<TextSentence> sentences = processor.textSentences;
		ontologyWeaver.process(sentences);
	}

}
