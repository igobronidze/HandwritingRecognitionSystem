package ge.edu.tsu.hrs.control_panel.console.fx.ui.dashboard;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.SystemPageType;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

public class AdminDashboard extends FlowPane {

	public AdminDashboard() {
		initUI();
		addButtons();
	}

	private void addButtons() {
		DashboardButton networkControlButton = new DashboardButton(Messages.get("networkControl"));
		networkControlButton.setOnAction(event -> {
			ControlPanel.initComponents(SystemPageType.NETWORK_CONTROL);
		});
		DashboardButton cleanImageButton = new DashboardButton(Messages.get("cleanImage"));
		cleanImageButton.setOnAction(event -> {
			ControlPanel.initComponents(SystemPageType.CLEAN_IMAGE);
		});
		DashboardButton cutSymbolsButton = new DashboardButton(Messages.get("cutSymbols"));
		cutSymbolsButton.setOnAction(event -> {
			ControlPanel.initComponents(SystemPageType.CUT_SYMBOLS);
		});
		DashboardButton normalizationButton = new DashboardButton(Messages.get("normalization"));
		normalizationButton.setOnAction(event -> {
			ControlPanel.initComponents(SystemPageType.NORMALIZATION);
		});
		DashboardButton systemParameterButton = new DashboardButton(Messages.get("systemParameters"));
		systemParameterButton.setOnAction(event -> {
			ControlPanel.initComponents(SystemPageType.SYSTEM_PARAMETERS);
		});
		DashboardButton textRecognitionButton = new DashboardButton(Messages.get("textRecognition"));
		textRecognitionButton.setOnAction(event -> {
			ControlPanel.initComponents(SystemPageType.TEXT_RECOGNITION);
		});
		this.getChildren().addAll(networkControlButton, cleanImageButton, cutSymbolsButton, normalizationButton, systemParameterButton, textRecognitionButton);
	}

	private void initUI() {
		this.setAlignment(Pos.TOP_CENTER);
		this.setVgap(15);
		this.setHgap(15);
		this.setPadding(new Insets(20, 20, 20, 20));
	}
}
