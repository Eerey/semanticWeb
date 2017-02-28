package ontology;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {

	private String content;

	public FileLoader(String path) throws Exception {
		content = readFile(path);
	}
	
	public String getContent(){
		return content;
	}

	private String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}

}
