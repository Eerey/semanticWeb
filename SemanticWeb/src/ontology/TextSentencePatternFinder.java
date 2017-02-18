package ontology;

public class TextSentencePatternFinder {

	public String findPattern(String sentence){
		checkForName();
		if (sentence.contains("is a")) return "equal"; //was ist mit namen und individuals?
		if (sentence.contains("owns a")) return "ownership";
		if (sentence.contains("is owned by")) return "ownership";
		if (sentence.contains("is a species of")) return "hierarchySubclass";
		if (sentence.contains("is a subclass of")) return "hierarchySubclass";
		if (sentence.contains("is a specification of")) return "hierarchySubclass";
		if (sentence.contains("is a part of")) return "hierarchySubclass";
		if (sentence.contains("is part of")) return "hierarchySubclass";
		if (sentence.contains("are a part of")) return "hierarchySubclass";
		if (sentence.contains("are part of")) return "hierarchySubclass";
		if (sentence.contains("is a superclass of")) return "hierarchySuperclass";
		return null;
	}

	public void checkForName(){

	}
	
	
}
