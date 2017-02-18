package ontology;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class TextParser {

	private SentenceDetectorME detector;
	private WordTokenizer wordTokenizer;

	public TextParser(){
		try {
			InputStream inputStream = new FileInputStream("opennlp-bins/en-sent.bin");
			SentenceModel model = new SentenceModel(inputStream);
			// Instantiating POSTaggerME class
			detector = new SentenceDetectorME(model);
			wordTokenizer = new WordTokenizer();
		} catch (Exception e) {
			System.out.println("Text parser could not be initialized");
			System.exit(4);
		}
	}
	
	public ArrayList<TextSentence> splitSentences(String originalText){

		String sentences[] = detector.sentDetect(originalText); 
	    
		ArrayList<TextSentence> textSentences = new ArrayList<>();
	    for(String sentence : sentences){
	    	TextSentence textSentence = new TextSentence(sentence);
	    	textSentence.taggedWords = wordTokenizer.tokenizeText(sentence);
	    	textSentences.add(textSentence);
	    }
		
		return textSentences;
	}
}
