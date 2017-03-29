package ge.edu.tsu.hrs.control_panel.service.common;

import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.server.processor.common.HRSPathProcessor;

public class HRSPathServiceImpl implements HRSPathService {

	private static final HRSPathProcessor hrsPathProcessor = new HRSPathProcessor();

	@Override
	public String getPath(HRSPath hrsPath) {
		return hrsPathProcessor.getPath(hrsPath);
	}
}
