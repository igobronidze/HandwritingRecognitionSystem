package ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.dao.DatabaseUtil;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import org.apache.commons.lang3.text.StrBuilder;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NormalizedDataDAOImpl implements NormalizedDataDAO {

    private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private final Parameter sleepPerSelectedNormalizedData = new Parameter("sleepPerSelectedNormalizedData", "1000");

    private final Parameter sleepBetweenSelectedNormalizedDataInSeconds = new Parameter("sleepBetweenSelectedNormalizedDataInSeconds", "1");

    private PreparedStatement pstmt;

    @Override
    public void addNormalizedDatum(List<NormalizedData> normalizedDatum, GroupedNormalizedData groupedNormalizedData) {
        try {
            System.out.println("Start add normalized data method count[" + normalizedDatum.size() + "]");
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
                pstmt.setInt(i * 3 + 3, groupedNormalizedData.getId());
            }
            pstmt.executeUpdate();
            String updateSql = "UPDATE grouped_normalized_data SET duration = duration + ? WHERE id = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(updateSql);
            pstmt.setLong(1, groupedNormalizedData.getDuration());
            pstmt.setInt(2, groupedNormalizedData.getId());
            pstmt.executeUpdate();
            System.out.println("Finished add normalized data method");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection();
        }
    }


    @Override
    public List<NormalizedData> getNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum) {
        System.out.println("Start normalized data selection for " + groupedNormalizedDatum.size() + " group!");
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
                int countData = 0;
                for (GroupedNormalizedData groupedNormalizedData : groupedNormalizedDatum) {
                    countData += groupedNormalizedData.getCount();
                }
                int counter = 0;
                while (rs.next()) {
                    NormalizedData normalizedData = new NormalizedData();
                    normalizedData.setId(rs.getInt("id"));
                    String letterString = rs.getString("letter");
                    normalizedData.setLetter(letterString == null || letterString.isEmpty() ? null : letterString.charAt(0));
                    normalizedData.setData((Float[]) rs.getArray("data").getArray());
                    normalizedDatum.add(normalizedData);
                    counter++;
                    if (counter % systemParameterProcessor.getIntegerParameterValue(sleepPerSelectedNormalizedData) == 0) {
                        System.out.println("Select " + counter + " normalized data from " + countData);
                        try {
                            Thread.sleep(systemParameterProcessor.getLongParameterValue(sleepBetweenSelectedNormalizedDataInSeconds) * 1000);
                        } catch (InterruptedException ex) {}
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection();
        }
        System.out.println("Finished normalized data selection!");
        return normalizedDatum;
    }
}
