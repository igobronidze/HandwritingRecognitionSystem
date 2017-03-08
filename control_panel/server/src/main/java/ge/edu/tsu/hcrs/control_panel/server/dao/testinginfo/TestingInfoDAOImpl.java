package ge.edu.tsu.hcrs.control_panel.server.dao.testinginfo;

import ge.edu.tsu.hcrs.control_panel.model.network.TestingInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.DatabaseUtil;
import ge.edu.tsu.hcrs.control_panel.server.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestingInfoDAOImpl implements TestingInfoDAO {

    private PreparedStatement pstmt;

    @Override
    public void addTestingInfo(TestingInfo testingInfo) {
        try {
            String sql = "INSERT INTO testing_info (generations, number_of_test, squared_error, percentage_of_corrects, " +
                    "diff_between_ans_and_best, normalized_general_error, network_id) VALUES (?,?,?,?,?,?,?);";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            List<Integer> groupedNormalizedDatumIds = new ArrayList<>();
            for (GroupedNormalizedData groupedNormalizedData : testingInfo.getGroupedNormalizedDatum()) {
                groupedNormalizedDatumIds.add(groupedNormalizedData.getId());
            }
            pstmt.setString(1, StringUtil.getStringFromIntegerList(groupedNormalizedDatumIds));
            pstmt.setInt(2, testingInfo.getNumberOfTest());
            pstmt.setFloat(3, testingInfo.getSquaredError());
            pstmt.setFloat(4, testingInfo.getPercentageOfCorrects());
            pstmt.setFloat(5, testingInfo.getDiffBetweenAnsAndBest());
            pstmt.setFloat(6, testingInfo.getNormalizedGeneralError());
            pstmt.setInt(7, testingInfo.getNetworkId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public List<TestingInfo> getTestingInfoListByNetworkId(int networkId) {
        List<TestingInfo> testingInfoList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM testing_info WHERE network_id = ? ORDER BY id DESC;";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setInt(1, networkId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TestingInfo testingInfo = new TestingInfo();
                testingInfo.setNetworkId(rs.getInt("network_id"));
                testingInfo.setNumberOfTest(rs.getInt("number_of_test"));
                List<Integer> groupedNormalizedDatumIds = StringUtil.getIntegerListFromString(rs.getString("groupedNormalizedDatum"));
                List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
                for (Integer groupedNormalizedDataId : groupedNormalizedDatumIds) {
                    GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
                    groupedNormalizedData.setId(groupedNormalizedDataId);
                    groupedNormalizedDatum.add(groupedNormalizedData);
                }
                testingInfo.setGroupedNormalizedDatum(groupedNormalizedDatum);
                testingInfo.setSquaredError(rs.getFloat("squared_error"));
                testingInfo.setPercentageOfCorrects(rs.getFloat("percentage_of_corrects"));
                testingInfo.setDiffBetweenAnsAndBest(rs.getFloat("diff_between_ans_and_best"));
                testingInfo.setNormalizedGeneralError(rs.getFloat("normalized_general_error"));
                testingInfoList.add(testingInfo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return testingInfoList;
    }
}
