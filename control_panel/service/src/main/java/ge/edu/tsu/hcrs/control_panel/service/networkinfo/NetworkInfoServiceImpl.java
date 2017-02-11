package ge.edu.tsu.hcrs.control_panel.service.networkinfo;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.server.dao.NetworkInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.NetworkInfoDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.TestingInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.TestingInfoDAOImpl;

import java.util.List;

public class NetworkInfoServiceImpl implements NetworkInfoService {

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    @Override
    public int addNetworkInfo(NetworkInfo networkInfo) {
        return networkInfoDAO.addNetworkInfo(networkInfo);
    }

    @Override
    public List<NetworkInfo> getNetworkInfoList(Integer id, String generation) {
        List<NetworkInfo> networkInfoList = networkInfoDAO.getNetworkInfoList(id, generation);
        for (NetworkInfo networkInfo : networkInfoList) {
            networkInfo.setTestingInfoList(testingInfoDAO.getTestingInfoListByNetworkId(networkInfo.getId()));
        }
        return networkInfoList;
    }

    @Override
    public void deleteNetworkInfo(int id) {
        networkInfoDAO.deleteNetworkInfo(id);
    }
}
