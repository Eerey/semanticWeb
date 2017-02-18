package ontology;

import java.util.ArrayList;

public class TextSentence {
	public String sentence = null;
	public ArrayList<TextSentence> contextSentences = null;
//	public ArrayList<String> words = null;
	public ArrayList<TextWord> taggedWords = null;

	public TextConcept subject = null;
	public TextConcept object = null;
	public TextVerb verb = null;
	public String tense = null;
	public TextSentencePattern pattern = null;
	public String patternName = null;

	public WordAnalyzer dictionary = null;
	public WordTokenizer tokenizer = null;

	public boolean hasHierarchy = false;
	public boolean hasIndividuals = false;
	public boolean hasDatatypeProperties = false;
	public boolean hasObjectProperties = false;

	public TextSentence(String sentence) {
		this.sentence = sentence;
//		String[] tempWords = sentence.split(" ");
//		this.words = new ArrayList<String>();
//		for (String w : tempWords){
//			this.words.add(w);
//		}
		this.pattern = new TextSentencePattern();
		this.patternName = pattern.findPattern(sentence);
		System.out.println(patternName);
//		for (String s : words) System.out.println(s);
		this.dictionary = new WordAnalyzer();
		
		// determine tokens
		taggedWords = new ArrayList<TextWord>();
		tokenizer = new WordTokenizer();
		taggedWords = tokenizer.tokenizeText(sentence);
		
		// determine subject
		determineSubject();
		// determine verb/predicate
		determineVerb();
		// determine object
		determineObject();
		
		// determine properties/relations
		
	}
	/**
	 * überprüft, ob className ein möglicher Vorname ist <BR>
	 */
	public boolean processPossibleIndividuals(TextConcept concept){
		
		if (dictionary.checkIfExistingName(concept.className)==true){
			concept.isIndividual=true;
			concept.individualName=concept.className;
			concept.className="Person";
		} else {
			concept.isIndividual=false;
		}
		
		return false;
	}

	
	public void determineSubject() {
		for (TextWord word : taggedWords) {
			if(word.tag.contains("NNP")||word.tag.contains("NN")){
				
				subject = new TextConcept();
				subject.className = word.word;
				processPossibleIndividuals(subject);
				System.out.println("Subject found: " + subject.className);
				taggedWords.remove(word);
				break;
			}
		}
	}
	public void determineVerb() {
		for (TextWord word : taggedWords) {
			if(word.tag.contains("VBZ")||word.tag.contains("VB")){
//				subject = new TextConcept();
//				subject.className = word.word;
//				taggedWords.remove(word);
				System.out.println("Verb found: " + word.word);
				break;
			}
		}
	}
	public void determineObject() {
		for (TextWord word : taggedWords) {
			if(word.tag.contains("NN")){
				object = new TextConcept();
				object.className = word.word;
				processPossibleIndividuals(object);
				System.out.println("Object found: " + object.className);
				taggedWords.remove(word);
				break;
			}
		}
	}
	
	
	
	public void determineSubjectOld() {
		for (TextWord word : taggedWords) {
//			TextConcept subject = dictionary.checkIfHumanName(word.word);
			if (subject != null) {
				this.subject = subject;
				taggedWords.remove(word);
				break;
			}
		}
	}
	public void determineVerbOld() {
		for (TextWord word : taggedWords) {
			TextVerb verb = dictionary.findVerb(word.word);
			if (verb != null) {
				this.verb = verb;
				taggedWords.remove(word);
				System.out.println(verb.infinitive + " is the infinitive of the verb '"+word+"'");
				break;
			} else {
				System.out.println(word+" could not be determined as a verb");
			}
		}
	}
	public void determineObjectOld() {
		for (TextWord word : taggedWords) {
//			TextConcept object = dictionary.checkIfHumanName(word.word);
			if (object != null) {
				this.object = object;
				taggedWords.remove(word);
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
