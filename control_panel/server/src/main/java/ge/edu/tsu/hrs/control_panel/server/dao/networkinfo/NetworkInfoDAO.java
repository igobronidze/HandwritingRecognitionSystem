package ge.edu.tsu.hrs.control_panel.server.dao.networkinfo;

import ge.edu.tsu.hrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;

import java.util.List;

public interface NetworkInfoDAO {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id);

    void deleteNetworkInfo(int id);

    void updateTrainingCurrentState(float currentSquaredError, long currentIterations, long currentDuration, int id);

    void updateTrainedState(long trainingDuration, int id);

    CharSequence getCharSequenceById(int networkId);

    void setFailedNetworkInfos();
}
