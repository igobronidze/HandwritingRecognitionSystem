package ge.edu.tsu.hrs.control_panel.console.fx.ui.component;

import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TCHFieldLabel extends VBox {

	public TCHFieldLabel(String text, Control control) {
		initComponent(text, control);
	}

	private void initComponent(String text, Control control) {
		this.setSpacing(5);
		this.setAlignment(Pos.CENTER);
		TCHLabel label = new TCHLabel(text + ":");
		this.getChildren().addAll(label, control);
	}
}
