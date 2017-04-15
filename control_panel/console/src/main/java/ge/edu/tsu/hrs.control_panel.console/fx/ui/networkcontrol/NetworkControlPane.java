package ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hrs.control_panel.model.network.TransferFunction;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class NetworkControlPane extends VBox {

    public NetworkControlPane() {
        initMainParametersPane();
        initExtraParametersPane();
        initNormalizationPane();
        initNetworkGridPane();
    }

    private void initNetworkGridPane() {

    }

    private void initNormalizationPane() {

    }

    private void initExtraParametersPane() {
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: black;");
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.TOP_CENTER);
        TCHTextField minBiasTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel minBiasFieldLabel = new TCHFieldLabel("biasMinValue", minBiasTextField);
        flowPane.getChildren().add(minBiasFieldLabel);
        TCHTextField maxBiasTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel maxBiasFieldLabel = new TCHFieldLabel("biasMaxValue", maxBiasTextField);
        flowPane.getChildren().add(maxBiasFieldLabel);
        TCHTextField minWeightTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel minWeightFieldLabel = new TCHFieldLabel("weightMinValue", minWeightTextField);
        flowPane.getChildren().add(minWeightFieldLabel);
        TCHTextField maxWeightTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel maxWeightFieldLabel = new TCHFieldLabel("weightMaxValue", maxWeightTextField);
        flowPane.getChildren().add(maxWeightFieldLabel);
        TCHTextField networkMetaInfoTextField = new TCHTextField(TCHComponentSize.LARGE);
        TCHFieldLabel networkMetaInfoFieldLabel = new TCHFieldLabel("networkMetaInfo", networkMetaInfoTextField);
        flowPane.getChildren().addAll(networkMetaInfoFieldLabel);
        TCHTextField descriptionTextField = new TCHTextField(TCHComponentSize.LARGE);
        TCHFieldLabel descriptionFieldLabel = new TCHFieldLabel("description", descriptionTextField);
        flowPane.getChildren().add(descriptionFieldLabel);
        this.getChildren().add(flowPane);
    }

    private void initMainParametersPane() {
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: black;");
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.TOP_CENTER);
        TCHComboBox networkProcessorComboBox = new TCHComboBox(Arrays.asList(NetworkProcessorType.values()));
        TCHFieldLabel networkProcessorFieldLabel = new TCHFieldLabel(Messages.get("networkProcessorType"), networkProcessorComboBox);
        flowPane.getChildren().add(networkProcessorFieldLabel);
        TCHTextField layersTextField = new TCHTextField(TCHComponentSize.MEDIUM);
        TCHFieldLabel layersFieldLabel = new TCHFieldLabel(Messages.get("hiddenLayers"), layersTextField);
        flowPane.getChildren().add(layersFieldLabel);
        TCHTextField learningRateTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel learningRateFieldLabel = new TCHFieldLabel(Messages.get("learningRate"), learningRateTextField);
        flowPane.getChildren().add(learningRateFieldLabel);
        TCHComboBox transferFunctionComboBox = new TCHComboBox(Arrays.asList(TransferFunction.values()));
        TCHFieldLabel transferFunctionFieldLabel = new TCHFieldLabel(Messages.get("transferFunction"), transferFunctionComboBox);
        flowPane.getChildren().add(transferFunctionFieldLabel);
        TCHTextField charSequenceTextField = new TCHTextField(TCHComponentSize.MEDIUM);
        TCHFieldLabel charSequenceFieldLabel = new TCHFieldLabel(Messages.get("charSequence"), charSequenceTextField);
        flowPane.getChildren().add(charSequenceFieldLabel);
        TCHTextField maxIterationTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel maxIterationFieldLabel = new TCHFieldLabel(Messages.get("trainingMaxIteration"), maxIterationTextField);
        flowPane.getChildren().add(maxIterationFieldLabel);
        TCHTextField dataPerIterationTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel dataPerIterationFieldLabel = new TCHFieldLabel(Messages.get("dataPerIteration"), dataPerIterationTextField);
        flowPane.getChildren().add(dataPerIterationFieldLabel);
        TCHTextField minErrorTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel minErrorFieldLabel = new TCHFieldLabel(Messages.get("minError"), minErrorTextField);
        flowPane.getChildren().add(minErrorFieldLabel);
        this.getChildren().add(flowPane);
    }


}
