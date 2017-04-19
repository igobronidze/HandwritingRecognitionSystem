package ge.edu.tsu.hrs.control_panel.console.fx.ui.component;

import javafx.scene.control.CheckBox;

public class TCHCheckBox extends CheckBox {

	public TCHCheckBox() {
		initComponent();
	}

	public TCHCheckBox(String text) {
		this();
		this.setText(text);
	}

	private void initComponent() {
		this.setStyle("-fx-font-family: sylfaen; -fx-font-size: 14px;");
	}
}
