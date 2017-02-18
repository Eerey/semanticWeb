package ontology;

import java.util.ArrayList;

public class TextSentence {
	public String sentence = null;
	public ArrayList<TextSentence> contextSentences = null;
	public ArrayList<String> words = null;

	public TextConcept subject = null;
	public TextConcept object = null;
	public TextVerb verb = null;
	public String tense = null;
	public TextSentencePattern pattern = null;
	public String patternName = null;

	public WordAnalyzer dictionary = null;

	public boolean hasHierarchy = false;
	public boolean hasIndividuals = false;
	public boolean hasDatatypeProperties = false;
	public boolean hasObjectProperties = false;

	public TextSentence(String sentence) {
		this.sentence = sentence;
		String[] tempWords = sentence.split(" ");
		this.words = new ArrayList<String>();
		for (String w : tempWords){
			this.words.add(w);
		}
		this.pattern = new TextSentencePattern();
		this.patternName = pattern.findPattern(sentence);
		for (String s : words)
			System.out.println(s);
		this.dictionary = new WordAnalyzer();
		// determine subject
//		determineSubject();
		// determine verb/predicate
		determineVerb();
		// determine object
//		determineObject();
	}

	public void sleep(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void determineSubject() {
		for (String word : words) {
			sleep(1);
			TextConcept subject = dictionary.checkIfHumanName(word);
			if (subject != null) {
				this.subject = subject;
				words.remove(word);
				break;
			}
		}
	}
	public void determineVerb() {
		for (String word : words) {
			if (word.length()==1) continue;
			sleep(1);
			TextVerb verb = dictionary.findVerb(word);
			if (verb != null) {
				this.verb = verb;
				words.remove(word);
				break;
			}
		}
	}
	public void determineObject() {
		for (String word : words) {
			sleep(1);
			TextConcept object = dictionary.checkIfHumanName(word);
			if (object != null) {
				this.object = object;
				words.remove(word);
				break;
			}
		}
	}

	

	public static void main(String[] args) {
		TextSentence ts = new TextSentence("Dieser Marwin ist ein super krasser Mensch");
//		ts.determineSubject();
		String t = ts.subject.className;
		System.out.println(ts.subject.className);
	}

}
