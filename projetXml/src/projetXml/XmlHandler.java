package projetXml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import mapper.Article;
import mapper.Extract;
import mapper.Inproceedings;
import mapper.Paper;

public class XmlHandler extends DefaultHandler {

	public static final String INPROCEEDINGS = "inproceedings";
	public static final String YEAR = "year";
	public static final String AUTHOR = "author";
	public static final String KEY = "key";
	public static final String TITLE = "title";
	public static final String ARTICLE = "article";

	private String nameToFind;

	private List<String> CoAuthors = new ArrayList<>();

	private boolean isArticle;
	private boolean isInproceedings;
	private boolean goodAuthor;

	private boolean isAuthor;
	private boolean isYear;
	private boolean isTitle;

	private Extract extract;
	private Article currentArticle;
	private Inproceedings currentInpro;

	private String currentAuthor = "";
	private String currentYear = "";
	private String currentTitle = "";

	public XmlHandler(final String ntf) {
		super();
		nameToFind = ntf.trim().replace("_", " ");
		extract = new Extract(nameToFind);

		isArticle = false;
		isInproceedings = false;

		goodAuthor = false;

		isAuthor = false;
		isYear = false;
		isTitle = false;
	}

	@Override
	public void endDocument() {
		final StringBuilder builder = new StringBuilder();
		builder.append("<extract>\n").append("<name>").append(extract.getName()).append("</name>\n");
		builder.append("\t<coauthors>\n");
		extract.getCoAuthors().forEach(author -> {
			builder.append("\t\t<author>").append(author).append("</author>\n");
		});
		builder.append("\t</coauthors>\n");
		extract.getArticles().forEach(article -> {
			builder.append("\t<article key=\"").append(article.getKey()).append("\">\n");
			article.getAuthors().forEach(author -> {
				builder.append("\t\t<author>").append(author).append("</author>\n");
			});
			builder.append("\t\t<title>").append(article.getTitle()).append("</title>\n");
			builder.append("\t\t<year>").append(article.getYear()).append("</year>\n");
			builder.append("\t</article>\n");
		});
		extract.getInproceedings().forEach(inpro -> {
			builder.append("\t<inproceedings key=\"").append(inpro.getKey()).append("\">\n");
			inpro.getAuthors().forEach(author -> {
				builder.append("\t\t<author>").append(author).append("</author>\n");
			});
			builder.append("\t\t<title>").append(inpro.getTitle()).append("</title>\n");
			builder.append("\t\t<year>").append(inpro.getYear()).append("</year>\n");
			builder.append("\t</inproceedings>\n");
		});

		builder.append("</extract>");

		File yourFile = new File("nameToFind.xml");
		try {
			yourFile.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(yourFile, false);
			outputStream.write(builder.toString().getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) {

		if (ARTICLE.equals(qName)) {
			isArticle = true;
			currentArticle = new Article(atts.getValue(KEY));
		} else if (INPROCEEDINGS.equals(qName)) {
			isInproceedings = true;
			currentInpro = new Inproceedings(atts.getValue(KEY));
		}
		switch (qName) {
		case AUTHOR:
			isAuthor = true;
			break;
		case TITLE:
			isTitle = true;
			break;
		case YEAR:
			isYear = true;
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		switch (qName) {
		case AUTHOR:
			if (isArticle)
				currentArticle.addAuthor(currentAuthor);
			else if (isInproceedings)
				currentInpro.addAuthor(currentAuthor);

			if (nameToFind.equals(currentAuthor))
				goodAuthor = true;
			currentAuthor = "";
			isAuthor = false;
			break;

		case TITLE:
			if (isArticle)
				currentArticle.setTitle(currentTitle);
			else if (isInproceedings)
				currentInpro.setTitle(currentTitle);
			currentTitle = "";
			isTitle = false;
			break;
		case YEAR:
			if (isArticle)
				currentArticle.setYear(currentYear);
			else if (isInproceedings)
				currentInpro.setYear(currentYear);
			currentYear = "";
			isYear = false;
			break;
		case ARTICLE:
			if (goodAuthor) {
				traitementCoAuthors(currentArticle);
				extract.getArticles().add(currentArticle);
				goodAuthor = false;
			}
			isArticle = false;
			break;
		case INPROCEEDINGS:
			if (goodAuthor) {
				traitementCoAuthors(currentInpro);
				extract.getInproceedings().add(currentInpro);
				goodAuthor = false;
			}
			isInproceedings = false;
			break;
		}
	}

	private void traitementCoAuthors(Paper paper) {
		paper.getAuthors().forEach(authors -> {
			if (!authors.equals(nameToFind))
				extract.addCoauthor(authors);
		});
	}

	@Override
	public void characters(char ch[], int begin, int length) {
		if (isArticle || isInproceedings) {
			if (isAuthor) {
				String author = new String(ch, begin, length);
				currentAuthor = currentAuthor.concat(author);
			} else if (isYear)
				currentYear = new String(ch, begin, length);
			else if (isTitle) {
				String titre = new String(ch, begin, length);
				currentTitle = currentTitle.concat(titre);
			}
		}
	}

}
