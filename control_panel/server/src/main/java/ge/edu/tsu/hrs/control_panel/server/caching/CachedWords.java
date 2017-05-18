package ge.edu.tsu.hrs.control_panel.server.caching;

import ge.edu.tsu.hrs.control_panel.model.book.Word;
import ge.edu.tsu.hrs.control_panel.server.dao.book.WordDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.book.WordDAOImpl;

import java.util.HashSet;
import java.util.Set;

public class CachedWords {

	private static WordDAO wordDAO = new WordDAOImpl();

	private static Set<Word> words;

	public static void loadData() {
		words = new HashSet<>(wordDAO.getWords());
	}

	public static void addWords(Set<Word> newWords) {
		words.addAll(newWords);
	}

	public static Set<Word> getWords() {
		return words;
	}
}
