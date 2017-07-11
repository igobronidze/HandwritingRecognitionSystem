package ge.edu.tsu.hrs.control_panel.server.processor.book;

import ge.edu.tsu.hrs.control_panel.model.book.Book;
import ge.edu.tsu.hrs.control_panel.model.book.Word;
import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedWords;
import ge.edu.tsu.hrs.control_panel.server.dao.book.BookDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.book.BookDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.book.WordDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.book.WordDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.words_processing.reader.DOCXReader;
import ge.edu.tsu.hrs.words_processing.reader.PDFReader;
import ge.edu.tsu.hrs.words_processing.reader.Reader;
import ge.edu.tsu.hrs.words_processing.reader.parser.GeorgianWordsParserPattern;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookProcessor {

	private BookDAO bookDAO = new BookDAOImpl();

	private WordDAO wordDAO = new WordDAOImpl();

	private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

	private Parameter minWordsInBook = new Parameter("minWordsInBook", "100");

	public void bookProcessing(File directory, String description) throws ControlPanelException {
		for (File file : directory.listFiles()) {
			try {
				String name = file.getName();
				System.out.println("Start book processing name[" + name + "]");
				long currMS = new Date().getTime();
				Book book = new Book();
				book.setName(name);
				book.setDescription(description);
				Reader reader;
				String ext = FilenameUtils.getExtension(file.getAbsolutePath());
				if ("pdf".equals(ext)) {
					reader = new PDFReader();
				} else if ("doc".equals(ext) || "docx".equals(ext)) {
					reader = new DOCXReader();
				} else {
					System.out.println(file.getName() + " is bad file!");
					continue;
				}
				List<String> result = reader.getAllWord(file.getAbsolutePath(), new GeorgianWordsParserPattern());
				if (result.size() >= systemParameterProcessor.getIntegerParameterValue(minWordsInBook)) {
					book.setTotalWords(result.size());
					Set<Word> words = new HashSet<>();
					for (String s : result) {
						words.add(new Word(s));
					}
					book.setDistinctWords(words.size());
					Map<Integer, List<String>> cachedWords = CachedWords.getWords();
					if (cachedWords == null) {
						throw new ControlPanelException("wordsNotCachedYet");
					}
					for (Word word : new HashSet<>(words)) {
						for (Integer length : cachedWords.keySet()) {
							if (cachedWords.get(length).contains(word.getWord())) {
								words.remove(word);
								break;
							}
						}
					}
					book.setSavedWords(words.size());
					wordDAO.addWords(new ArrayList<>(words));
					CachedWords.addWords(words);
					book.setDuration(new Date().getTime() - currMS);
					bookDAO.addBook(book);
					System.out.println("Finished book processing name[" + name + "]");
				} else {
					System.out.println("Book is too small");
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
