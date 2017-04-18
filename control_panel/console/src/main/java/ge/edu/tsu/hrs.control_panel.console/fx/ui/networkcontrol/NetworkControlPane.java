package ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hrs.control_panel.model.network.TransferFunction;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkControlPane extends VBox {

    private final String integerListSeparator = ",";

    private final GroupedNormalizedDataService groupedNormalizedDataService = new GroupedNormalizedDataServiceImpl();

    private NeuralNetworkService neuralNetworkService;

    private final int BUTTONS_PANE_WIDTH = 120;

    private final int NORMALIZATION_TABLE_HEIGHT = 180;

    private HBox parametersHBox;

    private HBox normalizationHBox;

    private TCHTextField minBiasTextField;

    private TCHTextField maxBiasTextField;

    private TCHTextField minWeightTextField;

    private TCHTextField maxWeightTextField;

    private TCHTextField networkMetaInfoTextField;

    private TCHTextField descriptionTextField;

    private TCHComboBox networkProcessorComboBox;

    private TCHTextField layersTextField;

    private TCHTextField learningRateTextField;

    private TCHTextField charSequenceTextField;

    private TCHTextField dataPerIterationTextField;

    private TCHTextField maxIterationTextField;

    private TCHComboBox transferFunctionComboBox;

    private TCHTextField minErrorTextField;

    private TableView<GroupedNormalizedDataProperty> normalizationTable;

    public NetworkControlPane() {
        initUI();
        initMainParametersPane();
        initExtraParametersPane();
        initNormalizationPane();
        initButtonsPane();
        initNetworkGridPane();
    }

    private void initUI() {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(5);
        parametersHBox = new HBox();
        parametersHBox.setSpacing(5);
        normalizationHBox = new HBox();
        normalizationHBox.setSpacing(5);
        normalizationHBox.setPadding(new Insets(10));
        normalizationHBox.setStyle("-fx-border-color: green; -fx-border-radius: 25px; -fx-border-size: 1px;");
        this.getChildren().addAll(parametersHBox, normalizationHBox);
    }

    private void initNetworkGridPane() {

    }

    private void initNormalizationPane() {
        TableColumn<GroupedNormalizedDataProperty, Boolean> checkColumn = new TableColumn<>("");
        checkColumn.setCellValueFactory(new PropertyValueFactory<>("checked"));
        checkColumn.setCellFactory(column -> new CheckBoxTableCell());
        checkColumn.setPrefWidth(30);
        TableColumn<GroupedNormalizedDataProperty, Boolean> idColumn = new TableColumn<>(Messages.get("id"));
        idColumn.setPrefWidth(40);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<GroupedNormalizedDataProperty, Boolean> widthColumn = new TableColumn<>(Messages.get("width"));
        widthColumn.setPrefWidth(100);
        widthColumn.setCellValueFactory(new PropertyValueFactory<>("width"));
        TableColumn<GroupedNormalizedDataProperty, Boolean> heightColumn = new TableColumn<>(Messages.get("height"));
        heightColumn.setPrefWidth(100);
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        TableColumn<GroupedNormalizedDataProperty, Boolean> minValueColumn = new TableColumn<>(Messages.get("minValue"));
        minValueColumn.setPrefWidth(100);
        minValueColumn.setCellValueFactory(new PropertyValueFactory<>("minValue"));
        TableColumn<GroupedNormalizedDataProperty, Boolean> maxValueColumn = new TableColumn<>(Messages.get("maxValue"));
        maxValueColumn.setPrefWidth(100);
        maxValueColumn.setCellValueFactory(new PropertyValueFactory<>("maxValue"));
        TableColumn<GroupedNormalizedDataProperty, Boolean> countColumn = new TableColumn<>(Messages.get("count"));
        countColumn.setPrefWidth(100);
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        TableColumn<GroupedNormalizedDataProperty, Boolean> normalizationTypeColumn = new TableColumn<>(Messages.get("normalizationType"));
        normalizationTypeColumn.setPrefWidth(140);
        normalizationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("normalizationType"));
        TableColumn<GroupedNormalizedDataProperty, Boolean> nameColumn = new TableColumn<>(Messages.get("name"));
        nameColumn.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(30 + 40 + 100 * 5 + 140 + 35 + BUTTONS_PANE_WIDTH));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        normalizationTable = new TableView<>();
        normalizationTable.setStyle("-fx-font-family: sylfaen; -fx-text-alignment: center; -fx-font-size: 15px;");
        normalizationTable.setPrefHeight(NORMALIZATION_TABLE_HEIGHT);
        normalizationTable.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(BUTTONS_PANE_WIDTH));
        normalizationTable.getColumns().addAll(checkColumn, idColumn, widthColumn, heightColumn, minValueColumn, maxValueColumn, countColumn, normalizationTypeColumn, nameColumn);
        normalizationTable.setRowFactory( tv -> {
            TableRow<GroupedNormalizedDataProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    GroupedNormalizedDataProperty rowData = row.getItem();
                    rowData.setChecked(!rowData.getChecked());
                    normalizationTable.refresh();
                }
            });
            return row ;
        });
        loadGripedNormalizedData();
        normalizationHBox.getChildren().add(normalizationTable);
    }

    private void initButtonsPane() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(BUTTONS_PANE_WIDTH);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        TCHButton trainButton = new TCHButton(Messages.get("train"));
        trainButton.setOnAction(event -> {
            trainAction();
        });
        TCHButton testButton = new TCHButton(Messages.get("test"));
        vBox.getChildren().addAll(trainButton, testButton);
        normalizationHBox.getChildren().add(vBox);
    }

    private void initExtraParametersPane() {
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setStyle("-fx-border-color: green; -fx-border-radius: 25px; -fx-border-size: 1px;");
        flowPane.setPadding(new Insets(4, 4, 4, 4));
        flowPane.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().divide(2).subtract(7));
        minBiasTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel minBiasFieldLabel = new TCHFieldLabel(Messages.get("biasMinValue"), minBiasTextField);
        flowPane.getChildren().add(minBiasFieldLabel);
        maxBiasTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel maxBiasFieldLabel = new TCHFieldLabel(Messages.get("biasMaxValue"), maxBiasTextField);
        flowPane.getChildren().add(maxBiasFieldLabel);
        minWeightTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel minWeightFieldLabel = new TCHFieldLabel(Messages.get("weightMinValue"), minWeightTextField);
        flowPane.getChildren().add(minWeightFieldLabel);
        maxWeightTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel maxWeightFieldLabel = new TCHFieldLabel(Messages.get("weightMaxValue"), maxWeightTextField);
        flowPane.getChildren().add(maxWeightFieldLabel);
        networkMetaInfoTextField = new TCHTextField(TCHComponentSize.MEDIUM);
        TCHFieldLabel networkMetaInfoFieldLabel = new TCHFieldLabel(Messages.get("networkMetaInfo"), networkMetaInfoTextField);
        flowPane.getChildren().addAll(networkMetaInfoFieldLabel);
        descriptionTextField = new TCHTextField(TCHComponentSize.MEDIUM);
        TCHFieldLabel descriptionFieldLabel = new TCHFieldLabel(Messages.get("description"), descriptionTextField);
        flowPane.getChildren().add(descriptionFieldLabel);
        parametersHBox.getChildren().add(flowPane);
    }

    private void initMainParametersPane() {
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setStyle("-fx-border-color: green; -fx-border-radius: 25px; -fx-border-size: 1px;");
        flowPane.setPadding(new Insets(4, 4, 4, 4));
        flowPane.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().divide(2).subtract(7));
        networkProcessorComboBox = new TCHComboBox(Arrays.asList(NetworkProcessorType.values()));
        TCHFieldLabel networkProcessorFieldLabel = new TCHFieldLabel(Messages.get("networkProcessorType"), networkProcessorComboBox);
        flowPane.getChildren().add(networkProcessorFieldLabel);
        layersTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel layersFieldLabel = new TCHFieldLabel(Messages.get("hiddenLayers"), layersTextField);
        flowPane.getChildren().add(layersFieldLabel);
        learningRateTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel learningRateFieldLabel = new TCHFieldLabel(Messages.get("learningRate"), learningRateTextField);
        flowPane.getChildren().add(learningRateFieldLabel);
        transferFunctionComboBox = new TCHComboBox(Arrays.asList(TransferFunction.values()));
        TCHFieldLabel transferFunctionFieldLabel = new TCHFieldLabel(Messages.get("transferFunction"), transferFunctionComboBox);
        flowPane.getChildren().add(transferFunctionFieldLabel);
        charSequenceTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel charSequenceFieldLabel = new TCHFieldLabel(Messages.get("charSequence"), charSequenceTextField);
        flowPane.getChildren().add(charSequenceFieldLabel);
        maxIterationTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel maxIterationFieldLabel = new TCHFieldLabel(Messages.get("trainingMaxIteration"), maxIterationTextField);
        flowPane.getChildren().add(maxIterationFieldLabel);
        dataPerIterationTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel dataPerIterationFieldLabel = new TCHFieldLabel(Messages.get("dataPerIteration"), dataPerIterationTextField);
        flowPane.getChildren().add(dataPerIterationFieldLabel);
        minErrorTextField = new TCHTextField(TCHComponentSize.SMALL);
        TCHFieldLabel minErrorFieldLabel = new TCHFieldLabel(Messages.get("minError"), minErrorTextField);
        flowPane.getChildren().add(minErrorFieldLabel);
        parametersHBox.getChildren().add(flowPane);
    }

    private void loadGripedNormalizedData() {
        List<GroupedNormalizedData> groupedNormalizedDatum = groupedNormalizedDataService.getGroupedNormalizedDatum(null, null, null, null, null, null, null);
        List<GroupedNormalizedDataProperty> groupedNormalizedDataPropertyList = new ArrayList<>();
        for (GroupedNormalizedData groupedNormalizedData : groupedNormalizedDatum) {
            groupedNormalizedDataPropertyList.add(new GroupedNormalizedDataProperty(groupedNormalizedData));
        }
        ObservableList<GroupedNormalizedDataProperty> data = FXCollections.observableArrayList(groupedNormalizedDataPropertyList);
        normalizationTable.setItems(data);
    }

    private void trainAction() {
        try {
            NetworkInfo networkInfo = new NetworkInfo();
            networkInfo.setWeightMinValue(Float.valueOf(minWeightTextField.getText()));
            networkInfo.setWeightMaxValue(Float.valueOf(maxWeightTextField.getText()));
            networkInfo.setBiasMinValue(Float.valueOf(minBiasTextField.getText()));
            networkInfo.setBiasMaxValue(Float.valueOf(maxBiasTextField.getText()));
            networkInfo.setTransferFunction(TransferFunction.valueOf(transferFunctionComboBox.getValue().toString()));
            networkInfo.setLearningRate(Float.valueOf(learningRateTextField.getText()));
            networkInfo.setMinError(Float.valueOf(minErrorTextField.getText()));
            networkInfo.setTrainingMaxIteration(Long.valueOf(maxIterationTextField.getText()));
            networkInfo.setNumberOfTrainingDataInOneIteration(Long.valueOf(dataPerIterationTextField.getText()));
            networkInfo.setCharSequence(new CharSequence(charSequenceTextField.getText()));
            networkInfo.setHiddenLayer(getIntegerListFromString(layersTextField.getText()));
            networkInfo.setNetworkProcessorType(NetworkProcessorType.valueOf(networkProcessorComboBox.getValue().toString()));
            networkInfo.setNetworkMetaInfo(networkMetaInfoTextField.getText());
            networkInfo.setDescription(descriptionTextField.getText());
            neuralNetworkService = new NeuralNetworkServiceImpl(networkInfo.getNetworkProcessorType());
            neuralNetworkService.trainNeural(networkInfo, getGroupedNormalizedDatum(), true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private List<GroupedNormalizedData> getGroupedNormalizedDatum() {
        List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
        for (GroupedNormalizedDataProperty groupedNormalizedDataProperty : normalizationTable.getItems()) {
            if (groupedNormalizedDataProperty.getChecked()) {
                groupedNormalizedDatum.add(groupedNormalizedDataService.getGroupedNormalizedData(groupedNormalizedDataProperty.getId()));
            }
        }
        return groupedNormalizedDatum;
    }

    private String getStringFromIntegerList(List<Integer> list) {
        String result = "";
        if (list == null || list.isEmpty()) {
            return result;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            result += list.get(i) + integerListSeparator;
        }
        result += list.get(list.size() - 1);
        return result;
    }

    private List<Integer> getIntegerListFromString(String text) {
        List<Integer> result = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return result;
        }
        for (String part : text.split(integerListSeparator)) {
            result.add(Integer.parseInt(part.trim()));
        }
        return result;
    }
}
