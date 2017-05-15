package ge.edu.tsu.hrs.control_panel.service.book;

import ge.edu.tsu.hrs.control_panel.model.book.Book;
import ge.edu.tsu.hrs.control_panel.server.dao.book.BookDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.book.BookDAOImpl;

import java.util.List;

public class BookServiceImpl implements BookService {

	private BookDAO bookDAO = new BookDAOImpl();

	@Override
	public List<Book> getBooks() {
		return bookDAO.getBooks();
	}
}
