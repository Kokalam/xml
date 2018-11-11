package mapper;

import java.util.ArrayList;
import java.util.List;

public class AuthorHandler {

	private static final int NBIMPORTANT = 57;
	final List<List<String>> listAuthors = new ArrayList<List<String>>();
	final List<Integer> listeIndiceOrdonnee = new ArrayList<>();

	final List<String> listeIndice = new ArrayList<>();

	final List<Integer> listeIndiceTemp = new ArrayList<>();

	public AuthorHandler() {
		listeIndice.add("Gilles Audemard");
		listeIndice.add("Sihem Belabbes");
		listeIndice.add("Salem Benferhat");
		listeIndice.add("Yazid Boumarafi");
		listeIndice.add("Zied Bouraoui");
		listeIndice.add("Frédéric Boussemart");
		listeIndice.add("Anasse Chafik");
		listeIndice.add("Fahima Cheik-Alili");
		listeIndice.add("Nathalie Chetcuti-Sperandio");
		listeIndice.add("François Chevalier");
		listeIndice.add("Assef Chmeiss");
		listeIndice.add("Jean-François Condotta");
		listeIndice.add("Sylvie Coste-Marquis");
		listeIndice.add("Cyrulle D'halluin");
		listeIndice.add("Virginie Delahaye");
		listeIndice.add("Tiago de Lima");
		listeIndice.add("Fabien Delorme");
		listeIndice.add("Vincent Dubois");
		listeIndice.add("Anis Gargouri");
		listeIndice.add("Gaël Glorian");
		listeIndice.add("Alux Goudyme");
		listeIndice.add("Éric Grégoire");
		listeIndice.add("Marwa Harzi");
		listeIndice.add("Fred Hemery");
		listeIndice.add("Yacine Izza");
		listeIndice.add("Saïd Jabbour");
		listeIndice.add("Sébastien Konieczny");
		listeIndice.add("Frédéric Koriche");
		listeIndice.add("Jean-Marie Lagniez");
		listeIndice.add("Daniel Le Berre");
		listeIndice.add("Christophe Lecoutre");
		listeIndice.add("Emmanuel Lonca");
		listeIndice.add("Jerry Loniac Konlac");
		listeIndice.add("Fatima Ezzahra Mana");
		listeIndice.add("Pierre Marquis");
		listeIndice.add("Bertrand Mazure");
		listeIndice.add("Stefan Mengel");
		listeIndice.add("Mensi Ali");
		listeIndice.add("Mhadhbi Nizar");
		listeIndice.add("Ikram Nekkache");
		listeIndice.add("Imen Ouled Dlala");
		listeIndice.add("Anastasia Paparrizou");
		listeIndice.add("Anne Parrain");
		listeIndice.add("Cédric Piette");
		listeIndice.add("Frédéric Renard");
		listeIndice.add("Olivier Roussel");
		listeIndice.add("Lakhdar Saïs");
		listeIndice.add("Sandrine Saitzek");
		listeIndice.add("Yakoub Salhi");
		listeIndice.add("Mouny Samy Modeliar");
		listeIndice.add("Nicolas Szczepanski");
		listeIndice.add("Sébastien Tabary");
		listeIndice.add("Karim Tabia");
		listeIndice.add("Ivan Varzinczak");
		listeIndice.add("Srdjan Vesic");
		listeIndice.add("Romain Wallon");
		listeIndice.add("Hugues Wattez");
		for (int i = 0; i < listeIndice.size(); i++) {
			listAuthors.add(new ArrayList<String>());
			listAuthors.get(i).add(listeIndice.get(i));
			listeIndiceOrdonnee.add(i);
		}
	}

	private int getIndice(String author) {
		for (int i = 0; i < listeIndice.size(); i++)
			if (listeIndice.get(i).equals(author))
				return i;
		return listeIndice.size();
	}

	public List<String> getListe(int indice) {
		if (indice < listAuthors.size())
			return listAuthors.get(indice);
		return null;
	}

	public String getFirstAuthor(int indice) {
		if (indice < listAuthors.size())
			return listAuthors.get(indice).get(0);
		else
			return null;
	}

	public void erase() {
		listeIndiceTemp.clear();
	}

	private void addCoAuthor(String author, int indice) {
		int inc = 0;
		int indiceLu = 0;
		/// indice : l'indice de l'auteur qu'on rajoute et author son nom
		while (inc < listeIndiceTemp.size()) /// on parcoure tous les auteurs du paper
		{
			indiceLu = listeIndiceTemp.get(inc);
			if (!listAuthors.get(indiceLu).contains(author)) {
				// on ajoute à chacun des auteurs de la liste temporaire le nouvel auteur
				listAuthors.get(indiceLu).add(author);
				// on ajoute à cet auteur tous les auteurs de la liste
				listAuthors.get(indice).add(listAuthors.get(indiceLu).get(0));
			}
			inc++;
		}
		listeIndiceTemp.add(indice);
	}

	public void addAuthor(String author) {
		int inc = getIndice(author);
		if (inc == listeIndice.size()) /// si l'auteur n'était pas encore dans la bdd
		{
			listeIndice.add(author);
			tri(inc, author);
			listAuthors.add(new ArrayList<String>());
			listAuthors.get(inc).add(author);
		}
		addCoAuthor(author, inc);
	}

	private void tri(int inc, String author) {
		if (listeIndiceOrdonnee.size() == NBIMPORTANT) {
			listeIndiceOrdonnee.add(inc);
			return;
		}
		boolean place = false;
		int indiceTemp = 0;
		int indiceAEcrire = inc;

		for (int i = NBIMPORTANT; i < listeIndiceOrdonnee.size(); i++) {
			if (place || (author.compareTo(listeIndice.get(i)) < 0)) {
				indiceTemp = listeIndiceOrdonnee.get(i);
				listeIndiceOrdonnee.set(i, indiceAEcrire);
				indiceAEcrire = indiceTemp;
				if (!place)
					place = true;
			}
		}
		listeIndiceOrdonnee.add(indiceAEcrire);
	}

	public List<Integer> getOrdre() {
		return listeIndiceOrdonnee;
	}
}
