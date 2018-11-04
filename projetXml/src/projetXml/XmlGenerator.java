package projetXml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlGenerator {

	public static void main(String[] args) {
		System.setProperty("jdk.xml.entityExpansionLimit", "0");
		System.out.println(args[0]);
		System.out.println(args[1]);
		// producteur
		XMLReader prod;

		// consommateur
		DefaultHandler cons;

		try {
			// obtenir un parser
			prod = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		} catch (SAXException | ParserConfigurationException e) {
			System.err.println("Problème de configuration");
			return;
		}

		try {
			// et un consommateur qui ne fait rien
			cons = new XmlHandler(args[0]);
			prod.setContentHandler(cons);
			prod.setErrorHandler(cons);
		} catch (Exception e) {
			System.out.println("il y a eu un souci");
			return;
		}

		try {
			prod.parse(args[1]);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (SAXException e) {
			e.printStackTrace();
			return;
		}
	}
}
