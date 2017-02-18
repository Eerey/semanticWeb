package ontology.model;

public abstract class TextConceptAbstractProperty<T> {
	public String label;
	public String domain;
	public T range;
	public int cardinality;
	public boolean hasCardinality;
	public boolean minCardinality;
	
	public TextConceptAbstractProperty(String label, String domain, T range, int cardinality, boolean hasCardinality, boolean minCardinality) {
		this.label = label;
		this.domain = domain;
		this.range = range;
		this.cardinality = cardinality;
		this.hasCardinality = hasCardinality;
		this.minCardinality = minCardinality;
	}

	public TextConceptAbstractProperty(String label, String domain, T range) {
		this(label, domain, range, 0, false, false);
	}

}
