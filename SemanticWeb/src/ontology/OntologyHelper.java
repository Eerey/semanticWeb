package ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.XSD;

public class OntologyHelper {

	// public final String nameSpace = "http://example.com/example#";
	// public final String nameSpace =
	// "http://protege.stanford.edu/ontologies/pizza/pizza.owl#";
	public final String nameSpace = "http://ontology/test#";
	public OntModel model;
	ArrayList<Resource> resources = null;
	ArrayList<OntClass> classes = null;

	public OntologyHelper() {
		initOntologyModel();
		testSequence01();
		printOntology();
		writeOntologyToDisk();
	}

	public void initOntologyModel() {
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RDFS_INF);
		String uri = nameSpace;
		try {
			model.read(uri, null);
		} catch (Exception e) {
			System.out.println("The ontology was not found or could not be read. Please check the namespace.");
		}
	}

	public void testSequence01() {
		OntClass animal = createClass("Animal");
		OntClass doge = createClass("Doge");
		OntClass shibaInu = createClass("ShibaInu");
		OntClass doberman = createClass("Doberman");
		getClass("Animal").addSubClass(doge);
		getClass("Doge").addSubClass(shibaInu);
		getClass("Doge").addSubClass(doberman);

		ObjectProperty op_canBark = createObjectProperty("canBarkWith");
		op_canBark.addDomain(doge);
		op_canBark.addRange(createClass("muzzle"));
		op_canBark.addLabel("Kann bellen. Wuff!", "de");

		DatatypeProperty dp_name = createDatatypeProperty("dogName");
		dp_name.addDomain(doge);
		dp_name.setRange(XSD.xstring); // com.hp.hpl.jena.vocabulary.XSD
		dp_name.addLabel("Kann bellen. Wuff!", "de");

		doge.addProperty(dp_name, "beschreibender Kram");

		shibaInu.setDisjointWith(doberman);
		doberman.setDisjointWith(shibaInu);
		// doberman.setDisjointWith(animal);
		createIndividual("Doberman", "MasterBlaster");

		// model.createStatement(doge,bark,"Loud Bark");

		// Statement stmt = iter.nextStatement(); // get next statement
		// Resource subject = stmt.getSubject(); // get the subject
		// Property predicate = stmt.getPredicate(); // get the predicate
		// RDFNode object = stmt.getObject(); // get the object
	}

	public ObjectProperty createObjectProperty(String objectPropertyName) {
		return model.createObjectProperty(nameSpace + objectPropertyName);
	}

	public Property createProperty(String propertyName) {
		return model.createProperty(nameSpace + propertyName);
	}

	public DatatypeProperty createDatatypeProperty(String datatypePropertyName) {
		return model.createDatatypeProperty(nameSpace + datatypePropertyName);
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
			model.write(System.out, "RDF/XML-ABBREV");
			break;
		case 1:
			model.write(System.out, "N-Triples");
			break;
		case 2:
			model.write(System.out, "TriG");
			break;
		case 3:
			model.write(System.out, "N-Quads");
			break;
		case 4:
			model.write(System.out, "TURTLE");
			break;
		case 5:
			model.write(System.out, "RDF/JSON");
			break;
		}
	}

	public void printOntology() {
		model.write(System.out, "RDF/XML-ABBREV");
	}

	public Individual createIndividual(String className, String individualName) {
		OntClass foo = model.getOntClass(nameSpace + className);
		Individual fubar = foo.createIndividual(nameSpace + individualName);
		return fubar;
	}

	/**
	 * The class to be created. Classes are unique in OntModel and will be
	 * overwritten if used more than once.
	 * 
	 * @return The newly created class in the model.
	 */
	public OntClass createClass(String className) {
		OntClass ontClass = model.createClass(nameSpace + className);
		return ontClass;
	}

	public OntClass getClass(String className) {
		return model.getOntClass(nameSpace + className);
	}

	public void writeOntologyToDisk() {
		FileWriter out = null;
		File file = new File("mymodel.owl");
		try {
			try {
				out = new FileWriter(file);
				System.out.println(file.getAbsolutePath());

			} catch (IOException e) {
				e.printStackTrace();
			}
			this.model.write(out, "RDF/XML-ABBREV");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	public void readAllStatements() {
		// list the statements in the Model
		StmtIterator iter = model.listStatements();
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

	public void readClassesAndProperties() {

		ExtendedIterator classIter = model.listClasses();
		while (classIter.hasNext()) {
			OntClass ontClass = (OntClass) classIter.next();

			String uriClassString = "";
			String uriDisjointWith = "";
			String uriLabel = "";
			ExtendedIterator ei = null;
			try {
				uriClassString = ontClass.getURI();
				uriDisjointWith = (ontClass.getDisjointWith().getURI() == null) ? null : ontClass.getDisjointWith().getURI();
				ei = (ontClass.listDeclaredProperties()) == null ? null : ontClass.listDeclaredProperties();
			} catch (NullPointerException e) {
				// System.out.println("none");
			}
			if (uriClassString != null & null != uriDisjointWith)
				System.out.println("Class: " + uriClassString);
			// System.out.println(uriDisjointWith);
			// System.out.println(uriLabel);
			if (ei != null)
				while (ei.hasNext()) {
					String current = ei.next().toString();
					// System.out.println("Property: " + current.split("#")[1]);
					Resource r = model.getResource(current);

					// for (Resource theClass: classes) {
					// if (prop.hasRange(theClass) System.out.printf("RANGE:
					// %s\n", theClass);
					// if (prop.hasDomain(theClass) System.out.printf("DOMAIN:
					// %s\n", theClass);
					// }

					System.out.println("Property (" + uriClassString.split("#")[1] + "): " + r.getLocalName());

				}
			else {
				// System.out.println("no Properties left");
			}

		}

	}
}
