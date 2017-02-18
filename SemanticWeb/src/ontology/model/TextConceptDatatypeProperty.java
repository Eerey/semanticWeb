package ontology.model;

public class TextConceptDatatypeProperty extends TextConceptAbstractProperty<String> {

	public TextConceptDatatypeProperty(String label, String domain, String range, int cardinality, boolean hasCardinality, boolean minCardinality) {
		super(label,domain,range,cardinality,hasCardinality,minCardinality);
	}

	public TextConceptDatatypeProperty(String label, String domain, String range) {
		this(label, domain, range, 0, false, false);
	}

}
