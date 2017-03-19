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
		ArrayList<TextSentence> sentences = processor.process(input);
		ontologyWeaver.process(sentences);
	}

}
