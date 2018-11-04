package projetXml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class DblpHandler extends DefaultHandler {

	private String nameToFind;

	private boolean isArticle;

	private boolean isAuthor;

	private boolean goodAuthor;

	private String currentAuthor = "";

	private List<String> articleAuthors = new ArrayList<>();
	private List<String> CoAuthors = new ArrayList<>();

	public DblpHandler(final String ntf) {
		super();
		nameToFind = ntf.trim().replace("_", " ");
		isArticle = false;
		isAuthor = false;
		goodAuthor = false;
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
			if (goodAuthor) {
				CoAuthors.add(currentAuthor);
			} else {
				if (nameToFind.equals(currentAuthor)) {
					goodAuthor = true;
					articleAuthors.forEach(authors -> {
						CoAuthors.add(authors);
					});
					articleAuthors.clear();
				} else {
					articleAuthors.add(currentAuthor);
				}
			}
			currentAuthor = "";
			isAuthor = false;
		}
		if ("article".equals(qName) || "inproceedings".equals(qName)) {
			isArticle = false;
			goodAuthor = false;
			articleAuthors.clear();
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
		final Set<String> coAuthors = new HashSet<String>();
		CoAuthors.forEach(authors -> {
			coAuthors.add(authors);
		});
		System.out.println(nameToFind + " has " + coAuthors.size() + " co-authors :");
		coAuthors.forEach(authors -> {
			System.out.println(authors);
			CoAuthors.remove(authors);
		});
	}
}
