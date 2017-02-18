package ontology;

public class TextConceptObjectProperty extends TextConceptAbstractProperty<TextConcept> {

	public TextConceptObjectProperty(String label, String domain, TextConcept range, int cardinality, boolean hasCardinality, boolean minCardinality) {
		super(label,domain,range,cardinality,hasCardinality,minCardinality);
	}

	public TextConceptObjectProperty(String label, String domain, TextConcept range) {
		this(label, domain, range, 0, false, false);
	}

}
