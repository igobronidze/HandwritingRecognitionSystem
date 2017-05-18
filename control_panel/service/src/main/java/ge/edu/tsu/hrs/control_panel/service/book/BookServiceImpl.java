package ge.edu.tsu.hrs.control_panel.service.book;

import ge.edu.tsu.hrs.control_panel.model.book.Book;
import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.server.dao.book.BookDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.book.BookDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.book.BookProcessor;

import java.io.File;
import java.util.List;

public class BookServiceImpl implements BookService {

	private BookDAO bookDAO = new BookDAOImpl();

	private BookProcessor bookProcessor = new BookProcessor();

	@Override
	public List<Book> getBooks() {
		return bookDAO.getBooks();
	}

	@Override
	public void bookProcessing(File directory, String description) {
		try {
			bookProcessor.bookProcessing(directory, description);
		} catch (ControlPanelException ex) {
			ex.printStackTrace();
		}
	}
}
