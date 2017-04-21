package ge.edu.tsu.hrs.control_panel.console.fx.ui.sysparams;

import ge.edu.tsu.hrs.control_panel.model.sysparam.SystemParameter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SystemParameterProperty {

    private SimpleIntegerProperty id;

    private SimpleStringProperty key;

    private SimpleStringProperty value;

    private SimpleStringProperty type;

    public SystemParameterProperty(SystemParameter systemParameter) {
        this.id = new SimpleIntegerProperty(systemParameter.getId());
        this.key = new SimpleStringProperty(systemParameter.getKey());
        this.value = new SimpleStringProperty(systemParameter.getValue());
        this.type = new SimpleStringProperty(systemParameter.getType().name());
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getKey() {
        return key.get();
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
