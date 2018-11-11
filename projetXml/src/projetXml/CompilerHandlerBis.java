package projetXml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import mapper.AuthorHandler;

public class CompilerHandlerBis extends DefaultHandler {
	String newFileName;
	private static final int NBIMPORTANT = 57;

	final AuthorHandler gestionTemporaire = new AuthorHandler();

	boolean isGoodPaper = false;
	boolean isAuthor = false;

	String currentAuthor = "";

	public CompilerHandlerBis(final String nfn) {
		newFileName = nfn;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if ("article".equals(qName) || "inproceedings".equals(qName)) {
			isGoodPaper = true;
		}
		if (isGoodPaper && "author".equals(qName)) {
			isAuthor = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if ("author".equals(qName)) {
			gestionTemporaire.addAuthor(currentAuthor);
			currentAuthor = "";
			isAuthor = false;
		}
		if ("article".equals(qName) || "inproceedings".equals(qName)) {
			isGoodPaper = false;
			gestionTemporaire.erase();
		}
	}

	@Override
	public void characters(char ch[], int begin, int length) {
		if (isGoodPaper && isAuthor) {
			String author = new String(ch, begin, length);
			currentAuthor = currentAuthor.concat(author);
		}
	}

	@Override
	public void endDocument() {
		System.out.println("Ecriture du fichier");
		List<Integer> listeIndiceOrdonnee = gestionTemporaire.getOrdre();
		char letter = 'A' - 1;
		BigInteger[] offsets = new BigInteger[26];
		int currentOffset = 0;
		StringBuilder builder = new StringBuilder();
		for (int j = 0; j < NBIMPORTANT; j++) {
			List<String> temp = gestionTemporaire.getListe(listeIndiceOrdonnee.get(j));
			builder.append(temp.get(0)).append(':');
			for (int i = 1; i < temp.size(); i++)
				builder.append(temp.get(i)).append(',');
			builder.append('\n');
		}
		BigInteger tempOffset = BigInteger.valueOf(builder.toString().getBytes().length);
		for (int j = NBIMPORTANT; j < listeIndiceOrdonnee.size(); j++) {
			List<String> temp = gestionTemporaire.getListe(listeIndiceOrdonnee.get(j));
			StringBuilder builderTemp = new StringBuilder();
			while (temp.get(0).charAt(0) != letter) {
				letter++;
				offsets[currentOffset] = currentOffset > 0 ? offsets[currentOffset - 1].add(tempOffset) : tempOffset;
				tempOffset = BigInteger.valueOf(0);
				currentOffset++;
			}
			builderTemp.append(temp.get(0)).append(':');
			for (int i = 1; i < temp.size(); i++)
				builderTemp.append(temp.get(i)).append(',');
			builderTemp.append('\n');
			tempOffset = tempOffset.add(BigInteger.valueOf(builderTemp.toString().getBytes().length));
			builder.append(builderTemp.toString());
		}
		letter = 'A' - 1;
		StringBuilder header = new StringBuilder();
		for (BigInteger offset : offsets) {
			letter++;
			header.append(letter).append(':').append(offset).append(',');
		}
		header.append('\n').append(builder.toString());
		File yourFile = new File(newFileName);
		try {
			yourFile.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(yourFile, false);
			outputStream.write(header.toString().getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
