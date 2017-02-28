package ontology;

public class Main {

	public static void main(String[] args) throws Exception {
		if(args.length == 1 && args[0].equals("help")){
			printHelp();
		}else if(args.length == 2){
			createOntologyOutOfFile(args);
		}else if(args.length == 3 && args[2].equals("-s")){
			createOntologyOutOfString(args);
		}else{
			System.out.println("Bitte rufen Sie das Programm mittels dem \"help\" Parameter auf.");
		}
		System.out.println("EXIT");
		System.exit(1);
	}

	private static void printHelp() {
		System.out.println("\nAuslesen einer Datei:");
		System.out.println("<Dateiname-inklusive-Endung> <Ontologiename>");
		System.out.println("Beispiel:\tjava -jar SemanticWeb.jar test.txt Test");
		System.out.println();
		System.out.println("Lesen eines Satzes:");
		System.out.println("\"<Satz>\" <Ontologiename> -s");
		System.out.println("Beispiel:\tjava -jar SemanticWeb.jar \"Marwin is a human.\" Human -s");
		System.out.println();
	}

	private static void createOntologyOutOfFile(String[] args) throws Exception {
		FileLoader fileLoader = new FileLoader(args[0]);
		String fileContent = fileLoader.getContent();
		System.out.println("Datei wurde ausgelesen");
		new TextToOntologyWeaver(fileContent, args[1]);
	}

	private static void createOntologyOutOfString(String[] args) {
		new TextToOntologyWeaver(args[0], args[1]);
	}

}
