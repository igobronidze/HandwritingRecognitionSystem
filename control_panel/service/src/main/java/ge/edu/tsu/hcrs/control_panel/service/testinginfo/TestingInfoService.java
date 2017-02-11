package ge.edu.tsu.hcrs.control_panel.service.testinginfo;

import ge.edu.tsu.hcrs.control_panel.model.network.TestingInfo;

import java.util.List;

public interface TestingInfoService {

    void addTestingInfo(TestingInfo testingInfo);

    List<TestingInfo> getTestingInfoListByNetworkId(int networkId);
}
