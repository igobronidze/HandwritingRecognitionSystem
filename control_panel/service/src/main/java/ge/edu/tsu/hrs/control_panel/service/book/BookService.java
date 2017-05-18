package ge.edu.tsu.hrs.control_panel.service.book;

import ge.edu.tsu.hrs.control_panel.model.book.Book;

import java.io.File;
import java.util.List;

public interface BookService {

	List<Book> getBooks();

	void bookProcessing(File directory, String description);
}
