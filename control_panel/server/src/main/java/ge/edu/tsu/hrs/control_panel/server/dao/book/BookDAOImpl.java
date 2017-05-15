package ge.edu.tsu.hrs.control_panel.server.dao.book;

import ge.edu.tsu.hrs.control_panel.model.book.Book;
import ge.edu.tsu.hrs.control_panel.server.dao.DatabaseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

	private PreparedStatement pstmt;

	@Override
	public void addBook(Book book) {
		try {
			String sql = "INSERT INTO Book (name, description, total_words, distinct_words, saved_words) VALUES (?,?,?,?,?,?);";
			pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
			pstmt.setString(1, book.getName());
			pstmt.setString(2, book.getDescription());
			pstmt.setInt(3, book.getTotalWords());
			pstmt.setInt(4, book.getDistinctWords());
			pstmt.setInt(5, book.getSavedWords());
			pstmt.setLong(6, book.getDuration());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection();
		}
	}

	@Override
	public List<Book> getBooks() {
		List<Book> books = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Book";
			pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setDescription(rs.getString("description"));
				book.setTotalWords(rs.getInt("total_words"));
				book.setDistinctWords(rs.getInt("distinct_words"));
				book.setSavedWords(rs.getInt("saved_words"));
				book.setDuration(rs.getLong("duration"));
				books.add(book);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection();
		}
		return books;
	}
}
