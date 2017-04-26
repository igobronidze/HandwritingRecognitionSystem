package ge.edu.tsu.hrs.control_panel.console.fx.ui.cleanimage;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.ImageFactory;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin.BlurringType;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.morphological.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.morphological.MorphologicalType;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.threshold.ThresholdParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.threshold.ThresholdType;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathService;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.imageprocessing.ImageProcessingService;
import ge.edu.tsu.hrs.control_panel.service.imageprocessing.ImageProcessingServiceImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CleanImagePane extends VBox {

    private static final double MAIN_PARAMETERS_PANE_WIDTH = 280;

    public static final double TOP_PANE_PART = 0.7;

    private final ImageProcessingService imageProcessingService = new ImageProcessingServiceImpl();

    private final HRSPathService hrsPathService = new HRSPathServiceImpl();

    private TCHComboBox blurringComboBox;

    private TCHComboBox thresholdComboBox;

    private TCHComboBox morphologicalComboBox;

    private TCHButton convertButton;

    private TCHButton toCutPageButton;

    private HBox bottomHBox;

    private BlurringPane blurringPane;

    private ThresholdPane thresholdPane;

    private MorphologicalPane morphologicalPane;

    private ImageView srcImageView;

    private ImageView resultImageView;

    private String imageName;

    public CleanImagePane() {
        initMainPain();
    }

    private void initMainPain() {
        this.setSpacing(3);
        this.getChildren().addAll(getTopPane(), getBottomPane());
    }

    private HBox getTopPane() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 0, 5));
        srcImageView = new ImageView();
        srcImageView.setImage(ImageFactory.getImage("no_photo.png"));
        srcImageView.fitHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(TOP_PANE_PART));
        srcImageView.fitWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(MAIN_PARAMETERS_PANE_WIDTH).divide(2));
        srcImageView.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
        srcImageView.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
        srcImageView.setOnMouseClicked(event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(hrsPathService.getPath(HRSPath.ORIGINAL_IMAGES_PATH)));
                fileChooser.setTitle(Messages.get("openSrcImage"));
                File file = fileChooser.showOpenDialog(ControlPanel.getStage());
                srcImageView.setImage(new Image(file.toURI().toURL().toExternalForm()));
                convertButton.setDisable(false);
                imageName = file.getName();
            } catch (Exception ex) {
                srcImageView.setImage(ImageFactory.getImage("no_photo.png"));
                imageName = "";
                convertButton.setDisable(true);
                System.out.println("Can't load image!");
            }
        });
        resultImageView = new ImageView();
        resultImageView.setImage(ImageFactory.getImage("no_photo.png"));
        resultImageView.fitHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(TOP_PANE_PART));
        resultImageView.fitWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(MAIN_PARAMETERS_PANE_WIDTH).divide(2));
        hBox.getChildren().addAll(srcImageView, getMainParametersPane(), resultImageView);
        resultImageView.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
        resultImageView.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
        resultImageView.setOnMouseClicked(event -> {
            if (imageName != null && !imageName.isEmpty()) {
                try {
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle(Messages.get("resultDirectory"));
                    directoryChooser.setInitialDirectory(new File(hrsPathService.getPath(HRSPath.CLEANED_IMAGES_PATH)));
                    File directory = directoryChooser.showDialog(ControlPanel.getStage());
                    if (directory != null) {
                        BufferedImage resultImage = SwingFXUtils.fromFXImage(resultImageView.getImage(), null);
                        ImageIO.write(resultImage, "png", new File(directory.getPath() + "/" + imageName));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return hBox;
    }

    private VBox getMainParametersPane() {
        VBox mainParametersVBox = new VBox();
        mainParametersVBox.setSpacing(15);
        mainParametersVBox.setPrefWidth(MAIN_PARAMETERS_PANE_WIDTH);
        mainParametersVBox.setAlignment(Pos.TOP_CENTER);
        mainParametersVBox.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(TOP_PANE_PART));
        CheckBox convertGrayCheckBox = new CheckBox();
        convertGrayCheckBox.setSelected(true);
        convertGrayCheckBox.setDisable(true);
        TCHFieldLabel convertGrayFieldLabel = new TCHFieldLabel(Messages.get("convertGrayIfNeed"), convertGrayCheckBox);
        blurringComboBox = new TCHComboBox(Arrays.asList(BlurringType.values()));
        TCHFieldLabel blurringFieldLabel = new TCHFieldLabel(Messages.get("blurringType"), blurringComboBox);
        thresholdComboBox = new TCHComboBox(Arrays.asList(ThresholdType.values()));
        TCHFieldLabel thresholdFieldLabel = new TCHFieldLabel(Messages.get("thresholdType"), thresholdComboBox);
        morphologicalComboBox = new TCHComboBox(Arrays.asList(MorphologicalType.values()));
        TCHFieldLabel morphologicalFieldLabel = new TCHFieldLabel(Messages.get("morphologicalType"), morphologicalComboBox);
        convertButton = new TCHButton(Messages.get("convert"));
        convertButton.setDisable(true);
        convertButton.setOnAction(event -> {
            BlurringParameters blurringParameters = blurringPane.getBlurringParameters();
            ThresholdParameters thresholdParameters = thresholdPane.getThresholdParameters();
            MorphologicalParameters morphologicalParameters = morphologicalPane.getMorphologicalParameters();
            BufferedImage srcImage = SwingFXUtils.fromFXImage(srcImageView.getImage(), null);
            BufferedImage bufferedImage = imageProcessingService.cleanImage(srcImage, blurringParameters, thresholdParameters, morphologicalParameters);
            Image resultImage = SwingFXUtils.toFXImage(bufferedImage, null);
            resultImageView.setImage(resultImage);
            toCutPageButton.setDisable(false);
        });
        toCutPageButton = new TCHButton(Messages.get("toCutPage"));
        toCutPageButton.setOnAction(event -> {
            ControlPanel.loadCutSymbolPane(SwingFXUtils.fromFXImage(resultImageView.getImage(), null));
        });
        toCutPageButton.setDisable(true);
        mainParametersVBox.getChildren().addAll(convertGrayFieldLabel, blurringFieldLabel, thresholdFieldLabel, morphologicalFieldLabel, convertButton, toCutPageButton);
        return mainParametersVBox;
    }

    private ScrollPane getBottomPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        initListeners();
        bottomHBox = new HBox();
        bottomHBox.setPadding(new Insets(5, 5, 0, 5));
        bottomHBox.setSpacing(20);
        scrollPane.setContent(bottomHBox);
        blurringPane = new BlurringPane(BlurringType.valueOf(blurringComboBox.getValue().toString()));
        thresholdPane = new ThresholdPane(ThresholdType.valueOf(thresholdComboBox.getValue().toString()));
        morphologicalPane = new MorphologicalPane(MorphologicalType.valueOf(morphologicalComboBox.getValue().toString()));
        updatePanes();
        return scrollPane;
    }

    private void initListeners() {
        blurringComboBox.valueProperty().addListener((ov, t, t1) -> {
            blurringPane = new BlurringPane(BlurringType.valueOf(blurringComboBox.getValue().toString()));
            updatePanes();
        });
        thresholdComboBox.valueProperty().addListener((ov, t, t1) -> {
            thresholdPane = new ThresholdPane(ThresholdType.valueOf(thresholdComboBox.getValue().toString()));
            updatePanes();
        });
        morphologicalComboBox.valueProperty().addListener((ov, t, t1) -> {
            morphologicalPane = new MorphologicalPane(MorphologicalType.valueOf(morphologicalComboBox.getValue().toString()));
            updatePanes();
        });
    }

    private void updatePanes() {
        bottomHBox.getChildren().clear();
        bottomHBox.getChildren().add(blurringPane);
        bottomHBox.getChildren().add(thresholdPane);
        bottomHBox.getChildren().addAll(morphologicalPane);
    }
}
