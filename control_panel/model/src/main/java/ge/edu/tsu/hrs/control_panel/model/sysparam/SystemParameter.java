package ge.edu.tsu.hrs.control_panel.model.sysparam;

import java.io.Serializable;

public class SystemParameter implements Serializable {

    private int id;

    private String key;

    private String value;

    private SysParamType type;

    public SystemParameter() {
    }

    public SystemParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public SystemParameter(int id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public SystemParameter(int id, String key, String value, SysParamType type) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SysParamType getType() {
        return type;
    }

    public void setType(SysParamType type) {
        this.type = type;
    }
}
