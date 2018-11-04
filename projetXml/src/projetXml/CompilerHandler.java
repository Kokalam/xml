package projetXml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class CompilerHandler extends DefaultHandler {

	String newFileName;

	final List<String> importantPeople = new ArrayList<>();
	final Map<String, Set<String>> people = new HashMap<>();

	final List<String> articlePeople = new ArrayList<>();

	boolean isArticle = false;
	boolean isAuthor = false;
	boolean hasFoundCharacter = false;

	String currentAuthor = "";

	public CompilerHandler(final String nfn) {
		newFileName = nfn;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if ("article".equals(qName) || "inproceedings".equals(qName)) {
			isArticle = true;
		}
		if (isArticle && "author".equals(qName)) {
			isAuthor = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if ("author".equals(qName)) {
			articlePeople.add(currentAuthor);
			currentAuthor = "";
			isAuthor = false;
		}
		if ("article".equals(qName) || "inproceedings".equals(qName)) {
			isArticle = false;
			articlePeople.forEach(people -> {
				if (!this.people.containsKey(people))
					this.people.put(people, new LinkedHashSet<String>());
				articlePeople.forEach(peoplePeople -> {
					if (!peoplePeople.equals(people))
						this.people.get(people).add(peoplePeople);
				});
			});
			articlePeople.clear();
		}
	}

	@Override
	public void characters(char ch[], int begin, int length) {
		if (isArticle && isAuthor) {
			String author = new String(ch, begin, length);
			currentAuthor = currentAuthor.concat(author);
		}
	}

	@Override
	public void endDocument() {
		final Map<String, Integer> alphabet = new HashMap<>();
		final SortedSet<String> keys = new TreeSet<String>(people.keySet());
		String letter = "1";
		final StringBuilder builder = new StringBuilder();
		for (String key : keys) {
			String currentLetter = key.charAt(0) + "";
			if (!currentLetter.equalsIgnoreCase(letter)) {
				alphabet.put(currentLetter, builder.toString().getBytes().length);
				letter = currentLetter;
			}
			builder.append(key).append(':');
			people.get(key).forEach(author -> builder.append(author).append(','));
			builder.append('\n');
		}
		final StringBuilder finalBuilder = new StringBuilder();
		final SortedSet<String> letters = new TreeSet<String>(alphabet.keySet());
		letters.forEach(letterOffset -> {
			finalBuilder.append(letterOffset).append(':').append(alphabet.get(letterOffset)).append(',');
		});
		finalBuilder.append('\n').append(builder.toString());
		File yourFile = new File(newFileName);
		try {
			yourFile.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(yourFile, false);
			outputStream.write(finalBuilder.toString().getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
