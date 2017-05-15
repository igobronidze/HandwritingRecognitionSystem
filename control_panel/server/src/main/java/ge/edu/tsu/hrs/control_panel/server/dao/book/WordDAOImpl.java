package ge.edu.tsu.hrs.control_panel.server.dao.book;

import ge.edu.tsu.hrs.control_panel.model.book.Word;
import ge.edu.tsu.hrs.control_panel.server.dao.DatabaseUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class WordDAOImpl implements WordDAO {

	private PreparedStatement pstmt;

	@Override
	public void addWords(List<Word> words) {
		try {
			if (!words.isEmpty()) {
				System.out.println("Started words inserting, count - " + words.size());
				Long currMS = new Date().getTime();
				StringBuilder sql = new StringBuilder("INSERT INTO Word VALUES ");
				for (int i = 0; i < words.size(); i++) {
					sql.append("(" + words.get(i).getWord() + ")");
					if (i != words.size() - 1) {
						sql.append(", ");
					}
				}
				pstmt = DatabaseUtil.getConnection().prepareStatement(sql.toString());
				pstmt.executeUpdate();
				System.out.println("Words inserting took " + (new Date().getTime() - currMS) + "ms");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection();
		}
	}
}
