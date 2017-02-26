package ge.edu.tsu.hcrs.control_panel.server.dao;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hcrs.control_panel.model.network.TransferFunction;
import ge.edu.tsu.hcrs.control_panel.server.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NetworkInfoDAOImpl implements NetworkInfoDAO {

    private PreparedStatement pstmt;

    @Override
    public int addNetworkInfo(NetworkInfo networkInfo) {
        try {
            String sql = "INSERT INTO network_info (width, height, generations, number_of_data, training_duration, weight_min_value," +
                    "weight_max_value, bias_min_value, bias_max_value, transfer_function_type, learning_rate, min_error, training_max_iteration," +
                    "number_of_training_data_in_one_iteration, char_sequence, hidden_layer, network_processor_type, network_meta_info, description) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setInt(1, networkInfo.getWidth());
            pstmt.setInt(2, networkInfo.getHeight());
            pstmt.setString(3, StringUtil.getStringFromList(networkInfo.getGenerations()));
            pstmt.setInt(4, networkInfo.getNumberOfData());
            pstmt.setLong(5, networkInfo.getTrainingDuration());
            pstmt.setFloat(6, networkInfo.getWeightMinValue());
            pstmt.setFloat(7, networkInfo.getWeightMaxValue());
            pstmt.setFloat(8, networkInfo.getBiasMinValue());
            pstmt.setFloat(9, networkInfo.getBiasMaxValue());
            pstmt.setString(10, networkInfo.getTransferFunction().name());
            pstmt.setFloat(11, networkInfo.getLearningRate());
            pstmt.setFloat(12, networkInfo.getMinError());
            pstmt.setLong(13, networkInfo.getTrainingMaxIteration());
            pstmt.setLong(14, networkInfo.getNumberOfTrainingDataInOneIteration());
            pstmt.setString(15, StringUtil.getStringFromCharSequence(networkInfo.getCharSequence()));
            pstmt.setString(16, StringUtil.getStringFromIntegerList(networkInfo.getHiddenLayer()));
            pstmt.setString(17, networkInfo.getNetworkProcessorType().name());
            pstmt.setString(18, networkInfo.getNetworkMetaInfo());
            pstmt.setString(19, networkInfo.getDescription());
            pstmt.executeUpdate();
            String idSql = "SELECT MAX(id) AS max_id FROM network_info";
            pstmt = DatabaseUtil.getConnection().prepareStatement(idSql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt("max_id");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return -1;
    }

    @Override
    public List<NetworkInfo> getNetworkInfoList(Integer id, String generation) {
        List<NetworkInfo> networkInfoList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM network_info WHERE 1 = 1 ";
            if (id != null) {
                sql += "AND id = '" + id + "' ";
            }
            if (generation != null) {
                sql += "AND generation = '" + generation + "' ";
            }
            sql += " ORDER BY id DESC;";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                NetworkInfo networkInfo = new NetworkInfo();
                networkInfo.setId(rs.getInt("id"));
                networkInfo.setWidth(rs.getInt("width"));
                networkInfo.setHeight(rs.getInt("height"));
                networkInfo.setGeneration(StringUtil.getListFromString(rs.getString("generations")));
                networkInfo.setNumberOfData(rs.getInt("number_of_data"));
                networkInfo.setTrainingDuration(rs.getLong("training_duration"));
                networkInfo.setWeightMinValue(rs.getFloat("weight_min_value"));
                networkInfo.setWeightMaxValue(rs.getFloat("weight_max_value"));
                networkInfo.setBiasMinValue(rs.getFloat("bias_min_value"));
                networkInfo.setBiasMaxValue(rs.getFloat("bias_max_value"));
                networkInfo.setTransferFunction(TransferFunction.valueOf(rs.getString("transfer_function_type")));
                networkInfo.setLearningRate(rs.getFloat("learning_rate"));
                networkInfo.setMinError(rs.getFloat("min_error"));
                networkInfo.setTrainingMaxIteration(rs.getLong("training_max_iteration"));
                networkInfo.setNumberOfTrainingDataInOneIteration(rs.getLong("number_of_training_data_in_one_iteration"));
                networkInfo.setCharSequence(StringUtil.getCharSequenceFromString(rs.getString("char_sequence")));
                networkInfo.setHiddenLayer(StringUtil.getIntegerListFromString(rs.getString("hidden_layer")));
                networkInfo.setNetworkProcessorType(NetworkProcessorType.valueOf(rs.getString("network_processor_type")));
                networkInfo.setNetworkMetaInfo(rs.getString("network_meta_info"));
                networkInfo.setDescription(rs.getString("description"));
                networkInfoList.add(networkInfo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return networkInfoList;
    }

    @Override
    public void deleteNetworkInfo(int id) {
        try {
            String sql = "DELETE fROM testing_info WHERE network_id = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            sql = "DELETE FROM network_info WHERE id = ?";
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
