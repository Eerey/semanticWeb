package ontology.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import ontology.model.TextWord;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

// http://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
//Tabelle unterhalb der Klasse
public class WordTokenizer {

	private static WordTokenizer wordTokenizer;
	private POSTaggerME tagger;

	private WordTokenizer() {
		try {
			InputStream inputStream = new FileInputStream("opennlp-bins/en-pos-maxent.bin");
			POSModel model = new POSModel(inputStream);
			// Instantiating POSTaggerME class
			tagger = new POSTaggerME(model);
		} catch (Exception e) {
			System.out.println("Word Tokenizer could not be initialized");
			System.exit(3);
		}
	}

	public ArrayList<TextWord> tokenizeText(String sentence) {
		// Tokenizing the sentence using WhitespaceTokenizer class
		WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
		String[] tokens = whitespaceTokenizer.tokenize(sentence);

		
		// Generating tags
		String[] tags = tagger.tag(tokens);

		ArrayList<TextWord> textWords = new ArrayList<>();
		int length = tags.length;
		for (int i = 0; i < length; i++) {
			TextWord textWord = new TextWord();
			textWord.word = tokens[i];
			textWord.tag = tags[i];
			textWords.add(textWord);
			System.out.println(textWord);
		}
		System.out.println("---");
		return textWords;
	}
	
	public static WordTokenizer getWordTokenizer(){
		if(wordTokenizer == null)
			wordTokenizer = new WordTokenizer();
		return wordTokenizer;
	}
}

//1.	CC	Coordinating conjunction
//2.	CD	Cardinal number
//3.	DT	Determiner
//4.	EX	Existential there
//5.	FW	Foreign word
//6.	IN	Preposition or subordinating conjunction
//7.	JJ	Adjective
//8.	JJR	Adjective, comparative
//9.	JJS	Adjective, superlative
//10.	LS	List item marker
//11.	MD	Modal
//12.	NN	Noun, singular or mass
//13.	NNS	Noun, plural
//14.	NNP	Proper noun, singular
//15.	NNPS	Proper noun, plural
//16.	PDT	Predeterminer
//17.	POS	Possessive ending
//18.	PRP	Personal pronoun
//19.	PRP$	Possessive pronoun
//20.	RB	Adverb
//21.	RBR	Adverb, comparative
//22.	RBS	Adverb, superlative
//23.	RP	Particle
//24.	SYM	Symbol
//25.	TO	to
//26.	UH	Interjection
//27.	VB	Verb, base form
//28.	VBD	Verb, past tense
//29.	VBG	Verb, gerund or present participle
//30.	VBN	Verb, past participle
//31.	VBP	Verb, non-3rd person singular present
//32.	VBZ	Verb, 3rd person singular present
//33.	WDT	Wh-determiner
//34.	WP	Wh-pronoun
//35.	WP$	Possessive wh-pronoun
//36.	WRB	Wh-adverb
