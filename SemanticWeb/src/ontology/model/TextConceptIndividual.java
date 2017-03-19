package ontology.model;

import java.util.HashMap;

public class TextConceptIndividual {
	public HashMap<String,Object> propertyKeyValues;
	public String individualName;
	
	public TextConceptIndividual(String individualName){
		this.individualName = individualName;
		propertyKeyValues = new HashMap<>();
	}
	
	public void addPropertyKeyValue(String key, Object value){
		propertyKeyValues.put(key, value);
	}
	
	public Object getValue(String key){
		return (propertyKeyValues.get(key) != null ) ? propertyKeyValues.get(key) : "undefined";
	}
	
}
