package ontology.test;

import ontology.model.TextWord;
import ontology.opennlp.WordTagger;

public class WordTaggerTest {
	
	public static void main(String[] args) {
		WordTagger tagger = WordTagger.getWordTokenizer();
		for(TextWord tw : tagger.tokenizeText("I like to go by train.")){
			System.out.println(tw.word+"_"+tw.tag);
		}
	}

}
