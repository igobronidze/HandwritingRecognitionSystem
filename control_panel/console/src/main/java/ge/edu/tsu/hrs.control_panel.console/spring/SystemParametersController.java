package ge.edu.tsu.hrs.control_panel.console.spring;

import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.model.sysparam.SysParamType;
import ge.edu.tsu.hrs.control_panel.model.sysparam.SystemParameter;
import ge.edu.tsu.hrs.control_panel.service.systemparameter.SystemParameterService;
import ge.edu.tsu.hrs.control_panel.service.systemparameter.SystemParameterServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class SystemParametersController {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    @RequestMapping(value = "/sysparams", method=RequestMethod.GET)
    public String sysparams(Map<String, Object> model) {
        model.put("systemParameters", systemParameterService.getSystemParameters(null, null));
        model.put("sysParamTypes", SysParamType.values());
        return "sysparams";
    }

    @RequestMapping(value = "/addSysParam", method=RequestMethod.POST)
    public String addSysParam(@RequestParam("key") String key,
                              @RequestParam("value") String value,
                              @RequestParam("type") String type,
                              @RequestParam(value = "action", required = false, defaultValue = "save") String action) {
        if(action.equals("save")) {
            SystemParameter systemParameter = new SystemParameter();
            systemParameter.setKey(key);
            systemParameter.setValue(value);
            systemParameter.setType(SysParamType.valueOf(type));
            try {
                systemParameterService.addSystemParameter(systemParameter);
            } catch (ControlPanelException e) {
                System.out.println(e.getMessage());
            }
        } else if(action.equals("edit")) {
            SystemParameter systemParameter = new SystemParameter();
            systemParameter.setKey(key);
            systemParameter.setValue(value);
            systemParameter.setType(SysParamType.valueOf(type));
            try {
                systemParameterService.editSystemParameter(systemParameter);
            } catch (ControlPanelException e) {
                System.out.println(e.getMessage());
            }
        } else if(action.equals("delete")) {
            try {
                systemParameterService.deleteSystemParameter(key);
            } catch (ControlPanelException e) {
                System.out.println(e.getMessage());
            }
        }
        return "redirect:";
    }
}
