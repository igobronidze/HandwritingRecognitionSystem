package ge.edu.tsu.hrs.control_panel.console.fx.ui.cleanimage;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHNumberTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class MorphologicalPane extends VBox {

    private TCHLabel titleLabel;

    private TCHNumberTextField erosionAmountField;

    private TCHNumberTextField dilationAmountField;

    private TCHNumberTextField erosionElemField;

    private TCHNumberTextField erosionSizeField;

    private TCHNumberTextField dilationElemField;

    private TCHNumberTextField dilationSizeField;

    private FlowPane flowPane;

    private MorphologicalType morphologicalType;

    public MorphologicalPane(MorphologicalType morphologicalType) {
        flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.setHgap(2);
        flowPane.setVgap(2);
        titleLabel = new TCHLabel("");
        titleLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        titleLabel.setTextFill(Color.GREEN);
        reloadPane(morphologicalType);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(10);
        this.setStyle("-fx-border-color: green; -fx-border-radius: 10px; -fx-border-size: 1px;");
        this.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(1 - CleanImagePane.TOP_PANE_PART));
    }

    private void reloadPane(MorphologicalType morphologicalType) {
        this.morphologicalType = morphologicalType;
        flowPane.getChildren().clear();
        this.getChildren().clear();
        titleLabel.setText(Messages.get("morphologicalWith") + ": " + morphologicalType.name());
        switch (morphologicalType) {
            case DILATION_EROSION:
            case EROSION_DILATION:
                Tooltip tooltip = new Tooltip("0 - 1 - 2");
                erosionAmountField = new TCHNumberTextField(new BigDecimal("1"), TCHComponentSize.SMALL);
                erosionElemField = new TCHNumberTextField(new BigDecimal("0"), TCHComponentSize.SMALL);
                erosionElemField.setTooltip(tooltip);
                erosionSizeField = new TCHNumberTextField(new BigDecimal("1"), TCHComponentSize.SMALL);
                dilationAmountField = new TCHNumberTextField(new BigDecimal("1"), TCHComponentSize.SMALL);
                dilationElemField = new TCHNumberTextField(new BigDecimal("0"), TCHComponentSize.SMALL);
                dilationElemField.setTooltip(tooltip);
                dilationSizeField = new TCHNumberTextField(new BigDecimal("1"), TCHComponentSize.SMALL);
                TCHFieldLabel erosionAmountFieldLabel = new TCHFieldLabel(Messages.get("erosionAmount"), erosionAmountField);
                TCHFieldLabel erosionElemFieldLabel = new TCHFieldLabel(Messages.get("erosionElem"), erosionElemField);
                TCHFieldLabel erosionSizeFieldLabel = new TCHFieldLabel(Messages.get("erosionSize"), erosionSizeField);
                TCHFieldLabel dilationAmountFieldLabel = new TCHFieldLabel(Messages.get("dilationAmount"), dilationAmountField);
                TCHFieldLabel dilationElemFieldLabel = new TCHFieldLabel(Messages.get("dilationElem"), dilationElemField);
                TCHFieldLabel dilationSizeFieldLabel = new TCHFieldLabel(Messages.get("dilationSize"), dilationSizeField);
                flowPane.getChildren().addAll(erosionAmountFieldLabel, erosionElemFieldLabel, erosionSizeFieldLabel, dilationAmountFieldLabel, dilationElemFieldLabel, dilationSizeFieldLabel);
                break;
            case NO_OPERATION:
                default:
                    break;
        }
        this.getChildren().addAll(titleLabel, flowPane);
    }

    public MorphologicalParameters getMorphologicalParameters() {
        MorphologicalParameters morphologicalParameters = null;
        try {
            morphologicalParameters = new MorphologicalParameters();
            morphologicalParameters.setMorphologicalType(morphologicalType);
            switch (morphologicalType) {
                case DILATION_EROSION:
                case EROSION_DILATION:
                    morphologicalParameters.setErosionAmount(erosionAmountField.getNumber().intValue());
                    morphologicalParameters.setErosionElem(erosionElemField.getNumber().intValue());
                    morphologicalParameters.setErosionSize(erosionSizeField.getNumber().intValue());
                    morphologicalParameters.setDilationAmount(dilationAmountField.getNumber().intValue());
                    morphologicalParameters.setDilationElem(dilationElemField.getNumber().intValue());
                    morphologicalParameters.setDilationSize(dilationSizeField.getNumber().intValue());
                    break;
                case NO_OPERATION:
                    default:
                        break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return morphologicalParameters;
    }
}
