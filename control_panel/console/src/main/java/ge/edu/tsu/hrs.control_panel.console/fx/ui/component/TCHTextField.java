package ge.edu.tsu.hrs.control_panel.console.fx.ui.component;

import javafx.scene.control.TextField;

public class TCHTextField extends TextField {

	public TCHTextField(TCHComponentSize size) {
		initComponent(size);
	}

	private void initComponent(TCHComponentSize size) {
		this.setStyle("-fx-font-family: sylfaen; -fx-font-size: 18px;");
		switch (size) {
			case SMALL:
				this.setWidth(100);
				break;
			case MEDIUM:
				this.setWidth(200);
				break;
			case LARGE:
				this.setWidth(300);
				break;
		}
	}
}
