package ge.edu.tsu.hrs.control_panel.console.fx.ui.component;

import javafx.scene.control.Control;
import javafx.scene.layout.HBox;

public class TCHFieldLabel extends HBox {

	public TCHFieldLabel(String text, Control control) {
		initComponent(text, control);
	}

	private void initComponent(String text, Control control) {
		this.setSpacing(10);
		this.getChildren().addAll(new TCHLabel(text + ":"), control);
	}
}
