package ontology.opennlp;

import java.io.InputStream;

import ontology.model.TextSentence;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class NameFinder {
	private static NameFinder personNameFinder;
	private static NameFinder locationNameFinder;
	private static NameFinder organizationNameFinder;
	
	private NameFinderME nameFinder;
	private Span[] nameSpans;
	private TextSentence sentence;

	private NameFinder(String binFile) {
		try {
			InputStream inputStream = new OpenNLPRessourceLoader(binFile).getRessource();
			TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
			// Instantiating POSTaggerME class
			nameFinder = new NameFinderME(model);
		} catch (Exception e) {
			System.out.println("LocationNameFinder could not be initialized");
			System.exit(3);
		}
	}

	public boolean isName(TextSentence textSentence, int id) {
		if (this.sentence == null || !sentence.equals(sentence))
			process(textSentence);
		return nameSpans.length > 0 && nameSpans[0].contains(id);
	}

	private void process(TextSentence sentence) {
		this.sentence = sentence;
		String[] wordToBeProcessed = new String[sentence.taggedWords.size()];

		for (int i = 0; i < sentence.taggedWords.size(); i++)
			wordToBeProcessed[i] = sentence.taggedWords.get(i).word;

		nameSpans = nameFinder.find(wordToBeProcessed);
	}

	public static NameFinder getPersonNameFinder() {
		if (personNameFinder == null)
			personNameFinder = new NameFinder("en-ner-person.bin");
		return personNameFinder;
	}
	
	public static NameFinder getLocationNameFinder() {
		if (locationNameFinder == null)
			locationNameFinder = new NameFinder("en-ner-location.bin");
		return locationNameFinder;
	}
	
	public static NameFinder getOrganizationNameFinder() {
		if (organizationNameFinder == null)
			organizationNameFinder = new NameFinder("en-ner-organization.bin");
		return organizationNameFinder;
	}
}
