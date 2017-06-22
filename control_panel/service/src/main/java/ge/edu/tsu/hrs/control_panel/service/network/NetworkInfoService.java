package ge.edu.tsu.hrs.control_panel.service.network;

import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;

import java.util.List;

public interface NetworkInfoService {

    List<NetworkInfo> getNetworkInfoList(Integer id);

    void deleteNetworkInfo(int id);
}
