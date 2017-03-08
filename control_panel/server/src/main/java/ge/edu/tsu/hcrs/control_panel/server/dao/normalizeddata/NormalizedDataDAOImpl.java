package ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.DatabaseUtil;
import org.apache.commons.lang3.text.StrBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NormalizedDataDAOImpl implements NormalizedDataDAO {

    private PreparedStatement pstmt;

    @Override
    public void addNormalizedDatum(List<NormalizedData> normalizedDatum, int groupedNormalizedDataId) {
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO normalized_data (letter, data, grouped_normalized_data_id) VALUES ");
            for (int i = 0; i < normalizedDatum.size() - 1; i++) {
                sql.append("(?, ?, ?), ");
            }
            sql.append("(?, ?, ?);");
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql.toString());
            for (int i = 0; i < normalizedDatum.size(); i++) {
                NormalizedData normalizedData = normalizedDatum.get(i);
                pstmt.setString(i * 3 + 1, "" + normalizedData.getLetter());
                pstmt.setArray(i * 3 + 2, DatabaseUtil.getConnection().createArrayOf("float4", normalizedData.getData()));
                pstmt.setInt(i * 3 + 3, groupedNormalizedDataId);
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public List<NormalizedData> getNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum) {
        List<NormalizedData> normalizedDatum = new ArrayList<>();
        try {
            if (!groupedNormalizedDatum.isEmpty()) {
                StrBuilder sql = new StrBuilder("SELECT * FROM normalized_data WHERE grouped_normalized_data_id IN (");
                for (int i = 0; i < groupedNormalizedDatum.size() - 1; i++) {
                    sql.append("?,");
                }
                sql.append("?);");
                pstmt = DatabaseUtil.getConnection().prepareStatement(sql.toString());
                for (int i = 0; i < groupedNormalizedDatum.size(); i++) {
                    pstmt.setInt(i + 1, groupedNormalizedDatum.get(i).getId());
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    NormalizedData normalizedData = new NormalizedData();
                    normalizedData.setId(rs.getInt("id"));
                    String letterString = rs.getString("letter");
                    normalizedData.setLetter(letterString == null || letterString.isEmpty() ? null : letterString.charAt(0));
                    Array sqlArray = rs.getArray("data");
                    normalizedData.setData((Float[]) sqlArray.getArray());
                    normalizedDatum.add(normalizedData);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return normalizedDatum;
    }
}
