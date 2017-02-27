package ge.edu.tsu.hcrs.control_panel.service.networkinfo;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;

import java.util.List;

public interface NetworkInfoService {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id, String generation);

    void deleteNetworkInfo(int id);

    Object[] getTrainingCurrentState(int id);
}
