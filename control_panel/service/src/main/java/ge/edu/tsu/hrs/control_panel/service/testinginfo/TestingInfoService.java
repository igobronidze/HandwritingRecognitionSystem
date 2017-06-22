package ge.edu.tsu.hrs.control_panel.service.testinginfo;

import ge.edu.tsu.hrs.control_panel.model.network.TestingInfo;

import java.util.List;

public interface TestingInfoService {

    List<TestingInfo> getTestingInfoListByNetworkId(int networkId);
}
