package ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHCheckBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.ImageFactory;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkTrainingStatus;
import ge.edu.tsu.hrs.control_panel.model.network.TestingInfo;
import ge.edu.tsu.hrs.control_panel.model.network.TransferFunction;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.service.network.NetworkInfoService;
import ge.edu.tsu.hrs.control_panel.service.network.NetworkInfoServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.network.NeuralNetworkUtilService;
import ge.edu.tsu.hrs.control_panel.service.network.NeuralNetworkUtilServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.trainingdatainfo.TrainingDataInfoService;
import ge.edu.tsu.hrs.control_panel.service.trainingdatainfo.TrainingDataInfoServiceImpl;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkControlPane extends VBox {

    private final String integerListSeparator = ",";

    private final GroupedNormalizedDataService groupedNormalizedDataService = new GroupedNormalizedDataServiceImpl();

    private final NetworkInfoService networkInfoService = new NetworkInfoServiceImpl();

    private final TrainingDataInfoService trainingDataInfoService = new TrainingDataInfoServiceImpl();

    private NeuralNetworkService neuralNetworkService;

    private NeuralNetworkUtilService neuralNetworkUtilService = new NeuralNetworkUtilServiceImpl();

    private final int BUTTONS_PANE_WIDTH = 120;

    private final int NORMALIZATION_TABLE_HEIGHT = 280;

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

    private TableView<NetworkInfoProperty> networkInfoTable;

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
        networkInfoTable = new TableView<>();
        this.getChildren().addAll(networkInfoTable, parametersHBox, normalizationHBox);
    }

    private void initNetworkGridPane() {
        DoubleBinding doubleBinding = ControlPanel.getCenterWidthBinding().subtract(125).divide(6);
        TableColumn<NetworkInfoProperty, Integer> idColumn = new TableColumn<>(Messages.get("id"));
        idColumn.prefWidthProperty().bind(doubleBinding);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<NetworkInfoProperty, Integer> trainingDurationColumn = new TableColumn<>(Messages.get("trainingDuration"));
        trainingDurationColumn.prefWidthProperty().bind(doubleBinding);
        trainingDurationColumn.setCellValueFactory(new PropertyValueFactory<>("trainingDuration"));
        trainingDurationColumn.setStyle("-fx-text-alignment: center;");
        TableColumn<NetworkInfoProperty, Integer> bestSquaredErrorColumn = new TableColumn<>(Messages.get("bestSquaredError"));
        bestSquaredErrorColumn.prefWidthProperty().bind(doubleBinding);
        bestSquaredErrorColumn.setCellValueFactory(new PropertyValueFactory<>("bestSquaredError"));
        TableColumn<NetworkInfoProperty, Integer> bestPercentageOfIncorrectColumn = new TableColumn<>(Messages.get("bestPercentageOfIncorrect"));
        bestPercentageOfIncorrectColumn.prefWidthProperty().bind(doubleBinding);
        bestPercentageOfIncorrectColumn.setCellValueFactory(new PropertyValueFactory<>("bestPercentageOfIncorrect"));
        TableColumn<NetworkInfoProperty, Integer> bestDiffBetweenAnsAndBestColumn = new TableColumn<>(Messages.get("bestDiffBetweenAnsAndBest"));
        bestDiffBetweenAnsAndBestColumn.prefWidthProperty().bind(doubleBinding);
        bestDiffBetweenAnsAndBestColumn.setCellValueFactory(new PropertyValueFactory<>("bestDiffBetweenAnsAndBest"));
        TableColumn<NetworkInfoProperty, Integer> bestNormalizedGeneralErrorColumn = new TableColumn<>(Messages.get("bestNormalizedGeneralError"));
        bestNormalizedGeneralErrorColumn.prefWidthProperty().bind(doubleBinding);
        bestNormalizedGeneralErrorColumn.setCellValueFactory(new PropertyValueFactory<>("bestNormalizedGeneralError"));
        TableColumn deleteColumn = new TableColumn<>("");
        deleteColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NetworkInfoProperty, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<NetworkInfoProperty, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });
        deleteColumn.setCellFactory(new Callback<TableColumn<NetworkInfoProperty, Boolean>, TableCell<NetworkInfoProperty, Boolean>>() {
                    @Override
                    public TableCell<NetworkInfoProperty, Boolean> call(TableColumn<NetworkInfoProperty, Boolean> p) {
                        return new DeleteButtonCell();
                    }
                });
        deleteColumn.setPrefWidth(48);
        TableColumn testResultColumn = new TableColumn<>("");
        testResultColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NetworkInfoProperty, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<NetworkInfoProperty, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        testResultColumn.setCellFactory(new Callback<TableColumn<NetworkInfoProperty, Boolean>, TableCell<NetworkInfoProperty, Boolean>>() {
                    @Override
                    public TableCell<NetworkInfoProperty, Boolean> call(TableColumn<NetworkInfoProperty, Boolean> p) {
                        return new TestResultButtonCell();
                    }
                });
        testResultColumn.setPrefWidth(48);
        networkInfoTable.setStyle("-fx-font-family: sylfaen; -fx-text-alignment: center; -fx-font-size: 16px; -fx-font-weight: bold;");
        networkInfoTable.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding());
        networkInfoTable.getColumns().addAll(idColumn, trainingDurationColumn, bestSquaredErrorColumn, bestPercentageOfIncorrectColumn, bestDiffBetweenAnsAndBestColumn, bestNormalizedGeneralErrorColumn, deleteColumn, testResultColumn);
        loadNetworkInfo();
        networkInfoTable.setRowFactory( tv -> {
            TableRow<NetworkInfoProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    NetworkInfo networkInfo = row.getItem().getNetworkInfo();
                    networkProcessorComboBox.setValue(networkInfo.getNetworkProcessorType().name());
                    layersTextField.setText(getStringFromIntegerList(networkInfo.getHiddenLayer()));
                    learningRateTextField.setText("" + networkInfo.getLearningRate());
                    transferFunctionComboBox.setValue(networkInfo.getTransferFunction().name());
                    charSequenceTextField.setText("" + networkInfo.getCharSequence().getCharactersRegex());
                    maxIterationTextField.setText("" + networkInfo.getTrainingMaxIteration());
                    dataPerIterationTextField.setText("" + networkInfo.getNumberOfTrainingDataInOneIteration());
                    minErrorTextField.setText("" + networkInfo.getMinError());
                    minBiasTextField.setText("" + networkInfo.getBiasMinValue());
                    maxBiasTextField.setText("" + networkInfo.getBiasMaxValue());
                    minWeightTextField.setText("" + networkInfo.getWeightMinValue());
                    maxWeightTextField.setText("" + networkInfo.getWeightMaxValue());
                    networkMetaInfoTextField.setText(networkInfo.getNetworkMetaInfo());
                    descriptionTextField.setText(networkInfo.getDescription());
                }
            });
            if (row.getItem() != null) {
                if (row.getItem().getNetworkInfo().getTrainingStatus() == NetworkTrainingStatus.TRAINING) {
                    row.setStyle("-fx-background-color:green");
                } else if (row.getItem().getNetworkInfo().getTrainingStatus() == NetworkTrainingStatus.FAILED) {
                    row.setStyle("-fx-background-color:red");
                } else {
                    row.setStyle("-fx-background-color:yellow");
                }
            }
            return row ;
        });
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
        normalizationTable.setStyle("-fx-font-family: sylfaen; -fx-text-alignment: center; -fx-font-size: 14px;");
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
        layersTextField = new TCHTextField(TCHComponentSize.MEDIUM);
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

    private void loadNetworkInfo() {
        List<NetworkInfo> networkInfoList = networkInfoService.getNetworkInfoList(null);
        List<NetworkInfoProperty> networkInfoProperties = new ArrayList<>();
        for (NetworkInfo networkInfo : networkInfoList) {
            networkInfoProperties.add(new NetworkInfoProperty(networkInfo));
        }
        ObservableList<NetworkInfoProperty> data = FXCollections.observableArrayList(networkInfoProperties);
        networkInfoTable.setItems(data);
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

    private class DeleteButtonCell extends TableCell<NetworkInfoProperty, Boolean> {
        final ImageView imageView;
        final Button cellButton;
        DeleteButtonCell(){
            imageView = new ImageView(ImageFactory.getImage("delete_blue.png"));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            cellButton = new Button("", imageView);
            cellButton.setPrefHeight(25);
            cellButton.setPrefWidth(25);
            cellButton.setOnAction(t -> {
                NetworkInfoProperty networkInfoProperty = DeleteButtonCell.this.getTableView().getItems().get(DeleteButtonCell.this.getIndex());
                TCHLabel titleLabel = new TCHLabel(Messages.get("deleteNetworksWithId") + ":" + networkInfoProperty.getId());
                titleLabel.setPadding(new Insets(0, 0, 10, 55));
                TCHCheckBox deleteNetworkInfoCheckBox = new TCHCheckBox(Messages.get("deleteNetworkInfo"));
                TCHCheckBox deleteTrainingInfoCheckBox = new TCHCheckBox(Messages.get("deleteTrainingInfo"));
                TCHCheckBox deleteFromDatabaseCheckBox = new TCHCheckBox(Messages.get("deleteNetworkFromDatabase"));
                TCHCheckBox deleteFromFileSystemCheckBox = new TCHCheckBox(Messages.get("deleteNetworkFromFileSystem"));
                TCHCheckBox deleteChildNetworkCheckBox = new TCHCheckBox(Messages.get("deleteChildNetworks"));
                VBox vBox = new VBox(10);
                vBox.setPadding(new Insets(20, 30, 20, 35));
                vBox.getChildren().addAll(titleLabel, deleteNetworkInfoCheckBox, deleteTrainingInfoCheckBox, deleteFromDatabaseCheckBox, deleteFromFileSystemCheckBox, deleteChildNetworkCheckBox);
                HBox hBox = new HBox(15);
                TCHButton closeButton = new TCHButton(Messages.get("close"));
                TCHButton deleteButton = new TCHButton(Messages.get("delete"));
                hBox.getChildren().addAll(closeButton, deleteButton);
                hBox.setPadding(new Insets(15, 0, 0, 0));
                hBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(hBox);
                Stage stage = new Stage();
                stage.setScene(new Scene(vBox, 320, 270));
                stage.setResizable(false);
                stage.setTitle(Messages.get("delete"));
                closeButton.setOnAction(event -> {
                    stage.close();
                });
                deleteButton.setOnAction(event -> {
                    int id = networkInfoProperty.getId();
                    if (deleteNetworkInfoCheckBox.isSelected()) {
                        networkInfoService.deleteNetworkInfo(id);
                    }
                    if (deleteTrainingInfoCheckBox.isSelected()) {
                        trainingDataInfoService.deleteTrainingDataInfo(id);
                    }
                    if (deleteFromDatabaseCheckBox.isSelected()) {
                        neuralNetworkUtilService.deleteNeuralNetwork(id);
                    }
                    if (deleteFromFileSystemCheckBox.isSelected()) {
                        neuralNetworkUtilService.deleteNetworkFromFile(id);
                    }
                    if (deleteChildNetworkCheckBox.isSelected()) {
                        neuralNetworkUtilService.deleteChildNetworks(id);
                    }
                    stage.close();
                });
                stage.showAndWait();
			});
        }
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

    private class TestResultButtonCell extends TableCell<NetworkInfoProperty, Boolean> {
        final ImageView imageView;
        final Button cellButton;
        TestResultButtonCell(){
            imageView = new ImageView(ImageFactory.getImage("test_result.png"));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            cellButton = new Button("", imageView);
            cellButton.setPrefHeight(25);
            cellButton.setPrefWidth(25);
            cellButton.setOnAction(t -> {
                TableView<TestingInfoProperty> testingInfoTable = new TableView<>();
                TableColumn<TestingInfoProperty, Integer> idColumn = new TableColumn<>(Messages.get("id"));
                idColumn.setPrefWidth(40);
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                TableColumn<TestingInfoProperty, Integer> networkIdColumn = new TableColumn<>(Messages.get("networkId"));
                networkIdColumn.setPrefWidth(60);
                networkIdColumn.setCellValueFactory(new PropertyValueFactory<>("networkId"));
                TableColumn<TestingInfoProperty, Integer> networkExtraIdColumn = new TableColumn<>(Messages.get("networkExtraId"));
                networkExtraIdColumn.setPrefWidth(60);
                networkExtraIdColumn.setCellValueFactory(new PropertyValueFactory<>("networkExtraId"));
                TableColumn<TestingInfoProperty, Integer> groupedNormalizedDatumColumn = new TableColumn<>(Messages.get("groupedNormalizedDatum"));
                groupedNormalizedDatumColumn.setPrefWidth(170);
                groupedNormalizedDatumColumn.setCellValueFactory(new PropertyValueFactory<>("groupedNormalizedDatum"));
                TableColumn<TestingInfoProperty, Integer> numberOfTestColumn = new TableColumn<>(Messages.get("numberOfTest"));
                numberOfTestColumn.setPrefWidth(110);
                numberOfTestColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTest"));
                TableColumn<TestingInfoProperty, Integer> squaredErrorColumn = new TableColumn<>(Messages.get("squaredError"));
                squaredErrorColumn.setPrefWidth(110);
                squaredErrorColumn.setCellValueFactory(new PropertyValueFactory<>("squaredError"));
                TableColumn<TestingInfoProperty, Integer> percentageOfIncorrectColumn = new TableColumn<>(Messages.get("percentageOfIncorrect"));
                percentageOfIncorrectColumn.setPrefWidth(110);
                percentageOfIncorrectColumn.setCellValueFactory(new PropertyValueFactory<>("percentageOfIncorrect"));
                TableColumn<TestingInfoProperty, Integer> diffBetweenAnsAndBestColumn = new TableColumn<>(Messages.get("diffBetweenAnsAndBest"));
                diffBetweenAnsAndBestColumn.setPrefWidth(110);
                diffBetweenAnsAndBestColumn.setCellValueFactory(new PropertyValueFactory<>("diffBetweenAnsAndBest"));
                TableColumn<TestingInfoProperty, Integer> normalizedGeneralErrorColumn = new TableColumn<>(Messages.get("normalizedGeneralError"));
                normalizedGeneralErrorColumn.setPrefWidth(110);
                normalizedGeneralErrorColumn.setCellValueFactory(new PropertyValueFactory<>("normalizedGeneralError"));
                TableColumn<TestingInfoProperty, Integer> durationColumn = new TableColumn<>(Messages.get("duration"));
                durationColumn.setPrefWidth(110);
                durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
                testingInfoTable.getColumns().addAll(idColumn, networkIdColumn, networkExtraIdColumn, groupedNormalizedDatumColumn, numberOfTestColumn, squaredErrorColumn, percentageOfIncorrectColumn,
                        diffBetweenAnsAndBestColumn, normalizedGeneralErrorColumn, durationColumn);
                testingInfoTable.setPadding(new Insets(15, 15, 15, 15));
                NetworkInfoProperty networkInfoProperty = TestResultButtonCell.this.getTableView().getItems().get(TestResultButtonCell.this.getIndex());
                List<TestingInfo> testingInfoList = networkInfoProperty.getNetworkInfo().getTestingInfoList();
                List<TestingInfoProperty> testingInfoPropertyList = new ArrayList<>();
                for (TestingInfo testingInfo : testingInfoList) {
                    testingInfoPropertyList.add(new TestingInfoProperty(testingInfo));
                }
                ObservableList<TestingInfoProperty> data = FXCollections.observableArrayList(testingInfoPropertyList);
                testingInfoTable.setItems(data);
                Stage stage = new Stage();
                stage.setTitle(Messages.get("testingInfo"));
                stage.setScene(new Scene(testingInfoTable, 1010, 400));
                stage.setResizable(false);
                stage.showAndWait();
            });
        }
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }
}
