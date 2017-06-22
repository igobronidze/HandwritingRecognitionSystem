package ge.edu.tsu.hrs.control_panel.console.fx.ui.textrecognition;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkResult;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecognizedSymbolPane extends VBox {

	private BufferedImage image;

	private NetworkResult networkResult;

	public RecognizedSymbolPane(BufferedImage image, NetworkResult networkResult) {
		this.image = image;
		this.networkResult = networkResult;
		initUI();
	}

	private void initUI() {
		this.setSpacing(3);
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.TOP_CENTER);
		this.setStyle("-fx-border-color: green; -fx-border-radius: 25px; -fx-border-size: 1px;");
		this.getChildren().addAll(getImagePane(), getSymbolPane());
	}

	private ImageView getImagePane() {
		ImageView imageView = new ImageView(SwingFXUtils.toFXImage(image, null));
		imageView.setFitWidth(80);
		imageView.setFitHeight(80);
		imageView.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
		imageView.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
		imageView.setOnMouseClicked(event -> showActivationsPane());
		return imageView;
	}

	private Label getSymbolPane() {
		Label symbolLabel = new Label();
		symbolLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 21px;");
		symbolLabel.setTextFill(Color.RED);
		symbolLabel.setText(networkResult.getAnswer() + "");
		return symbolLabel;
	}

	private void showActivationsPane() {
		Stage stage = new Stage();
		stage.setTitle(Messages.get("activation"));
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefHeight(490);
		flowPane.setPrefWidth(930);
		flowPane.setHgap(8);
		flowPane.setVgap(8);
		flowPane.setPadding(new Insets(20));
		List<Float> outputActivations = networkResult.getOutputActivation();
		List<SymbolActivation> symbolActivations = new ArrayList<>();
		for (int i = 0; i < outputActivations.size(); i++) {
			symbolActivations.add(new SymbolActivation(networkResult.getCharSequence().getIndexToCharMap().get(i), outputActivations.get(i)));
		}
		Collections.sort(symbolActivations);
		Collections.reverse(symbolActivations);
		for (SymbolActivation symbolActivation : symbolActivations) {
			VBox vBox = new VBox(3);
			vBox.setAlignment(Pos.TOP_CENTER);
			vBox.setPadding(new Insets(3));
			vBox.setPrefWidth(120);
			vBox.setStyle("-fx-border-color: green; -fx-border-size: 1px;");
			TCHLabel symbolLabel = new TCHLabel("");
			symbolLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 18px;");
			symbolLabel.setText(symbolActivation.symbol + "");
			symbolLabel.setTextFill(Color.GREEN);
			TCHLabel activationLabel = new TCHLabel("");
			activationLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
			activationLabel.setText(symbolActivation.activation + "");
			vBox.getChildren().addAll(symbolLabel, activationLabel);
			flowPane.getChildren().add(vBox);
		}
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(flowPane);
		stage.setScene(new Scene(scrollPane));
		stage.showAndWait();
	}

	private class SymbolActivation implements Comparable<SymbolActivation> {

		private char symbol;

		private float activation;

		private SymbolActivation(char symbol, float activation) {
			this.symbol = symbol;
			this.activation = activation;
		}

		@Override
		public int compareTo(SymbolActivation o) {
			return Float.compare(activation, o.activation);
		}
	}
}
