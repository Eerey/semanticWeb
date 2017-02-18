package ontology.model;

import java.util.ArrayList;

public class TextSentence {
	public String sentence = null;
	public ArrayList<TextSentence> contextSentences = null;
	public ArrayList<TextWord> taggedWords = null;

	public TextConcept subject = null;
	public TextConcept object = null;
	public TextVerb verb = null;
	public String tense = null;
	public String patternName = null;

	public boolean hasHierarchy = false;
	public boolean hasIndividuals = false;
	public boolean hasDatatypeProperties = false;
	public boolean hasObjectProperties = false;

	public TextSentence(String sentence) {
		this.sentence = sentence;
		contextSentences = new ArrayList<>();
		taggedWords = new ArrayList<>();
		
		subject = new TextConcept();
		object = new TextConcept();
		verb = new TextVerb();
		tense = "unknown";
		patternName = "unknown";
		
	}

}
