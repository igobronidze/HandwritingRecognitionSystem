package ge.edu.tsu.hrs.control_panel.service.networkinfo;

import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;

import java.util.List;

public interface NetworkInfoService {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id);

    void deleteNetworkInfo(int id);

    Object[] getTrainingCurrentState(int id);
}
