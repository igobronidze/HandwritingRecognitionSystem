package ge.edu.tsu.hrs.control_panel.server.dao.book;

import ge.edu.tsu.hrs.control_panel.model.book.Word;

import java.util.List;

public interface WordDAO {

	void addWords(List<Word> words);

	List<Word> getWords();
}
