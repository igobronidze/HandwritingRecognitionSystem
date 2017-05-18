package ge.edu.tsu.hrs.control_panel.server.dao.book;

import ge.edu.tsu.hrs.control_panel.model.book.Word;
import ge.edu.tsu.hrs.control_panel.server.dao.DatabaseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WordDAOImpl implements WordDAO {

	private PreparedStatement pstmt;

	@Override
	public void addWords(List<Word> words) {
		try {
			if (!words.isEmpty()) {
				Long currMS = new Date().getTime();
				System.out.println("Started words inserting, count - " + words.size());
				StringBuilder sql = new StringBuilder("INSERT INTO Word (word) VALUES ");
				for (int i = 0; i < words.size(); i++) {
					sql.append("('").append(words.get(i).getWord()).append("')");
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

	@Override
	public List<Word> getWords() {
		List<Word> words = new ArrayList<>();
		try {
			Long currMS = new Date().getTime();
			System.out.println("Started words selecting");
			String sql = "SELECT * FROM Word";
			pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Word word = new Word(rs.getString("word"));
				words.add(word);
			}
			System.out.println("Words selecting took " + (new Date().getTime() - currMS) + "ms, count - " + words.size());
		} catch (SQLException ex) {
			ex.printStackTrace();
			return getWords();   // TODO[IG] შეიძლება ჩაიციკლოს
		} finally {
			DatabaseUtil.closeConnection();
		}
		return words;
	}
}
