package ge.edu.tsu.hcrs.control_panel.server.dao;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NormalizedDataDAOImpl implements NormalizedDataDAO {

    private PreparedStatement pstmt;

    @Override
    public void addNormalizedData(NormalizedData normalizedData) {
        try {
            String sql = "INSERT INTO normalized_data (width, height, letter, generation, data)" +
                    " VALUES (?, ?, ?, ?, ?)";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setInt(1, normalizedData.getWidth());
            pstmt.setInt(2, normalizedData.getHeight());
            pstmt.setString(3, "" + normalizedData.getLetter());
            pstmt.setString(4, normalizedData.getTrainingSetGeneration());
            pstmt.setArray(5, DatabaseUtil.getConnection().createArrayOf("float4", normalizedData.getData()));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public List<NormalizedData> getNormalizedDatas(Integer width, Integer height, String generation) {
        List<NormalizedData> normalizedDataList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM normalized_data WHERE 1=1 ";
            if (width != null) {
                sql += "AND width = '" + width + "' ";
            }
            if (height != null) {
                sql += "AND height = '" + height + "' ";
            }
            if (generation != null) {
                sql += "AND generation = '" + generation + "' ";
            }
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer wid = rs.getInt("width");
                Integer heig = rs.getInt("height");
                String letterString = rs.getString("letter");
                Character letter = letterString == null || letterString.isEmpty() ? null : letterString.charAt(0);
                String gen = rs.getString("generation");
                Array sqlArray = rs.getArray("data");
                Float[] data = (Float[])sqlArray.getArray();
                NormalizedData normalizedData = new NormalizedData(wid, heig, data, letter, gen);
                normalizedDataList.add(normalizedData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return normalizedDataList;
    }

    @Override
    public int countNormalizedDatas(Integer width, Integer height, String generation) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(id) FROM normalized_data WHERE 1=1 ";
            if (width != null) {
                sql += "AND width = '" + width + "' ";
            }
            if (height != null) {
                sql += "AND height = '" + height + "' ";
            }
            if (generation != null) {
                sql += "AND generation = '" + generation + "' ";
            }
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return count;
    }

    @Override
    public List<GroupedNormalizedData> getGroupedNormalizedDatas() {
        List<GroupedNormalizedData> groupedNormalizedDatas = new ArrayList<>();
        try {
            String sql = "SELECT width, height, first_symbol, last_symbol, generation, COUNT(id) AS count FROM normalized_data GROUP BY width, height, generation";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer wid = rs.getInt("width");
                Integer heig = rs.getInt("height");
                String generation = rs.getString("generation");
                int count = rs.getInt("count");
                GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
                groupedNormalizedData.setWidth(wid);
                groupedNormalizedData.setHeight(heig);
                groupedNormalizedData.setTrainingSetGeneration(generation);
                groupedNormalizedData.setCount(count);
                groupedNormalizedDatas.add(groupedNormalizedData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return groupedNormalizedDatas;
    }
}
