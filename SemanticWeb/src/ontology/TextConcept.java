package ontology;

import java.util.ArrayList;

public class TextConcept {

	public String className;
	public ArrayList<TextConceptAbstractProperty<?>> properties;
	public boolean isIndividual;
	public String individualName;
	public TextVerb verb;
	public boolean verbIsInverse;
	public ArrayList<TextConcept> referenceConcept;

	public TextConcept() {
		this("unknown");
	}

	public TextConcept(String className) {
		this.className = className;
		this.properties = new ArrayList<TextConceptAbstractProperty<?>>();
	}

}
