package ge.edu.tsu.hrs.control_panel.service.networkinfo;

import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.testinginfo.TestingInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.testinginfo.TestingInfoDAOImpl;

import java.util.List;

public class NetworkInfoServiceImpl implements NetworkInfoService {

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    @Override
    public int addNetworkInfo(NetworkInfo networkInfo) {
        return networkInfoDAO.addNetworkInfo(networkInfo);
    }

    @Override
    public List<NetworkInfo> getNetworkInfoList(Integer id) {
        List<NetworkInfo> networkInfoList = networkInfoDAO.getNetworkInfoList(id);
        for (NetworkInfo networkInfo : networkInfoList) {
            networkInfo.setTestingInfoList(testingInfoDAO.getTestingInfoListByNetworkId(networkInfo.getId()));
        }
        return networkInfoList;
    }

    @Override
    public void deleteNetworkInfo(int id) {
        networkInfoDAO.deleteNetworkInfo(id);
    }

    @Override
    public Object[] getTrainingCurrentState(int id) {
        return networkInfoDAO.getTrainingCurrentState(id);
    }
}
