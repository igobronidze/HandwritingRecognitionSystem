package ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.server.dao.DatabaseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupedNormalizedDataDAOImpl implements GroupedNormalizedDataDAO {

    private PreparedStatement pstmt;

    @Override
    public Integer addOrGetGroupedNormalizedDataId(GroupedNormalizedData groupedNormalizedData) {
        try {
            String idSql = "SELECT id FROM grouped_normalized_data WHERE height = ? AND width = ? AND min_value = ? AND max_value = ? AND name = ? AND normalization_type = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(idSql);
            pstmt.setInt(1, groupedNormalizedData.getHeight());
            pstmt.setInt(2, groupedNormalizedData.getWidth());
            pstmt.setFloat(3, groupedNormalizedData.getMinValue());
            pstmt.setFloat(4, groupedNormalizedData.getMaxValue());
            pstmt.setString(5, groupedNormalizedData.getName());
            pstmt.setString(6, groupedNormalizedData.getNormalizationType().name());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String updateSql = "UPDATE grouped_normalized_data SET duration = duration + ? WHERE id = ?";
                pstmt = DatabaseUtil.getConnection().prepareStatement(updateSql);
                pstmt.executeUpdate();
                return id;
            } else {
                String insertSql = "INSERT INTO grouped_normalized_data (height, width, min_value, max_value, name, normalization_type, count, duration) VALUES (?,?,?,?,?,?,?,?);";
                pstmt = DatabaseUtil.getConnection().prepareStatement(insertSql);
                pstmt.setInt(1, groupedNormalizedData.getHeight());
                pstmt.setInt(2, groupedNormalizedData.getWidth());
                pstmt.setFloat(3, groupedNormalizedData.getMinValue());
                pstmt.setFloat(4, groupedNormalizedData.getMaxValue());
                pstmt.setString(5, groupedNormalizedData.getName());
                pstmt.setString(6, groupedNormalizedData.getNormalizationType().name());
                pstmt.setInt(7, groupedNormalizedData.getCount());
                pstmt.setLong(8, groupedNormalizedData.getDuration());
                pstmt.executeUpdate();
                String maxIdSql = "SELECT MAX(id) AS max_id FROM grouped_normalized_data";
                pstmt = DatabaseUtil.getConnection().prepareStatement(maxIdSql);
                rs = pstmt.executeQuery();
                rs.next();
                return rs.getInt("max_id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public List<GroupedNormalizedData> getGroupedNormalizedDatum(Integer height, Integer width, Float minValue, Float maxValue, NormalizationType normalizationType, String name) {
        List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
        try {
            String sql = "SELECT * FROM grouped_normalized_data WHERE 1 = 1 ";
            if (height != null) {
                sql += "AND height = " + height + " ";
            }
            if (width != null) {
                sql += "AND width = " + width + " ";
            }
            if (minValue != null) {
                sql += "AND min_value = " + minValue + " ";
            }
            if (maxValue != null) {
                sql += "AND max_value = " + maxValue + " ";
            }
            if (normalizationType != null) {
                sql += "AND normalization_type = '" + normalizationType.name() + "' ";
            }
            if (name != null) {
                sql += "AND name LIKE '%" + name + "%' ";
            }
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
                groupedNormalizedData.setHeight(rs.getInt("height"));
                groupedNormalizedData.setWidth(rs.getInt("width"));
                groupedNormalizedData.setMinValue(rs.getFloat("min_value"));
                groupedNormalizedData.setMaxValue(rs.getFloat("max_value"));
                groupedNormalizedData.setNormalizationType(NormalizationType.valueOf(rs.getString("normalization_type")));
                groupedNormalizedData.setName(rs.getString("name"));
                groupedNormalizedData.setCount(rs.getInt("count"));
                groupedNormalizedData.setId(rs.getInt("id"));
                groupedNormalizedData.setDuration(rs.getLong("duration"));
                groupedNormalizedDatum.add(groupedNormalizedData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return groupedNormalizedDatum;
    }
}