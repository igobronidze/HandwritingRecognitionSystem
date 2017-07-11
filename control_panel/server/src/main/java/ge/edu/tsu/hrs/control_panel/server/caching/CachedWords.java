package ge.edu.tsu.hrs.control_panel.server.caching;

import ge.edu.tsu.hrs.control_panel.model.book.Word;
import ge.edu.tsu.hrs.control_panel.server.dao.book.WordDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.book.WordDAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CachedWords {

	private static WordDAO wordDAO = new WordDAOImpl();

	private static Map<Integer, List<String>> words;

	public static void loadData() {
		words = new HashMap<>();
		for (Word word : wordDAO.getWords()) {
			words.putIfAbsent(word.getWord().length(), new ArrayList<>());
			words.get(word.getWord().length()).add(word.getWord());
		}
	}

	public static void addWords(Set<Word> newWords) {
		for (Word word : newWords) {
			words.putIfAbsent(word.getWord().length(), new ArrayList<>());
			words.get(word.getWord().length()).add(word.getWord());
		}
	}

	public static Map<Integer, List<String>> getWords() {
		return words;
	}
}
