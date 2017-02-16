package tutorial;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;

public class Example01 {
	public static void main(String args[]) {

		Model model = ModelFactory.createDefaultModel();

		Resource alice = ResourceFactory.createResource("http://example.org/alice");

		Resource bob = ResourceFactory.createResource("http://example.org/bob");
		model.add(alice, RDF.type, FOAF.Person);
		model.add(alice, FOAF.name, "Alice");
		model.add(alice, FOAF.mbox, ResourceFactory.createResource("mailto:alice@example.org"));
		model.add(alice, FOAF.knows, bob);

		model.write(System.out, "TURTLE");
	}
}
