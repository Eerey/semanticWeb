package ontology;

import java.util.ArrayList;

import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.vocabulary.XSD;

import ontology.model.TextConceptAbstractProperty;
import ontology.model.TextConceptDatatypeProperty;
import ontology.model.TextConceptIndividual;
import ontology.model.TextConceptObjectProperty;
import ontology.model.TextSentence;

public class OntologyWeaver {

	private OntologyHelper ontologyHelper;
	private String ontologyName;

	public OntologyWeaver(String ontologyName) {
		this.ontologyName = ontologyName;
	}

	public void process(ArrayList<TextSentence> sentences) {
		ontologyHelper = new OntologyHelper(ontologyName);
		for (TextSentence sentence : sentences) {
			constructOntologyResources(sentence);
		}
		writeOntologyToDisk();
	}

	private void writeOntologyToDisk() {
		ontologyHelper.writeOntologyToDisk();
	}

	private void constructOntologyResources(TextSentence sentence) {
		// create classes
		OntClass subject = ontologyHelper.createClass(sentence.subject.className);
		OntClass object = ontologyHelper.createClass(sentence.object.className);

		// create object properties
		ObjectProperty verbObjectProperty = ontologyHelper.createObjectProperty(sentence.verb.infinitive);
		verbObjectProperty.addDomain(subject);
		verbObjectProperty.addRange(object);
		verbObjectProperty.addLabel(sentence.verb.infinitive, "en");

		// TODO Invers-Property müsste, wenn Sie gebraucht wird auch
		// abgespeichert werden
		verbObjectProperty.getInverse();

		// create range OntClasses
		for (TextConceptAbstractProperty<?> abstractProperties : sentence.subject.properties.values()) {
			if (abstractProperties instanceof TextConceptObjectProperty) {
				TextConceptObjectProperty property = (TextConceptObjectProperty) abstractProperties;
				ontologyHelper.createClass(property.range.className);
			}
		}

		// create Object Properties (range classes are already available)
		for (TextConceptAbstractProperty<?> abstractProperties : sentence.subject.properties.values()) {
			OntProperty newObjectProperty = null;
			if (abstractProperties instanceof TextConceptObjectProperty) {
				newObjectProperty = ontologyHelper.createObjectProperty(abstractProperties.label);
				newObjectProperty.addRange(ontologyHelper.getResource(((TextConceptObjectProperty) abstractProperties).range.className));
			}
			if (abstractProperties instanceof TextConceptDatatypeProperty) {
				newObjectProperty = ontologyHelper.createDatatypeProperty(abstractProperties.label);
				switch (((TextConceptDatatypeProperty) abstractProperties).range) {
				case "String":
					newObjectProperty.addRange(XSD.xstring);
					break;
				case "Integer":
					newObjectProperty.addRange(XSD.integer);
					break;
				default:
					break;
				}
			}
			newObjectProperty.addLabel(abstractProperties.label, "en");
			newObjectProperty.addDomain(ontologyHelper.getResource(abstractProperties.domain));
		}

		// create individuals
		if (!sentence.subject.individuals.values().isEmpty()) {
			for (TextConceptIndividual individual : sentence.subject.individuals.values()) {
				ontologyHelper.createIndividual(sentence.subject.className, individual.individualName);
			}
		}

	}

}
