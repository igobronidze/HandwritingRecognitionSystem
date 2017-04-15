package ge.edu.tsu.hrs.control_panel.console.fx.ui.cleanimage;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHNumberTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class ThresholdPane extends VBox {

    private TCHLabel titleLabel;

    private TCHNumberTextField maxValueField;

    private TCHNumberTextField adaptiveMethodField;

    private TCHNumberTextField thresholdTypeField;

    private TCHNumberTextField blockSizeField;

    private TCHNumberTextField cField;

    private TCHNumberTextField threshField;

    private TCHNumberTextField typeField;

    private FlowPane flowPane;

    private ThresholdType thresholdType;

    public ThresholdPane(ThresholdType thresholdType) {
        flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.setHgap(2);
        flowPane.setVgap(2);
        titleLabel = new TCHLabel("");
        titleLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        titleLabel.setTextFill(Color.GREEN);
        reloadPane(thresholdType);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(10);
        this.setStyle("-fx-border-color: green; -fx-background-radius: 2px; -fx-background-size: 1px;");
        this.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(1 - CleanImagePane.TOP_PANE_PART));
    }

    private void reloadPane(ThresholdType thresholdType) {
        this.thresholdType = thresholdType;
        flowPane.getChildren().clear();
        this.getChildren().clear();
        titleLabel.setText(Messages.get("thresholdWith") + ": " + thresholdType.name());
        Tooltip maxValueTooltip = new Tooltip("maxval – maximum value to use with the THRESH_BINARY and THRESH_BINARY_INV thresholding types.");
        maxValueField = new TCHNumberTextField(new BigDecimal(255), TCHComponentSize.SMALL);
        maxValueField.setTooltip(maxValueTooltip);
        TCHFieldLabel maxValueFieldLabel = new TCHFieldLabel(Messages.get("maxValue"), maxValueField);
        if (thresholdType != ThresholdType.NO_THRESHOLD) {
            flowPane.getChildren().add(maxValueFieldLabel);
        }
        switch (thresholdType) {
            case ADAPTIVE_THRESHOLD:
                Tooltip adaptiveMethodTooltip = new Tooltip("adaptiveMethod – Adaptive thresholding algorithm to use, ADAPTIVE_THRESH_MEAN_C=0 or ADAPTIVE_THRESH_GAUSSIAN_C=1.");
                Tooltip thresholdTypeTooltip = new Tooltip("thresholdType – Thresholding type that must be either THRESH_BINARY=0 or THRESH_BINARY_INV=1.");
                Tooltip blockSizeTooltip = new Tooltip("blockSize – Size of a pixel neighborhood that is used to calculate a threshold value for the pixel: 3, 5, 7, and so on.");
                Tooltip cTooltip = new Tooltip("C – Constant subtracted from the mean or weighted mean (see the details below). Normally, it is positive but may be zero or negative as well.");
                adaptiveMethodField = new TCHNumberTextField(new BigDecimal(1), TCHComponentSize.SMALL);
                adaptiveMethodField.setTooltip(adaptiveMethodTooltip);
                thresholdTypeField = new TCHNumberTextField(new BigDecimal(0), TCHComponentSize.SMALL);
                thresholdTypeField.setTooltip(thresholdTypeTooltip);
                blockSizeField = new TCHNumberTextField(new BigDecimal(15), TCHComponentSize.SMALL);
                blockSizeField.setTooltip(blockSizeTooltip);
                cField = new TCHNumberTextField(new BigDecimal(2), TCHComponentSize.SMALL);
                cField.setTooltip(cTooltip);
                TCHFieldLabel adaptiveMethodFieldLabel = new TCHFieldLabel(Messages.get("adaptiveMethod"), adaptiveMethodField);
                TCHFieldLabel thresholdTypeFieldLabel = new TCHFieldLabel(Messages.get("thresholdType"), thresholdTypeField);
                TCHFieldLabel blockSizeFieldLabel = new TCHFieldLabel(Messages.get("blockSize"), blockSizeField);
                TCHFieldLabel cFieldLabel = new TCHFieldLabel(Messages.get("c"), cField);
                flowPane.getChildren().addAll(adaptiveMethodFieldLabel, thresholdTypeFieldLabel, blockSizeFieldLabel, cFieldLabel);
                break;
            case OTSU_BINARIZATION:
                Tooltip threshTooltip = new Tooltip("thresh – threshold value, მგონი უნდა იყოს 0 რადგან მაინც არ იყენებს, თვითონ თვლის კარგ მნიშვნელობას და იყენებს მას");
                Tooltip typeTooltip = new Tooltip("type – thresholding type opencv_imgproc.THRESH_BINARY=0|THRESH_BINARY_INV=1 + opencv_imgproc.THRESH_OTSU=8");
                threshField = new TCHNumberTextField(new BigDecimal(0), TCHComponentSize.SMALL);
                threshField.setTooltip(threshTooltip);
                typeField = new TCHNumberTextField(new BigDecimal(8), TCHComponentSize.SMALL);
                typeField.setTooltip(typeTooltip);
                TCHFieldLabel threshFieldLabel = new TCHFieldLabel(Messages.get("thresh"), threshField);
                TCHFieldLabel typeFieldLabel = new TCHFieldLabel(Messages.get("type"), typeField);
                flowPane.getChildren().addAll(threshFieldLabel, typeFieldLabel);
                break;
            case SIMPLE_THRESHOLD:
                Tooltip tooltip1 = new Tooltip("thresh – threshold value. თუ არ ვცდები მასზე ნაკლები იქცევა თეთრად, მეტი შავად(თეთრი და შავი მაგალითისთვის)");
                Tooltip tooltip2 = new Tooltip("type – thresholding type (see the details below). [THRESH_BINARY=0, THRESH_BINARY_INV=1, THRESH_TRUNC=2, THRESH_TOZERO=3, THRESH_TOZERO_INV=4]");
                threshField = new TCHNumberTextField(new BigDecimal(127), TCHComponentSize.SMALL);
                threshField.setTooltip(tooltip1);
                typeField = new TCHNumberTextField(new BigDecimal(0), TCHComponentSize.SMALL);
                typeField.setTooltip(tooltip2);
                TCHFieldLabel fieldLabel1 = new TCHFieldLabel(Messages.get("thresh"), threshField);
                TCHFieldLabel fieldLabel2 = new TCHFieldLabel(Messages.get("type"), typeField);
                flowPane.getChildren().addAll(fieldLabel1, fieldLabel2);
                break;
            case NO_THRESHOLD:
                default:
                    break;
        }
        this.getChildren().addAll(titleLabel, flowPane);
    }

    public ThresholdParameters getThresholdParameters() {
        ThresholdParameters parameters = null;
        try {
            parameters = new ThresholdParameters();
            parameters.setThresholdMethodType(thresholdType);
            if (thresholdType != ThresholdType.NO_THRESHOLD) {
                parameters.setMaxValue(maxValueField.getNumber().intValue());
            }
            switch (thresholdType) {
                case ADAPTIVE_THRESHOLD:
                    parameters.setAdaptiveMethod(adaptiveMethodField.getNumber().intValue());
                    parameters.setThresholdType(thresholdTypeField.getNumber().intValue());
                    parameters.setBlockSize(blockSizeField.getNumber().intValue());
                    parameters.setC(cField.getNumber().intValue());
                    break;
                case OTSU_BINARIZATION:
                case SIMPLE_THRESHOLD:
                    parameters.setThresh(threshField.getNumber().intValue());
                    parameters.setType(typeField.getNumber().intValue());
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return parameters;
    }
}
