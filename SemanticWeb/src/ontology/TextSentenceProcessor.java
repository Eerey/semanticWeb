package ontology;

import java.util.ArrayList;

import ontology.model.TextConcept;
import ontology.model.TextSentence;
import ontology.model.TextWord;
import ontology.opennlp.SentenceSplitter;
import ontology.opennlp.WordTokenizer;

public class TextSentenceProcessor {

	public ArrayList<TextSentence> textSentences;
	private TextSentencePatternFinder patternFinder;
	private WordAnalyzer dictionary = null;
	private WordTokenizer tokenizer = null;
	private SentenceSplitter sentenceSplitter;

	public TextSentenceProcessor() {
		sentenceSplitter = SentenceSplitter.getSentenceSplitter();
		patternFinder = new TextSentencePatternFinder();
		dictionary = new WordAnalyzer();
		tokenizer = WordTokenizer.getWordTokenizer();
	}

	public void process(String text) {
		textSentences = sentenceSplitter.splitSentences(text);

		for (TextSentence textSentence : textSentences) {
			textSentence.patternName = patternFinder.findPattern(textSentence.sentence);
			textSentence.taggedWords = tokenizer.tokenizeText(textSentence.sentence);

			// determine subject
			determineSubject(textSentence);
			//findet das erste Nomen, sollte aber einen zusammenhängenden Terminus wie "Marwin Lebensky" erkennen können
			
			// determine verb/predicate
			determineVerb(textSentence);
			// determine object
			determineObject(textSentence);

			// determine properties/relations
		}
	}

	/**
	 * überprüft, ob className ein möglicher Vorname ist <BR>
	 */
	private boolean processPossibleIndividuals(TextConcept concept) {

		if ( dictionary.checkIfExistingName(concept.className) == true) {
			concept.isIndividual = true;
			concept.individualName = concept.className;
			concept.className = "Person";
		} else {
			concept.isIndividual = false;
		}

		return false;
	}

	private void determineSubject(TextSentence textSentence) {
		for (TextWord word : textSentence.taggedWords) {
			if (word.tag.contains("NNP") || word.tag.contains("NN")) {

				textSentence.subject = new TextConcept();
				textSentence.subject.className = word.word;
				processPossibleIndividuals(textSentence.subject);
				System.out.println("Subject found: " + textSentence.subject.className);
				int temp = textSentence.taggedWords.lastIndexOf(word);
				temp++;
				System.out.println(textSentence.taggedWords.get(temp));
				
				
				
				textSentence.taggedWords.remove(word);
				break;
			}
		}
	}

	private void determineVerb(TextSentence textSentence) {
		for (TextWord word : textSentence.taggedWords) {
			if (word.tag.contains("VBZ") || word.tag.contains("VB")) {
				// subject = new TextConcept();
				// subject.className = word.word;
				// taggedWords.remove(word);
				System.out.println("Verb found: " + word.word);
				break;
			}
		}
	}

	private void determineObject(TextSentence textSentence) {
		for (TextWord word : textSentence.taggedWords) {
			if (word.tag.contains("NN") || word.tag.contains("NNP")) {
				textSentence.object = new TextConcept();
				textSentence.object.className = word.word;
				processPossibleIndividuals(textSentence.object);
				System.out.println("Object found: " + textSentence.object.className);
				textSentence.taggedWords.remove(word);
				break;
			}
		}
	}

}
