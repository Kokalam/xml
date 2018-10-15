package mapper;

import java.util.List;

public class Extract {
	private String name;
	
	private List<String> coAuthors;
	
	private List<Article> articles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCoAuthors() {
		return coAuthors;
	}

	public void setCoAuthors(List<String> coAuthors) {
		this.coAuthors = coAuthors;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
