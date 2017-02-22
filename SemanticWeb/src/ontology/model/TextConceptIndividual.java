package ontology.model;

import java.util.HashMap;

public class TextConceptIndividual {
	public HashMap<String,String> propertyKeyValues;
	public String individualName;
	
	public TextConceptIndividual(String individualName){
		this.individualName = individualName;
		propertyKeyValues = new HashMap<>();
	}
	
	public void addPropertyKeyValue(String key, String value){
		propertyKeyValues.put(key, value);
	}
	
	public String getValue(String key){
		return (propertyKeyValues.get(key) != null ) ? propertyKeyValues.get(key) : "undefined";
	}
	
}
