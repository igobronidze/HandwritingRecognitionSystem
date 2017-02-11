package ge.edu.tsu.hcrs.control_panel.service.systemparameter;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SystemParameter;
import ge.edu.tsu.hcrs.control_panel.server.dao.SystemParameterDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.SystemParameterDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.manager.SystemParameterManager;

import java.util.ArrayList;
import java.util.List;

public class SystemParameterServiceImpl implements SystemParameterService {

    private SystemParameterDAO systemParameterDAO = new SystemParameterDAOImpl();

    private SystemParameterManager systemParameterManager = new SystemParameterManager();

    @Override
    public void addSystemParameter(SystemParameter systemParameter) throws ControlPanelException {
        systemParameterDAO.addSystemParameter(systemParameter);
    }

    @Override
    public void editSystemParameter(SystemParameter systemParameter) throws ControlPanelException {
        systemParameterDAO.editSystemParameter(systemParameter);
    }

    @Override
    public void deleteSystemParameter(String key) throws ControlPanelException {
        systemParameterDAO.deleteSystemParameter(key);
    }

    @Override
    public List<SystemParameter> getSystemParameters(String key) {
        return systemParameterDAO.getSystemParameters(key);
    }

    @Override
    public String getParameterValue(Parameter parameter) {
        return systemParameterManager.getParameterValue(parameter);
    }

    @Override
    public Integer getIntegerParameterValue(Parameter parameter) {
        return systemParameterManager.getIntegerParameterValue(parameter);
    }

    @Override
    public Float getFloatParameterValue(Parameter parameter) {
        return systemParameterManager.getFloatParameterValue(parameter);
    }

    @Override
    public Long getLongParameterValue(Parameter parameter) {
        return systemParameterManager.getLongParameterValue(parameter);
    }

    @Override
    public List<Integer> getIntegerListParameterValue(Parameter parameter) {
        return systemParameterManager.getIntegerListParameterValue(parameter);
    }
}
