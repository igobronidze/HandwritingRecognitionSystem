package ge.edu.tsu.hrs.control_panel.console.fx.ui.book;

import ge.edu.tsu.hrs.control_panel.model.book.Book;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookProperty {

	private IntegerProperty id;

	private StringProperty name;

	private StringProperty description;

	private IntegerProperty totalWords;

	private IntegerProperty distinctWords;

	private IntegerProperty savedWords;

	private LongProperty duration;

	public BookProperty(Book book) {
		this.id = new SimpleIntegerProperty(book.getId());
		this.name = new SimpleStringProperty(book.getName());
		this.description = new SimpleStringProperty(book.getDescription());
		this.totalWords = new SimpleIntegerProperty(book.getTotalWords());
		this.distinctWords = new SimpleIntegerProperty(book.getDistinctWords());
		this.savedWords = new SimpleIntegerProperty(book.getSavedWords());
		this.duration = new SimpleLongProperty(book.getDuration());
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public int getTotalWords() {
		return totalWords.get();
	}

	public void setTotalWords(int totalWords) {
		this.totalWords.set(totalWords);
	}

	public int getDistinctWords() {
		return distinctWords.get();
	}

	public void setDistinctWords(int distinctWords) {
		this.distinctWords.set(distinctWords);
	}

	public int getSavedWords() {
		return savedWords.get();
	}

	public void setSavedWords(int savedWords) {
		this.savedWords.set(savedWords);
	}

	public long getDuration() {
		return duration.get();
	}

	public void setDuration(long duration) {
		this.duration.set(duration);
	}
}
