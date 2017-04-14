package ge.edu.tsu.hrs.control_panel.console.fx.ui.component;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.List;

public class TCHComboBox extends ComboBox {

    public TCHComboBox(List<Object> values) {
        initComponent(values);
    }

    private void initComponent(List<Object> values) {
        this.setStyle("-fx-font-family: sylfaen; -fx-font-size: 14px;");
        this.setPrefWidth(220);
        this.getItems().addAll(FXCollections.observableArrayList(values));
        if (this.getItems().size() != 0) {
            this.setValue(this.getItems().get(0));
        }
    }
}
