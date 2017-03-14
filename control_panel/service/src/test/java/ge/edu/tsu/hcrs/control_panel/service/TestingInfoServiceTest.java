package ge.edu.tsu.hcrs.control_panel.service;

import ge.edu.tsu.hcrs.control_panel.model.network.TestingInfo;
import ge.edu.tsu.hcrs.control_panel.service.testinginfo.TestingInfoService;
import ge.edu.tsu.hcrs.control_panel.service.testinginfo.TestingInfoServiceImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class TestingInfoServiceTest {

	@Test
	@Ignore
	public void testGetTestingInfoListByNetworkId() {
		TestingInfoService testingInfoService = new TestingInfoServiceImpl();
		List<TestingInfo> testingInfoList = testingInfoService.getTestingInfoListByNetworkId(3);
		for (TestingInfo testingInfo : testingInfoList) {
			System.out.println(testingInfo.getId());
		}
	}
}
