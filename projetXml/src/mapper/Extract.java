package mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Extract {
	private String name;

	private Set<String> coAuthors = new HashSet<>();

	private List<Article> articles = new ArrayList<>();
	private List<Inproceedings> inproceedings = new ArrayList<>();

	public Extract(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getCoAuthors() {
		return coAuthors;
	}

	public void setCoAuthors(Set<String> coAuthors) {
		this.coAuthors = coAuthors;
	}

	public boolean addCoauthor(String co) {
		return this.coAuthors.add(co);
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public boolean addArticles(Article article) {
		return this.articles.add(article);
	}

	public List<Inproceedings> getInproceedings() {
		return inproceedings;
	}

	public void setInproceedings(List<Inproceedings> inproceedings) {
		this.inproceedings = inproceedings;
	}

	public boolean addInproceedings(Inproceedings inpro) {
		return this.inproceedings.add(inpro);
	}
}
