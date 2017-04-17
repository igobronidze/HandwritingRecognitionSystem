package ge.edu.tsu.hrs.control_panel.console.fx.ui.cleanimage;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHNumberTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class BlurringPane extends VBox {

    private TCHLabel titleLabel;

    private TCHNumberTextField amountField;

    private TCHNumberTextField kSizeWidthField;

    private TCHNumberTextField kSizeHeightField;

    private TCHNumberTextField diameterField;

    private TCHNumberTextField sigmaColorField;

    private TCHNumberTextField sigmaSpaceField;

    private TCHTextField sigmaXField;

    private TCHTextField sigmaYField;

    private TCHNumberTextField borderTypeField;

    private TCHNumberTextField kSizeField;

    private FlowPane flowPane;

    private BlurringType type;

    public BlurringPane(BlurringType blurringType) {
        flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.setHgap(2);
        flowPane.setVgap(2);
        titleLabel = new TCHLabel("");
        titleLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        titleLabel.setTextFill(Color.GREEN);
        reloadPane(blurringType);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(10);
        this.setStyle("-fx-border-color: green; -fx-border-radius: 10px; -fx-border-size: 1px;");
        this.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(1 - CleanImagePane.TOP_PANE_PART));
    }

    private void reloadPane(BlurringType type) {
        this.type = type;
        flowPane.getChildren().clear();
        this.getChildren().clear();
        titleLabel.setText(Messages.get("blurringWith") + ": " + type.name());
        amountField = new TCHNumberTextField(new BigDecimal(1), TCHComponentSize.SMALL);
        TCHFieldLabel amountFieldLabel = new TCHFieldLabel(Messages.get("amount"), amountField);
        if (type != BlurringType.NO_BLURRING) {
            flowPane.getChildren().add(amountFieldLabel);
        }
        switch (type) {
            case BLUR:
                Tooltip tooltip = new Tooltip("ksize - Gaussian kernel size. ksize.width and ksize.height can differ but they" +
                        " both must be positive and odd. Or, they can be zeros and then they are computed from sigma.");
                kSizeWidthField = new TCHNumberTextField(new BigDecimal(5), TCHComponentSize.SMALL);
                kSizeHeightField = new TCHNumberTextField(new BigDecimal(5), TCHComponentSize.SMALL);
                kSizeWidthField.setTooltip(tooltip);
                kSizeHeightField.setTooltip(tooltip);
                TCHFieldLabel f1 = new TCHFieldLabel(Messages.get("kSizeWidth"), kSizeWidthField);
                TCHFieldLabel f2 = new TCHFieldLabel(Messages.get("kSizeHeight"), kSizeHeightField);
                flowPane.getChildren().addAll(f1, f2);
                break;
            case BILATERAL_FILTER:
                Tooltip diameterTooltip = new Tooltip("diameter - Diameter of each pixel neighborhood that is used during filtering. If it is non-positive, it is computed from sigmaSpace.");
                Tooltip sigmaColorTooltip = new Tooltip("sigmaColor - Filter sigma in the color space. A larger value of the parameter means that farther colors within the pixel neighborhood \n" +
                        " will be mixed together, resulting in larger areas of semi-equal color.");
                Tooltip sigmaSpaceTooltip = new Tooltip("sigmaSpace - Filter sigma in the coordinate space. A larger value of the parameter means that farther pixels will influence each \n" +
                        " other as long as their colors are close enough. When d>0 , it specifies the neighborhood size regardless of sigmaSpace . Otherwise, d is proportional to sigmaSpace.");
                diameterField = new TCHNumberTextField(new BigDecimal(9), TCHComponentSize.SMALL);
                diameterField.setTooltip(diameterTooltip);
                sigmaColorField = new TCHNumberTextField(new BigDecimal(75), TCHComponentSize.SMALL);
                sigmaColorField.setTooltip(sigmaColorTooltip);
                sigmaSpaceField = new TCHNumberTextField(new BigDecimal(75), TCHComponentSize.SMALL);
                sigmaSpaceField.setTooltip(sigmaSpaceTooltip);
                TCHFieldLabel diameterFieldLabel = new TCHFieldLabel(Messages.get("diameter"), diameterField);
                TCHFieldLabel sigmaColorFieldLabel = new TCHFieldLabel(Messages.get("sigmaColor"), sigmaColorField);
                TCHFieldLabel sigmaSpaceFieldLabel = new TCHFieldLabel(Messages.get("sigmaSpace"), sigmaSpaceField);
                flowPane.getChildren().addAll(diameterFieldLabel, sigmaColorFieldLabel, sigmaSpaceFieldLabel);
                break;
            case GAUSSIAN_BLUR:
                Tooltip kSizeTooltip = new Tooltip("ksize - Gaussian kernel size. ksize.width and ksize.height can differ but they" +
                        " both must be positive and odd. Or, they can be zeros and then they are computed from sigma.");
                Tooltip sigmaXTooltip = new Tooltip("sigmaX - Gaussian kernel standard deviation in X direction.");
                Tooltip sigmaYTooltip = new Tooltip("// sigmaY - Gaussian kernel standard deviation in Y direction; if sigmaY is zero, it is set to be equal to sigmaX,\n" +
                        "  if both sigmas are zeros, they are computed from ksize.width and ksize.height , respectively; to fully control\n" +
                        "  the result regardless of possible future modifications of all this semantics, it is recommended to specify all of ksize, sigmaX, and sigmaY.");
                Tooltip borderTypeTooltip = new Tooltip("borderType - pixel extrapolation method.");
                kSizeWidthField = new TCHNumberTextField(new BigDecimal(5), TCHComponentSize.SMALL);
                kSizeHeightField = new TCHNumberTextField(new BigDecimal(5), TCHComponentSize.SMALL);
                sigmaXField = new TCHTextField("0.0", TCHComponentSize.SMALL);
                sigmaYField = new TCHTextField("0.0", TCHComponentSize.SMALL);
                borderTypeField = new TCHNumberTextField(new BigDecimal(4), TCHComponentSize.SMALL);
                kSizeWidthField.setTooltip(kSizeTooltip);
                kSizeHeightField.setTooltip(kSizeTooltip);
                sigmaXField.setTooltip(sigmaXTooltip);
                sigmaYField.setTooltip(sigmaYTooltip);
                borderTypeField.setTooltip(borderTypeTooltip);
                TCHFieldLabel kSizeWidthFieldLabel = new TCHFieldLabel(Messages.get("kSizeWidth"), kSizeWidthField);
                TCHFieldLabel kSizeHeightFieldLabel = new TCHFieldLabel(Messages.get("kSizeHeight"), kSizeHeightField);
                TCHFieldLabel sigmaXFieldLabel = new TCHFieldLabel(Messages.get("sigmaX"), sigmaXField);
                TCHFieldLabel sigmaYFieldLabel = new TCHFieldLabel(Messages.get("sigmaY"), sigmaYField);
                TCHFieldLabel borderTypeFieldLabel = new TCHFieldLabel(Messages.get("borderType"), borderTypeField);
                flowPane.getChildren().addAll(kSizeWidthFieldLabel, kSizeHeightFieldLabel, sigmaXFieldLabel, sigmaYFieldLabel, borderTypeFieldLabel);
                break;
            case MEDIAN_BLUR:
                Tooltip kTooltip = new Tooltip("ksize - aperture linear size; it must be odd and greater than 1, for example: 3, 5, 7 ...");
                kSizeField = new TCHNumberTextField(new BigDecimal(5), TCHComponentSize.SMALL);
                kSizeField.setTooltip(kTooltip);
                TCHFieldLabel kSizeFieldLabel = new TCHFieldLabel(Messages.get("kSize"), kSizeField);
                flowPane.getChildren().add(kSizeFieldLabel);
                break;
            case NO_BLURRING:
                default:
                    break;
        }
        this.getChildren().addAll(titleLabel, flowPane);
    }

    public BlurringParameters getBlurringParameters() {
        BlurringParameters parameters = null;
        try {
            parameters = new BlurringParameters();
            parameters.setType(type);
            if (type != BlurringType.NO_BLURRING) {
                parameters.setAmount(amountField.getNumber().intValue());
            }
            switch (type) {
                case BLUR:
                    parameters.setkSizeWidth(kSizeWidthField.getNumber().intValue());
                    parameters.setkSizeHeight(kSizeHeightField.getNumber().intValue());
                    break;
                case BILATERAL_FILTER:
                    parameters.setDiameter(diameterField.getNumber().intValue());
                    parameters.setSigmaSpace(sigmaSpaceField.getNumber().intValue());
                    parameters.setSigmaColor(sigmaColorField.getNumber().intValue());
                    break;
                case GAUSSIAN_BLUR:
                    parameters.setSigmaX(Double.valueOf(sigmaXField.getText()));
                    parameters.setSigmaY(Double.valueOf(sigmaYField.getText()));
                    parameters.setBorderType(borderTypeField.getNumber().intValue());
                    parameters.setkSizeWidth(kSizeWidthField.getNumber().intValue());
                    parameters.setkSizeHeight(kSizeHeightField.getNumber().intValue());
                    break;
                case MEDIAN_BLUR:
                    parameters.setkSize(kSizeField.getNumber().intValue());
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return parameters;
    }
}
