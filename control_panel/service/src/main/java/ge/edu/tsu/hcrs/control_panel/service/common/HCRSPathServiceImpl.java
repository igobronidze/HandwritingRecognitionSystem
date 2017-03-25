package ge.edu.tsu.hcrs.control_panel.service.common;

import ge.edu.tsu.hcrs.control_panel.model.common.HCRSPath;
import ge.edu.tsu.hcrs.control_panel.server.processor.common.HCRSPathProcessor;

public class HCRSPathServiceImpl implements HCRSPathService {

    private static final HCRSPathProcessor hcrsPathProcessor = new HCRSPathProcessor();

    @Override
    public String getPath(HCRSPath hcrsPath) {
        return hcrsPathProcessor.getPath(hcrsPath);
    }
}
