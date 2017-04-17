package ge.edu.tsu.hrs.control_panel.console.fx.ui.component;

import javafx.scene.control.RadioButton;

public class TCHRadioButton extends RadioButton {

    public TCHRadioButton() {
        initComponent();
    }

    public TCHRadioButton(String text) {
        this();
        this.setText(text);
    }

    private void initComponent() {
        this.setStyle("-fx-font-family: sylfaen; -fx-font-size: 14px;");
    }
}
