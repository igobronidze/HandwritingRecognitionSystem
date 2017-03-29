package ge.edu.tsu.hrs.control_panel.server.dao.testinginfo;

import ge.edu.tsu.hrs.control_panel.model.network.TestingInfo;

import java.util.List;

public interface TestingInfoDAO {

    void addTestingInfo(TestingInfo testingInfo);

    List<TestingInfo> getTestingInfoListByNetworkId(int networkId);
}
