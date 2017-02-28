package ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;

public class OntologyHelper {

	private String nameSpace = "http://ontology/sw/";
	private String ontologyName;
	public OntModel model;
	public HashMap<String,Resource> resources;
	ArrayList<OntClass> classes = null;

	public OntologyHelper(String ontologyName) {
		resources = new HashMap<>();
		this.ontologyName = ontologyName;
		nameSpace += ontologyName + "#";
		initOntologyModel();
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

	public ObjectProperty createObjectProperty(String objectPropertyName) {
		ObjectProperty objectProperty = model.createObjectProperty(nameSpace + objectPropertyName);
		resources.put(objectPropertyName, objectProperty);
		return objectProperty;
	}

	public Property createProperty(String propertyName) {
		return model.createProperty(nameSpace + propertyName);
	}

	public DatatypeProperty createDatatypeProperty(String datatypePropertyName) {
		DatatypeProperty datatypeProperty = model.createDatatypeProperty(nameSpace + datatypePropertyName);
		resources.put(datatypePropertyName, datatypeProperty);
		return datatypeProperty;
		
	}
	

	public void printOntology() {
		model.write(System.out, "RDF/XML-ABBREV");
	}

	public Individual createIndividual(String className, String individualName) {
		OntClass ontClass = model.getOntClass(nameSpace + className);
		Individual individual = ontClass.createIndividual(nameSpace + individualName);
		resources.put(className+"_"+individualName, individual);
		return individual;
	}

	/**
	 * The class to be created. Classes are unique in OntModel and will be
	 * overwritten if used more than once.
	 * 
	 * @return The newly created class in the model.
	 */
	public OntClass createClass(String className) {
		OntClass ontClass = model.createClass(nameSpace + className);
		resources.put(className, ontClass);
		return ontClass;
	}

	public Resource getResource(String key) {
		return resources.get(key);
	}

	public void writeOntologyToDisk() {
		FileWriter out = null;
		File file = new File("output/"+ontologyName+".owl");
		try {
			try {
				out = new FileWriter(file);
				System.out.println("Ihre Ontologie wurde unter \""+file.getAbsolutePath()+"\" abgespeichert.");

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

	public void readClassesAndProperties() {

		ExtendedIterator<?> classIter = model.listClasses();
		while (classIter.hasNext()) {
			OntClass ontClass = (OntClass) classIter.next();

			String uriClassString = "";
			String uriDisjointWith = "";
			ExtendedIterator<?> ei = null;
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
