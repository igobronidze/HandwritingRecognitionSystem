package ge.edu.tsu.hrs.control_panel.service;

import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.service.network.NetworkInfoService;
import ge.edu.tsu.hrs.control_panel.service.network.NetworkInfoServiceImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class NetworkInfoServiceTest {

	@Test
	@Ignore
	public void testGetNetworkInfoList() {
		NetworkInfoService networkInfoService = new NetworkInfoServiceImpl();
		List<NetworkInfo> networkInfoList = networkInfoService.getNetworkInfoList(3);
		for (NetworkInfo networkInfo : networkInfoList) {
			System.out.println(networkInfo.getDescription());
		}
	}
}
