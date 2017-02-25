package ge.edu.tsu.hcrs.control_panel.service.systemparameter;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SysParamType;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SystemParameter;
import ge.edu.tsu.hcrs.control_panel.server.caching.CachedSystemParameter;
import ge.edu.tsu.hcrs.control_panel.server.dao.SystemParameterDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.SystemParameterDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.manager.SystemParameterProcessor;

import java.util.List;

public class SystemParameterServiceImpl implements SystemParameterService {

    private SystemParameterDAO systemParameterDAO = new SystemParameterDAOImpl();

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    @Override
    public void addSystemParameter(SystemParameter systemParameter) throws ControlPanelException {
        systemParameterDAO.addSystemParameter(systemParameter);
        CachedSystemParameter.fillParameters();
    }

    @Override
    public void editSystemParameter(SystemParameter systemParameter) throws ControlPanelException {
        systemParameterDAO.editSystemParameter(systemParameter);
        CachedSystemParameter.fillParameters();
    }

    @Override
    public void deleteSystemParameter(String key) throws ControlPanelException {
        systemParameterDAO.deleteSystemParameter(key);
        CachedSystemParameter.fillParameters();
    }

    @Override
    public List<SystemParameter> getSystemParameters(String key, SysParamType type) {
        return systemParameterDAO.getSystemParameters(key, type);
    }

    @Override
    public String getParameterValue(Parameter parameter) {
        return systemParameterProcessor.getParameterValue(parameter);
    }

    @Override
    public Integer getIntegerParameterValue(Parameter parameter) {
        return systemParameterProcessor.getIntegerParameterValue(parameter);
    }

    @Override
    public Float getFloatParameterValue(Parameter parameter) {
        return systemParameterProcessor.getFloatParameterValue(parameter);
    }

    @Override
    public Long getLongParameterValue(Parameter parameter) {
        return systemParameterProcessor.getLongParameterValue(parameter);
    }

    @Override
    public List<Integer> getIntegerListParameterValue(Parameter parameter) {
        return systemParameterProcessor.getIntegerListParameterValue(parameter);
    }
}
