package ontology.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;

public class OpenNLPRessourceLoader {

	private String path;
	
	public OpenNLPRessourceLoader(String path){
		this.path = path;
	}
	
	public InputStream getRessource(){
		try{ 
			return new FileInputStream("opennlp-bins/" + path);
		}catch(Exception e){
			return null;
		}
	}
	
}
