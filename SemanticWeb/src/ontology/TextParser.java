package ontology;

import java.util.ArrayList;

public class TextParser {
	
	public TextParser(){

//		splitSentences();
	}
	public static ArrayList<TextSentence> splitSentences(String originalText){
		ArrayList<TextSentence> sentences = new ArrayList<TextSentence>();
		//TODO: Split Function. Vorsicht vor: Mr. Lebensky is a human. - dass er nicht bei "Mr." splittet
		sentences.add(new TextSentence(originalText));
		return sentences;
	}
}
