package ontology.test;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.XSD;

import ontology.OntologyHelper;

public class OntologyHelperTest {

	// public final String nameSpace = "http://example.com/example#";
		// public final String nameSpace =
		// "http://protege.stanford.edu/ontologies/pizza/pizza.owl#";
	
	private OntologyHelper ontologyHelper;
	
	public OntologyHelperTest() {
		ontologyHelper = new OntologyHelper("NewOntology");
		testSequence01();
		ontologyHelper.printOntology();
		ontologyHelper.writeOntologyToDisk();
	}
	
	public void testSequence01() {
		ontologyHelper.createClass("Animal");
		OntClass doge = ontologyHelper.createClass("Doge");
		OntClass shibaInu = ontologyHelper.createClass("ShibaInu");
		OntClass doberman = ontologyHelper.createClass("Doberman");
		((OntClass) ontologyHelper.getResource("Animal")).addSubClass(doge);
		((OntClass) ontologyHelper.getResource("Doge")).addSubClass(shibaInu);
		((OntClass) ontologyHelper.getResource("Doge")).addSubClass(doberman);

		ObjectProperty op_canBark = ontologyHelper.createObjectProperty("canBarkWith");
		op_canBark.addDomain(doge);
		op_canBark.addRange(ontologyHelper.createClass("muzzle"));
		op_canBark.addLabel("Kann bellen. Wuff!", "de");

		DatatypeProperty dp_name = ontologyHelper.createDatatypeProperty("dogName");
		dp_name.addDomain(doge);
		dp_name.setRange(XSD.xstring); // com.hp.hpl.jena.vocabulary.XSD
		dp_name.addLabel("Kann bellen. Wuff!", "de");

		doge.addProperty(dp_name, "beschreibender Kram");

		shibaInu.setDisjointWith(doberman);
		doberman.setDisjointWith(shibaInu);
		ontologyHelper.createIndividual("Doberman", "MasterBlaster");

		// model.createStatement(doge,bark,"Loud Bark");

		// Statement stmt = iter.nextStatement(); // get next statement
		// Resource subject = stmt.getSubject(); // get the subject
		// Property predicate = stmt.getPredicate(); // get the predicate
		// RDFNode object = stmt.getObject(); // get the object
	}
	


	/**
	 * 0: "RDF/XML-ABBREV" <BR>
	 * 1: "N-Triples" <BR>
	 * 2: "TriG" <BR>
	 * 3: "N-Quads" <BR>
	 * 4: "TURTLE" <BR>
	 * 5: "RDF/JSON" <BR>
	 */
	public void printOntology(int i) {
		switch (i) {
		case 0:
			ontologyHelper.model.write(System.out, "RDF/XML-ABBREV");
			break;
		case 1:
			ontologyHelper.model.write(System.out, "N-Triples");
			break;
		case 2:
			ontologyHelper.model.write(System.out, "TriG");
			break;
		case 3:
			ontologyHelper.model.write(System.out, "N-Quads");
			break;
		case 4:
			ontologyHelper.model.write(System.out, "TURTLE");
			break;
		case 5:
			ontologyHelper.model.write(System.out, "RDF/JSON");
			break;
		}
	}
	


	public void readAllStatements() {
		// list the statements in the Model
		StmtIterator iter = ontologyHelper.model.listStatements();
		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement(); // get next statement
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object

			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				System.out.print(object.toString());
			} else {
				// object is a literal
				System.out.print(" \"" + object.toString() + "\"");
			}
			System.out.println(" .");
		}
	}

	
}
