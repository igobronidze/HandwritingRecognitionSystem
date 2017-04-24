package ge.edu.tsu.hrs.control_panel.console.fx.ui.textrecognition;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHNumberTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextArea;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.ImageFactory;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathService;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextRecognitionPane extends VBox {

    private final HRSPathService hrsPathService = new HRSPathServiceImpl();

    private NeuralNetworkService neuralNetworkService;

    private ImageView srcImageView;

    private ImageView cleanedImageView;

    private TCHTextArea textArea;

    private TCHButton recognizeButton;

    private final double TOP_PANE_PART = 0.55;

    public TextRecognitionPane() {
        initUI();
    }

    private void initUI() {
        this.setSpacing(10);
        this.setPadding(new Insets(5));
        this.getChildren().addAll(getTopPane(), getBottomPane());
    }

    private HBox getTopPane() {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        srcImageView = new ImageView();
        srcImageView.setImage(ImageFactory.getImage("no_photo.png"));
        srcImageView.fitHeightProperty().bind(ControlPanel.getCenterHeightBinding().subtract(20).multiply(TOP_PANE_PART));
        srcImageView.fitWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(25).multiply(0.3));
        srcImageView.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
        srcImageView.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
        srcImageView.setOnMouseClicked(event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(Messages.get("openSrcImage"));
                fileChooser.setInitialDirectory(new File(hrsPathService.getPath(HRSPath.ORIGINAL_IMAGES_PATH)));
                File file = fileChooser.showOpenDialog(ControlPanel.getStage());
                srcImageView.setImage(new Image(file.toURI().toURL().toExternalForm()));
                recognizeButton.setDisable(false);
            } catch (Exception ex) {
                srcImageView.setImage(ImageFactory.getImage("no_photo.png"));
                recognizeButton.setDisable(true);
                ex.printStackTrace();
            }
        });
        cleanedImageView = new ImageView();
        cleanedImageView.setImage(ImageFactory.getImage("no_photo.png"));
        cleanedImageView.fitHeightProperty().bind(ControlPanel.getCenterHeightBinding().subtract(20).multiply(TOP_PANE_PART));
        cleanedImageView.fitWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(25).multiply(0.3));
        textArea = new TCHTextArea();
        textArea.setEditable(false);
        textArea.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().subtract(20).multiply(TOP_PANE_PART));
        textArea.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(25).multiply(0.4));
        hBox.getChildren().addAll(srcImageView, cleanedImageView, textArea);
        return hBox;
    }

    private HBox getBottomPane() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        TCHComboBox processorComboBox = new TCHComboBox(Arrays.asList(NetworkProcessorType.values()));
        TCHFieldLabel processorFieldLabel = new TCHFieldLabel(Messages.get("networkProcessorType"), processorComboBox);
        TCHNumberTextField networkIdField = new TCHNumberTextField(new BigDecimal(-1), TCHComponentSize.SMALL);
        TCHFieldLabel networkIdFIeFieldLabel = new TCHFieldLabel(Messages.get("networkId"), networkIdField);
        TCHNumberTextField extraIdField = new TCHNumberTextField(new BigDecimal(0), TCHComponentSize.SMALL);
        TCHFieldLabel extraIdFieldLabel = new TCHFieldLabel(Messages.get("networkExtraId"), extraIdField);
        recognizeButton = new TCHButton(Messages.get("recognize"));
        recognizeButton.setDisable(true);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(237));
        scrollPane.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().subtract(20).multiply(1 - TOP_PANE_PART));
        recognizeButton.setOnAction(event -> {
            neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.valueOf(processorComboBox.getValue().toString()));
            List<BufferedImage> images = new ArrayList<>();
            images.add(SwingFXUtils.fromFXImage(srcImageView.getImage(), null));
            List<RecognitionInfo> recognitionInfoList = neuralNetworkService.recognizeText(images, networkIdField.getNumber().intValue(), extraIdField.getNumber().intValue(), true);
            try {
                RecognitionInfo recognitionInfo = recognitionInfoList.get(0);
                textArea.setText(recognitionInfo.getText());
                cleanedImageView.setImage(SwingFXUtils.toFXImage(recognitionInfo.getCleanedImage(), null));
                scrollPane.setContent(getSymbolsPane(recognitionInfo));
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        });
        vBox.getChildren().addAll(processorFieldLabel, networkIdFIeFieldLabel, extraIdFieldLabel, recognizeButton);
        hBox.getChildren().addAll(vBox, scrollPane);
        return hBox;
    }

    private FlowPane getSymbolsPane(RecognitionInfo recognitionInfo) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5));
        flowPane.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().subtract(20).multiply(1 - TOP_PANE_PART));
        flowPane.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(255));
        flowPane.setVgap(7);
        flowPane.setHgap(7);
        List<BufferedImage> symbolImages = recognitionInfo.getCutSymbolImages();
        List<NetworkResult> networkResults = recognitionInfo.getNetworkResults();
        for (int i = 0; i < symbolImages.size(); i++) {
            flowPane.getChildren().add(new RecognizedSymbolPane(symbolImages.get(i), networkResults.get(i)));
        }
        return flowPane;
    }
}
