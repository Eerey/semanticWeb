package ontology;

import java.util.HashMap;

public class TextSentencePattern {

	public HashMap<String,String> patterns;
	
	public TextSentencePattern(){
		 
	}
	public String findPattern(String sentence){
		if (sentence.contains("is a")) return "ownership";
		if (sentence.contains("owns a")) return "ownership";
		if (sentence.contains("is owned by")) return "ownership";
		if (sentence.contains("is a species of")) return "hierarchySubclass";
		if (sentence.contains("is a subclass of")) return "hierarchySubclass";
		if (sentence.contains("is a specification of")) return "hierarchySubclass";
		if (sentence.contains("is a superclass of")) return "hierarchySuperclass";
		return null;
	}
	
	
}
