package ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo;

import ge.edu.tsu.hrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hrs.control_panel.server.dao.DatabaseUtil;
import ge.edu.tsu.hrs.control_panel.server.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainingDataInfoDAOImpl implements TrainingDataInfoDAO {

	private PreparedStatement pstmt;

	@Override
	public void addTrainingDataInfo(TrainingDataInfo trainingDataInfo) {
		try {
			String sql = "INSERT INTO training_data_info (id, grouped_normalized_datum_ids, height, width, min_value, max_value, normalization_type, count) VALUES (?,?,?,?,?,?,?,?);";
			pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
			pstmt.setInt(1, trainingDataInfo.getId());
			pstmt.setString(2, StringUtil.getStringFromIntegerList(trainingDataInfo.getGroupedNormalizedDatumIds()));
			pstmt.setInt(3, trainingDataInfo.getHeight());
			pstmt.setInt(4, trainingDataInfo.getWidth());
			pstmt.setFloat(5, trainingDataInfo.getMinValue());
			pstmt.setFloat(6, trainingDataInfo.getMaxValue());
			pstmt.setString(7, trainingDataInfo.getNormalizationType().name());
			pstmt.setInt(8, trainingDataInfo.getCount());
			pstmt.executeUpdate();
			System.out.println("Inserted training data info!");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			DatabaseUtil.closeConnection();
		}
	}

	@Override
	public TrainingDataInfo getTrainingDataInfo(Integer id) {
		try {
			String sql = "SELECT * FROM training_data_info WHERE id = ?";
			pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				TrainingDataInfo trainingDataInfo = new TrainingDataInfo();
				trainingDataInfo.setId(id);
				trainingDataInfo.setGroupedNormalizedDatumIds(StringUtil.getIntegerListFromString(rs.getString("grouped_normalized_datum_ids")));
				trainingDataInfo.setHeight(rs.getInt("height"));
				trainingDataInfo.setWidth(rs.getInt("width"));
				trainingDataInfo.setMinValue(rs.getFloat("min_value"));
				trainingDataInfo.setMaxValue(rs.getFloat("max_value"));
				trainingDataInfo.setNormalizationType(NormalizationType.valueOf(rs.getString("normalization_type")));
				trainingDataInfo.setCount(rs.getInt("count"));
				return trainingDataInfo;
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			DatabaseUtil.closeConnection();
		}
		return null;
	}

	@Override
	public void deleteTrainingDataInfo(int id) {
		try {
			String sql = "DELETE FROM training_data_info WHERE id=?";
			pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			DatabaseUtil.closeConnection();
		}
	}
}
