package ontology;

import java.util.ArrayList;

import ontology.model.TextConcept;
import ontology.model.TextConceptDatatypeProperty;
import ontology.model.TextConceptIndividual;
import ontology.model.TextSentence;
import ontology.model.TextWord;
import ontology.opennlp.NameFinder;
import ontology.opennlp.SentenceSplitter;
import ontology.opennlp.WordTagger;

public class TextProcessor {

	public ArrayList<TextSentence> textSentences;
	private Dictionary dictionary = null;
	private WordTagger tokenizer = null;
	private NameFinder personNameFinder;
	private SentenceSplitter sentenceSplitter;
	

	public TextProcessor() {
		sentenceSplitter = SentenceSplitter.getSentenceSplitter();
		dictionary = new Dictionary();
		tokenizer = WordTagger.getWordTokenizer();
		personNameFinder = NameFinder.getPersonNameFinder();
	}

	
	public ArrayList<TextSentence> process(String text) {
		textSentences = sentenceSplitter.splitSentences(text);

		for (TextSentence textSentence : textSentences) {
			
			textSentence.taggedWords = tokenizer.tokenizeText(textSentence.sentence);
			
			// determine subject
			determineSubject(textSentence);
			//findet das erste Nomen, sollte aber einen zusammenhängenden Terminus wie "Marwin Lebensky" erkennen können
			
			// determine verb/predicate
			determineVerb(textSentence);
			// determine object
			determineObject(textSentence);

			// determine properties/relations

			cleanString(textSentence);
			printInformation(textSentence);
		}
		return textSentences;
	}
	
	private void determineSubject(TextSentence textSentence) {
		ArrayList<TextWord> words = textSentence.taggedWords;
 		for (int i = 0; i<words.size(); i++) {
			if (words.get(i).tag.contains("NNP") || words.get(i).tag.contains("NN")) {
				boolean isPersonName = personNameFinder.isName(textSentence, i);
				if (isPersonName){
					createPerson(textSentence, words.get(i), i,textSentence.subject);
				}
				else{
					// not known entity
					textSentence.subject.className = words.get(i).word;
				}
			
				textSentence.taggedWords.remove(words.get(i));
				break;
			}
		}
	}

	private void createPerson(TextSentence textSentence, TextWord word, int i, TextConcept concept) {
		concept.className = "Person";
		// add datatype property surname
		concept.addProperty(new TextConceptDatatypeProperty("surname", textSentence.subject.className, "String"),"unknown");
		// add datatype property gender
		String gender = dictionary.getGender(word.word);
		concept.addProperty(new TextConceptDatatypeProperty("gender", textSentence.subject.className, "String"),"unknown");
		
		TextConceptIndividual person = new TextConceptIndividual(word.word);
		person.addPropertyKeyValue("gender", gender);
		person.addPropertyKeyValue("surname", word.word);
		
		concept.addIndividual(person);
	}

	private void determineVerb(TextSentence textSentence) {
		for (TextWord word : textSentence.taggedWords) {
			if (word.tag.contains("VBZ") || word.tag.contains("VB")) {
				textSentence.verb = dictionary.findVerb(word.word);
				textSentence.taggedWords.remove(word);
				break;
			}
		}
	}

	private void determineObject(TextSentence textSentence) {
		ArrayList<TextWord> words = textSentence.taggedWords;
 		for (int i = 0; i<words.size(); i++) {
			if (words.get(i).tag.contains("NNP") || words.get(i).tag.contains("NN")) {
				boolean isPersonName = personNameFinder.isName(textSentence, i);
				if (isPersonName){
					createPerson(textSentence, words.get(i), i, textSentence.object);
				}
				else{
					// not known entity
					textSentence.object.className = words.get(i).word;
				}
				
				textSentence.taggedWords.remove(words.get(i));
				break;
			}
		}
	}
	
	public void cleanString(TextSentence sentence){
		sentence.subject.className = sentence.subject.className.replace(".", "");
		sentence.object.className = sentence.object.className.replace(".", "");
		sentence.verb.originalVerb = sentence.verb.originalVerb.replace(".", "");
	}

	private void printInformation(TextSentence sentence) {
		System.out.println(sentence.subject.className+":"+sentence.verb.infinitive+":"+sentence.object.className);
		System.out.print("Unused words: ");
		for(int i = 0; i<sentence.taggedWords.size(); i++)
			System.out.print("\""+sentence.taggedWords.get(i).word+"\" ");
		System.out.println();
	}
	
}
