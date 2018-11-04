package mapper;

import java.util.ArrayList;
import java.util.List;

public class Paper {
	private List<String> authors = new ArrayList<>();
	private String title;
	private String year;

	private String key;

	public Paper(String key) {
		this.key = key;
	}

	public String getTitle() {
		if (title == null)
			return "";
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		if (year == null)
			return "";
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public void addAuthor(String author) {
		this.authors.add(author);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
