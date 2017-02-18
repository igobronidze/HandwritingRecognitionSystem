package ge.edu.tsu.hcrs.control_panel.console;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SystemParameter;
import ge.edu.tsu.hcrs.control_panel.service.systemparameter.SystemParameterService;
import ge.edu.tsu.hcrs.control_panel.service.systemparameter.SystemParameterServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SystemParametersController {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    @RequestMapping(value = "/sys_param/add", method = RequestMethod.PUT)
    public SystemParameter addSystemParameter(@RequestParam(value="key") String key,
                                              @RequestParam(value="value") String value) throws ControlPanelException {
        SystemParameter systemParameter = new SystemParameter(key, value);
        systemParameterService.addSystemParameter(systemParameter);
        return systemParameter;
    }

    @RequestMapping(value = "/sys_param", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void getSystemParameterById(@RequestParam("key") String key) throws ControlPanelException {
        systemParameterService.deleteSystemParameter(key);
    }

    @RequestMapping(value = "/sys_param/all", method = RequestMethod.GET)
    public List<SystemParameter> getAllSystemParameters() {
        return systemParameterService.getSystemParameters(null);
    }
}
