package ontology;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TextSentence {
	public String sentence = null;
	public ArrayList<String> contextSentences = null;
	public String[] words = null;

	public String subject = null;
	public String object = null;
	public String verb = null;
	public String tense = null;
	public ArrayList<String> subjectAtributes = null; // Adjektive
	public ArrayList<String> objectAttributes = null; // Adjektive
	public ArrayList<String> verbAttributes = null; // Adverben
	public TextSentencePattern pattern = null;
	public String patternName = null;

	public boolean hasHierarchy = false;
	public boolean hasIndividuals = false;
	public boolean hasDatatypeProperties = false;
	public boolean hasObjectProperties = false;

	public TextSentence(String sentence) {
		this.sentence = sentence;
		this.words = sentence.split(" ");
		this.pattern = new TextSentencePattern();
		this.patternName = pattern.findPattern(sentence);
		for (String s : words)
			System.out.println(s);
	}

	public static void main(String[] args) {


	}

}
