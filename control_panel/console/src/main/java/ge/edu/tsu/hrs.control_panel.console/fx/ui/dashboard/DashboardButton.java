package ge.edu.tsu.hrs.control_panel.console.fx.ui.dashboard;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class DashboardButton extends Button {

	public DashboardButton(String text) {
		this.setText(text);
		initUI();
	}

	private void initUI() {
		this.setPrefWidth(290);
		this.setPrefHeight(100);
		this.setStyle("-fx-font-family: sylfaen; -fx-font-size: 22px;");
		this.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
		this.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
	}
}
