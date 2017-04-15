package ge.edu.tsu.hrs.control_panel.console.fx.ui.cleanimage;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanelFooter;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanelHeader;
import ge.edu.tsu.hrs.control_panel.console.fx.util.ImageFactory;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringType;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalType;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Arrays;

public class CleanImagePane extends VBox {

    private static final double MAIN_PARAMETERS_PANE_WIDTH = 300;

    public static final double TOP_PANE_PART = 0.65;

    private TCHComboBox blurringComboBox;

    private TCHComboBox thresholdComboBox;

    private TCHComboBox morphologicalComboBox;

    private HBox bottomHBox;

    public CleanImagePane() {
        initMainPain();
    }

    private void initMainPain() {
        this.getChildren().addAll(getTopPane(), getBottomPane());
    }

    private HBox getTopPane() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 0, 5));
        ImageView srcImageView = new ImageView();
        srcImageView.setImage(ImageFactory.getImage("no_image.png"));
        srcImageView.fitHeightProperty().bind(ControlPanel.getStage().heightProperty().subtract(ControlPanelHeader.LOGO_HEIGHT).subtract(ControlPanelFooter.HEIGHT).multiply(TOP_PANE_PART).subtract(5));
        srcImageView.fitWidthProperty().bind(ControlPanel.getStage().widthProperty().subtract(MAIN_PARAMETERS_PANE_WIDTH).divide(2));
        srcImageView.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
        srcImageView.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
        srcImageView.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(Messages.get("openSrcImage"));
            File file = fileChooser.showOpenDialog(ControlPanel.getStage());
            System.out.println(file.getAbsolutePath());
            srcImageView.setImage(new Image(file.getAbsolutePath()));
        });
        ImageView resultImageView = new ImageView();
        resultImageView.setImage(ImageFactory.getImage("no_image.png"));
        resultImageView.fitHeightProperty().bind(ControlPanel.getStage().heightProperty().subtract(ControlPanelHeader.LOGO_HEIGHT).subtract(ControlPanelFooter.HEIGHT).multiply(TOP_PANE_PART).subtract(5));
        resultImageView.fitWidthProperty().bind(ControlPanel.getStage().widthProperty().subtract(MAIN_PARAMETERS_PANE_WIDTH).divide(2));
        hBox.getChildren().addAll(srcImageView, getMainParametersPane(), resultImageView);
        srcImageView.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
        srcImageView.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
        return hBox;
    }

    private VBox getMainParametersPane() {
        VBox mainParametersVBox = new VBox();
        mainParametersVBox.setSpacing(15);
        mainParametersVBox.setPrefWidth(MAIN_PARAMETERS_PANE_WIDTH);
        mainParametersVBox.setAlignment(Pos.TOP_CENTER);
        mainParametersVBox.prefHeightProperty().bind(ControlPanel.getStage().heightProperty().subtract(ControlPanelHeader.LOGO_HEIGHT).subtract(ControlPanelFooter.HEIGHT).multiply(TOP_PANE_PART).subtract(5));
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
        TCHButton convertButton = new TCHButton(Messages.get("convert"));
        mainParametersVBox.getChildren().addAll(convertGrayFieldLabel, blurringFieldLabel, thresholdFieldLabel, morphologicalFieldLabel, convertButton);
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
        updatePanes();
        return scrollPane;
    }

    private void initListeners() {
        blurringComboBox.valueProperty().addListener((ov, t, t1) -> {
            updatePanes();
        });
        thresholdComboBox.valueProperty().addListener((ov, t, t1) -> {
            updatePanes();
        });
        morphologicalComboBox.valueProperty().addListener((ov, t, t1) -> {
            updatePanes();
        });
    }

    private void updatePanes() {
        bottomHBox.getChildren().clear();
        bottomHBox.getChildren().add(new BlurringPane(BlurringType.valueOf(blurringComboBox.getValue().toString())));
        bottomHBox.getChildren().add(new ThresholdPane(ThresholdType.valueOf(thresholdComboBox.getValue().toString())));
        bottomHBox.getChildren().addAll(new MorphologicalPane(MorphologicalType.valueOf(morphologicalComboBox.getValue().toString())));
    }
}
