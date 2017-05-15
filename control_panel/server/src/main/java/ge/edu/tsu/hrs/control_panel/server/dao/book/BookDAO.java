package ge.edu.tsu.hrs.control_panel.server.dao.book;

import ge.edu.tsu.hrs.control_panel.model.book.Book;

import java.util.List;

public interface BookDAO {

	void addBook(Book book);

	List<Book> getBooks();
}
