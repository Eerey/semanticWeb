package ontology;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;

import ontology.model.TextConceptAbstractProperty;
import ontology.model.TextConceptDatatypeProperty;
import ontology.model.TextConceptIndividual;
import ontology.model.TextConceptObjectProperty;
import ontology.model.TextSentence;

public class TextToOntologyWeaver {

	public ArrayList<TextSentence> sentences;
	public OntologyHelper ontologyHelper;
	public HashMap<String,Resource> resources;
	
	public TextToOntologyWeaver(String input, String ontologyName){
		TextSentenceProcessor processor = new TextSentenceProcessor();
		processor.process(input);
		
		this.sentences = processor.textSentences; //kann bis jetzt nur einen Satz

		ontologyHelper = new OntologyHelper(ontologyName);
		
		for(TextSentence sentence : sentences){
			printInformation(sentence);
			
			cleanString(sentence);
			
			constructOntologyResources(sentence);
		}
		
		writeOntologyToDisk();
	}

	public void writeOntologyToDisk() {
		ontologyHelper.writeOntologyToDisk();
	}

	private void printInformation(TextSentence sentence) {
		System.out.println(sentence.subject.className+":"+sentence.verb.infinitive+":"+sentence.object.className);
		System.out.print("Unused words: ");
		for(int i = 0; i<sentence.taggedWords.size(); i++)
			System.out.print("\""+sentence.taggedWords.get(i).word+"\" ");
		System.out.println();
	}
	
	public void cleanString(TextSentence sentence){
		sentence.subject.className = sentence.subject.className.replace(".", "");
		sentence.object.className = sentence.object.className.replace(".", "");
		sentence.verb.originalVerb = sentence.verb.originalVerb.replace(".", "");
	}
	
	public void constructOntologyResources(TextSentence sentence){
		// create classes
		OntClass subject = ontologyHelper.createClass(sentence.subject.className);
		OntClass object = ontologyHelper.createClass(sentence.object.className);
		
		// create object properties
		ObjectProperty verbObjectProperty = ontologyHelper.createObjectProperty(sentence.verb.infinitive);
		verbObjectProperty.addDomain(subject);
		verbObjectProperty.addRange(object);
		verbObjectProperty.addLabel(sentence.verb.infinitive, "de");
		
		//TODO Invers-Property müsste, wenn Sie gebraucht wird auch abgespeichert werden
		verbObjectProperty.getInverse();
		
		// create range OntClasses
		for(TextConceptAbstractProperty<?> abstractProperties : sentence.subject.properties.values()){
			if(abstractProperties instanceof TextConceptObjectProperty){
				TextConceptObjectProperty property  = (TextConceptObjectProperty) abstractProperties;
				ontologyHelper.createClass(property.range.className);
			}
		}
		
		// create Object Properties (range classes are already available)
		for(TextConceptAbstractProperty<?> abstractProperties : sentence.subject.properties.values()){
			OntProperty newObjectProperty = null;
			if(abstractProperties instanceof TextConceptObjectProperty){
				newObjectProperty = ontologyHelper.createObjectProperty(abstractProperties.label);
				newObjectProperty.addRange(ontologyHelper.getResource(((TextConceptObjectProperty)abstractProperties).range.className));
			}
			if(abstractProperties instanceof TextConceptDatatypeProperty){
				newObjectProperty = ontologyHelper.createDatatypeProperty(abstractProperties.label);
				switch (((TextConceptDatatypeProperty)abstractProperties).range){
					case "String": newObjectProperty.addRange(XSD.xstring);
						break;
					case "Integer": newObjectProperty.addRange(XSD.integer);
						break;
					default: break;
				}
			}
			newObjectProperty.addLabel(abstractProperties.label, "de");
			newObjectProperty.addDomain(ontologyHelper.getResource(abstractProperties.domain));
		}

		// create individuals
		if (!sentence.subject.individuals.values().isEmpty()){
			for (TextConceptIndividual individual : sentence.subject.individuals.values()){
				ontologyHelper.createIndividual(sentence.subject.className,individual.individualName);
			}
		}
		
		
	}
	
}
