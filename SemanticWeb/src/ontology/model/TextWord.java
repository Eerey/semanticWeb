package ontology.model;

public class TextWord {
	public String word;
	public String tag;

	@Override
	public String toString() {
		return word + " - " + tag;
	}
}
