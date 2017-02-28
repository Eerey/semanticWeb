package ontology.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TextConcept {

	public String className;
	public HashMap<String, TextConceptAbstractProperty<?>> properties;
	public ArrayList<TextConcept> referenceConcept;
	public HashMap<String, TextConceptIndividual> individuals;

	public TextConcept() {
		this("unknown");
	}

	public TextConcept(String className) {
		this.className = className;
		this.properties = new HashMap<>();
		individuals = new HashMap<>();
	}
	
	public void addProperty(TextConceptAbstractProperty<?> property, String defaultValue){
		properties.put(property.label, property);
		for(TextConceptIndividual individual : individuals.values())
			individual.addPropertyKeyValue(property.label, defaultValue);
	}
	
	public void addIndividual(TextConceptIndividual individual){
		individuals.put(individual.individualName, individual);
	}

}
