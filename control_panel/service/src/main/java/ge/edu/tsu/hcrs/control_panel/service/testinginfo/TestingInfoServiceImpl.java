package ge.edu.tsu.hcrs.control_panel.service.testinginfo;

import ge.edu.tsu.hcrs.control_panel.model.network.TestingInfo;
import ge.edu.tsu.hcrs.control_panel.server.dao.testinginfo.TestingInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.testinginfo.TestingInfoDAOImpl;

import java.util.List;

public class TestingInfoServiceImpl implements TestingInfoService {

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    @Override
    public void addTestingInfo(TestingInfo testingInfo) {
        testingInfoDAO.addTestingInfo(testingInfo);
    }

    @Override
    public List<TestingInfo> getTestingInfoListByNetworkId(int networkId) {
        return testingInfoDAO.getTestingInfoListByNetworkId(networkId);
    }
}
