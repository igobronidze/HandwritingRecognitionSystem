package ge.edu.tsu.hrs.control_panel.model.sysparam;

import java.io.Serializable;

public class Parameter implements Serializable {

    private String key;

    private String defaultValue;

    public Parameter() {
    }

    public Parameter(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
