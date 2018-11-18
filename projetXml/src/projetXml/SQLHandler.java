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

public class SQLHandler extends DefaultHandler {

	String newFileName;
	
	final List<String> articlePeople = new ArrayList<>();

	boolean isArticle = false;
	boolean isAuthor = false;
	boolean hasFoundCharacter = false;

	String currentAuthor = "";

	public SQLHandler(final String nfn) {
		newFileName = nfn;
		DataBaseAccess.createTable();
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
				DataBaseAccess.insertAuteur(people);
				articlePeople.forEach(copeople -> {
					if(people != copeople)
						DataBaseAccess.insertCoAuteur(people, copeople);						
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
		System.out.println("fini");
		DataBaseAccess.dropTable();
	}

}
