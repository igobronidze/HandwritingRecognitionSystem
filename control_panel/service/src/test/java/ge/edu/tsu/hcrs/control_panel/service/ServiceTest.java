package ge.edu.tsu.hcrs.control_panel.service;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SysParamType;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SystemParameter;
import ge.edu.tsu.hcrs.control_panel.service.systemparameter.SystemParameterService;
import ge.edu.tsu.hcrs.control_panel.service.systemparameter.SystemParameterServiceImpl;
import org.junit.Ignore;
import org.junit.Test;

public class ServiceTest {

    @Test
    @Ignore
    public void testSysParamService() {
        SystemParameterService systemParameterService = new SystemParameterServiceImpl();
//        SystemParameter systemParameter = new SystemParameter();
//        systemParameter.setKey("testKey2");
//        systemParameter.setValue("testValue2");
//        systemParameter.setType(SysParamType.NEURAL_NETWORK);
//        try {
//            systemParameterService.addSystemParameter(systemParameter);
//        } catch (ControlPanelException ex) {
//            System.out.println(ex.getMessage());
//        }
//        System.out.println(systemParameterService.getSystemParameters("", SysParamType.CONTROL_PANEL).size());

    }
}
