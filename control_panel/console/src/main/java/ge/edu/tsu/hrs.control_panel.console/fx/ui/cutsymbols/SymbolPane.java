package ge.edu.tsu.hrs.control_panel.console.fx.ui.cutsymbols;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHCheckBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHRadioButton;
import ge.edu.tsu.hrs.control_panel.console.fx.util.ImageFactory;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.TextCutterParameters;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.service.imageprocessing.ImageProcessingService;
import ge.edu.tsu.hrs.control_panel.service.imageprocessing.ImageProcessingServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.systemparameter.SystemParameterService;
import ge.edu.tsu.hrs.control_panel.service.systemparameter.SystemParameterServiceImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SymbolPane extends VBox {

    private final SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private final ImageProcessingService imageProcessingService = new ImageProcessingServiceImpl();

    private final Parameter cutImageWidthOnUI = new Parameter("cutImageWidthOnUI", "115");

    private final Parameter cutImageHeightOnUI = new Parameter("cutImageHeightOnUI", "115");

    private Integer cachedWidth;

    private Integer cachedHeight;

    private BufferedImage image;

    private int index;

    private String symbol;

    private SymbolsPane symbolsPane;

    private int canvasPenSize = 4;

    private Color color = Color.WHITE;

    private TextCutterParameters parameters;

    private TextField textField;

    public SymbolPane(BufferedImage image, String symbol, int index, TextCutterParameters parameters, SymbolsPane symbolsPane) {
        this.image = image;
        this.index = index;
        this.symbol = symbol;
        this.symbolsPane = symbolsPane;
        this.parameters = parameters;
        this.setSpacing(3);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-border-color: green; -fx-border-radius: 25px; -fx-border-size: 1px;");
        this.setPadding(new Insets(10));
        this.getChildren().addAll(getImageView(), getHBox());
    }

    private ImageView getImageView() {
        ImageView imageView = new ImageView();
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
        if (cachedWidth == null) {
            cachedWidth = systemParameterService.getIntegerParameterValue(cutImageWidthOnUI);
        }
        if (cachedHeight == null) {
            cachedHeight = systemParameterService.getIntegerParameterValue(cutImageHeightOnUI);
        }
        imageView.setFitWidth(cachedWidth);
        imageView.setFitHeight(cachedHeight);
        return imageView;
    }

    private HBox getHBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(2);
        hBox.setAlignment(Pos.TOP_CENTER);
        ImageView leftArrowImage = new ImageView(ImageFactory.getImage("left.png"));
        leftArrowImage.setFitWidth(14);
        leftArrowImage.setFitHeight(14);
        Button leftButton = new Button("", leftArrowImage);
        leftButton.setOnAction(event -> {
            symbolsPane.getSymbols().set(index - 1, symbolsPane.getSymbols().get(index - 1) + symbolsPane.getSymbols().get(index));
            symbolsPane.getSymbols().remove(index);
            symbolsPane.updateSymbols();
        });
        textField = new TextField(symbol);
        textField.setStyle("-fx-font-family: sylfaen; -fx-font-size: 12px;");
        textField.setPrefWidth(30);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (symbolsPane.getSymbols().size() <= index) {
                for (int i = symbolsPane.getSymbols().size(); i <= index; i++) {
                    symbolsPane.getSymbols().add("");
                }
            }
            symbolsPane.getSymbols().set(index, newValue);
        });
        ImageView editImage = new ImageView(ImageFactory.getImage("edit.png"));
        editImage.setFitWidth(14);
        editImage.setFitHeight(14);
        Button editButton = new Button("", editImage);
        editButton.setOnAction(event -> {
            Stage stage = new Stage();
            stage.setTitle(Messages.get("edit"));
            stage.setWidth(380);
            stage.setHeight(380);
            Canvas canvas = new Canvas(200, 200);
            AnchorPane pane = new AnchorPane();
            pane.setPrefWidth(200 + 10);
            pane.setMaxWidth(200 + 10);
            pane.setMaxHeight(200 + 10);
            pane.setPrefHeight(200 + 10);
            pane.setStyle("-fx-border-color: green; -fx-border-style: solid; -fx-border-width: 6px; -fx-background-color: white");
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(SwingFXUtils.toFXImage(image, null), 0, 0,canvas.getWidth(), canvas.getHeight());
            canvas.setOnMouseDragged(event1 -> {
                for (int i = -canvasPenSize; i <= canvasPenSize; i++) {
                    for (int j = -canvasPenSize; j <= canvasPenSize; j++) {
                        gc.getPixelWriter().setColor((int) event1.getX() + i, (int) event1.getY() + j, color);
                    }
                }
            });
            pane.getChildren().addAll(canvas);
            AnchorPane.setTopAnchor(canvas, 2.0);
            AnchorPane.setLeftAnchor(canvas, 2.0);
            HBox mainHBox = new HBox();
            mainHBox.setSpacing(20);
            VBox parametersVBox = new VBox();
            parametersVBox.setAlignment(Pos.TOP_CENTER);
            parametersVBox.setSpacing(40);
            VBox colorsVBox = new VBox();
            colorsVBox.setAlignment(Pos.TOP_LEFT);
            colorsVBox.setSpacing(5);
            ToggleGroup colorGroup = new ToggleGroup();
            TCHRadioButton whiteRadioButton = new TCHRadioButton(Messages.get("white"));
            whiteRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    color = Color.WHITE;
                }
            });
            whiteRadioButton.setToggleGroup(colorGroup);
            whiteRadioButton.setSelected(true);
            TCHRadioButton blackRadioButton = new TCHRadioButton(Messages.get("black"));
            blackRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    color = Color.BLACK;
                }
            });
            blackRadioButton.setToggleGroup(colorGroup);
            TCHLabel colorLabel = new TCHLabel(Messages.get("color") + ":");
            colorsVBox.getChildren().addAll(colorLabel, whiteRadioButton, blackRadioButton);
            VBox sizeVBox = new VBox();
            sizeVBox.setAlignment(Pos.TOP_LEFT);
            sizeVBox.setSpacing(5);
            ToggleGroup sizeToggleGroup = new ToggleGroup();
            TCHRadioButton size1 = new TCHRadioButton("1");
            size1.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    canvasPenSize = 1;
                }
            });
            size1.setToggleGroup(sizeToggleGroup);
            size1.setSelected(true);
            TCHRadioButton size2 = new TCHRadioButton("2");
            size2.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    canvasPenSize = 2;
                }
            });
            size2.setToggleGroup(sizeToggleGroup);
            TCHRadioButton size3 = new TCHRadioButton("4");
            size3.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    canvasPenSize = 4;
                }
            });
            size3.setToggleGroup(sizeToggleGroup);
            TCHRadioButton size4 = new TCHRadioButton("8");
            size4.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    canvasPenSize = 8;
                }
            });
            size4.setToggleGroup(sizeToggleGroup);
            TCHLabel sizeLabel = new TCHLabel(Messages.get("size") + ":");
            sizeVBox.getChildren().addAll(sizeLabel, size1, size2, size3, size4);
            parametersVBox.getChildren().addAll(colorsVBox, sizeVBox);
            mainHBox.getChildren().addAll(pane, parametersVBox);

            TCHCheckBox userJoiningFunctionalCheckBox = new TCHCheckBox(Messages.get("useJoiningFunctional"));
            userJoiningFunctionalCheckBox.setSelected(parameters != null && parameters.isUseJoiningFunctional());

            TCHButton closeButton = new TCHButton(Messages.get("close"));
            closeButton.setOnAction(event1 -> stage.close());
            TCHButton saveButton = new TCHButton(Messages.get("save"));
            saveButton.setOnAction(event1 -> {
                WritableImage writableImage = new WritableImage(200, 200);
                canvas.snapshot(null, writableImage);
                if (parameters != null) {
                    parameters.setUseJoiningFunctional(userJoiningFunctionalCheckBox.isSelected());
                }
                List<BufferedImage> images = imageProcessingService.getCutSymbols(SwingFXUtils.fromFXImage(writableImage, null), parameters, !userJoiningFunctionalCheckBox.isSelected(), null);
                List<String> symbols = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    symbols.add("");
                }
                String text = textField.getText();
                for (int i = 0; i < Math.min(images.size(), text.length()); i++) {
                    symbols.set(i, text.charAt(i) + "");
                }
                symbolsPane.getSymbols().remove(index);
                symbolsPane.getImages().remove(index);
                symbolsPane.getSymbols().addAll(index, symbols);
                symbolsPane.getImages().addAll(index, images);
                symbolsPane.updateSymbols();
                stage.close();
            });
            HBox buttonsHBox = new HBox();
            buttonsHBox.setSpacing(15);
            buttonsHBox.setAlignment(Pos.CENTER);
            buttonsHBox.getChildren().addAll(saveButton, closeButton);
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.setPadding(new Insets(15, 15, 15, 15));
            vBox.setSpacing(20);
            vBox.getChildren().addAll(mainHBox, userJoiningFunctionalCheckBox, buttonsHBox);
            stage.setScene(new Scene(vBox));
            stage.showAndWait();
        });
        ImageView deleteImage = new ImageView(ImageFactory.getImage("delete.png"));
        deleteImage.setFitWidth(14);
        deleteImage.setFitHeight(14);
        Button deleteButton = new Button("", deleteImage);
        deleteButton.setOnAction(event -> {
            symbolsPane.getImages().remove(index);
            symbolsPane.updateSymbols();
        });
        hBox.getChildren().addAll(leftButton, textField, editButton, deleteButton);
        return hBox;
    }
}
