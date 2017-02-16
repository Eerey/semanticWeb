package tutorial;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.jena.base.Sys;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;

public class Tutorial {
	public static void main(String args[]) {
		System.out.println("Application started.");
//		alterSystemOut(true);
		createExampleOntology();
//		createExampleModel();
	}
	

	public static void createExampleOntology(){
//		http://protege.stanford.edu/ontologies/pizza/pizza.owl
//		OntModel model = ModelFactory.createOntologyModel();
		String fileName = "pizza.owl";
		try{
//			File file = new File(fileName);
//			FileReader reader = new FileReader(file);
			OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
			String uri = "http://protege.stanford.edu/ontologies/pizza/pizza.owl";
			model.read(uri,null);
//			model.write(System.out,"RDF/XML-ABBREV");
//			model.write(System.out,"N-Triples");
//			model.write(System.out,"TriG");
//			model.write(System.out,"N-Quads");
//			model.write(System.out,"TURTLE");
//			model.write(System.out,"RDF/JSON");
			
			ExtendedIterator classIter = model.listClasses();
			while (classIter.hasNext()) {
				OntClass ontClass = (OntClass) classIter.next();
				String uriClassString = "";
				String uriDisjointWith = "";
				String uriLabel = "";
				ExtendedIterator ei = null;
				try{
				uriClassString = ontClass.getURI();
				uriDisjointWith = (ontClass.getDisjointWith().getURI()==null)? "damn it's null":ontClass.getDisjointWith().getURI();
				ei = (ontClass.listDeclaredProperties()) == null? null:ontClass.listDeclaredProperties();
				}
				catch (NullPointerException e){
//					System.out.println("none");
				}
				if (uriClassString!=null&null!=uriDisjointWith)
					System.out.println("Class: "+ uriClassString);
//					System.out.println(uriDisjointWith);
//					System.out.println(uriLabel);
					if (ei!=null)while(ei.hasNext())
					{
						String current = ei.next().toString();
//						System.out.println("Property: " + current.split("#")[1]);
						Resource r=model.getResource(current);
						System.out.println("Property ("+uriClassString.split("#")[1]+"): " + r.getLocalName());
					} else{
						System.out.println("no Properties left");
					}
				}
				
			} catch (Exception e){
			e.printStackTrace();
		}
			
		
		
	}
	public static void createExampleModel(){
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://example.com/test/";
		Resource r = m.createResource(NS + "r");
		Property p = m.createProperty(NS + "p");
		r.addProperty(p,  "hello world", XSDDatatype.XSD);
		m.write(System.out, "Turtle");
		
	}
	
	public static void alterSystemOut(boolean arg){
		PrintStream myStream = new PrintStream(System.out) {
			Date startOfApplication = new Date();
		    @Override
		    public void println(String x) {
		    	long delta = (new Date().getTime())-startOfApplication.getTime();
		        super.println(new SimpleDateFormat(
		        		"HH:mm:ss (SSS").format(new Date())+
		        		"ms): " +
		        		"("+delta+") "+
		        		x);
		    }
		};
		System.setOut(myStream);
	}
}
