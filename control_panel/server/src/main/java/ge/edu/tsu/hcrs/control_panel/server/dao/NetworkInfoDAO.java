package ge.edu.tsu.hcrs.control_panel.server.dao;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;

import java.util.List;

public interface NetworkInfoDAO {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id, String generation);

    void deleteNetworkInfo(int id);

    Object[] getTrainingCurrentState(int id);

    void updateTrainingCurrentState(float currentSquaredError, long currentIterations, long currentDuration, int id);

    void updateTrainedState(long trainingDuration, int id);
}
