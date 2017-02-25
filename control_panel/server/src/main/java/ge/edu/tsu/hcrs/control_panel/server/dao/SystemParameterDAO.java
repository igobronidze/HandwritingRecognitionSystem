package ge.edu.tsu.hcrs.control_panel.server.dao;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SysParamType;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.SystemParameter;

import java.util.List;

public interface SystemParameterDAO {

    void addSystemParameter(SystemParameter systemParameter) throws ControlPanelException;

    void editSystemParameter(SystemParameter systemParameter) throws ControlPanelException;

    void deleteSystemParameter(String key) throws ControlPanelException;

    List<SystemParameter> getSystemParameters(String key, SysParamType type);
}
