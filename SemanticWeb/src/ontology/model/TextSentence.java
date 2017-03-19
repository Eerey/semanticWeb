package ontology.model;

import java.util.ArrayList;

public class TextSentence {
	public String sentence = null;
	public ArrayList<TextWord> taggedWords = null;

	public TextConcept subject = null;
	public TextConcept object = null;
	public TextVerb verb = null;

	public TextSentence(String sentence) {
		this.sentence = sentence;
		taggedWords = new ArrayList<>();
		
		subject = new TextConcept();
		object = new TextConcept();
		verb = new TextVerb();
	}

}
