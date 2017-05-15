package ge.edu.tsu.hrs.control_panel.model.book;

public class Book {

	private int id;

	private String name;

	private String description;

	private int totalWords;

	private int distinctWords;

	private int savedWords;

	private long duration;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalWords() {
		return totalWords;
	}

	public void setTotalWords(int totalWords) {
		this.totalWords = totalWords;
	}

	public int getDistinctWords() {
		return distinctWords;
	}

	public void setDistinctWords(int distinctWords) {
		this.distinctWords = distinctWords;
	}

	public int getSavedWords() {
		return savedWords;
	}

	public void setSavedWords(int savedWords) {
		this.savedWords = savedWords;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
}
