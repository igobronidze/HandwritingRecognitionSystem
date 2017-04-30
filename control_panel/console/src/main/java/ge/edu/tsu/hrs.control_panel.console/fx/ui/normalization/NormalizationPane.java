package ge.edu.tsu.hrs.control_panel.console.fx.ui.normalization;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHNumberTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathService;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.NormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.NormalizedDataServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalizationPane extends VBox {

	private final double TABLE_HEIGHT_PART = 0.8;

	private final GroupedNormalizedDataService groupedNormalizedDataService = new GroupedNormalizedDataServiceImpl();

	private final HRSPathService hrsPathService = new HRSPathServiceImpl();

	private final NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();

	private TableView<GroupedNormalizedDataProperty> normalizationTable;

	private TCHNumberTextField widthField;

	private TCHNumberTextField heightField;

	private TCHTextField minValueField;

	private TCHTextField maxValueField;

	private TCHComboBox typeComboBox;

	private TCHTextField nameField;

	public NormalizationPane() {
		initUI();
	}

	private void initUI() {
		this.setSpacing(10);
		this.setPadding(new Insets(10));
		initTopPane();
		initBottomPane();
	}

	private void initTopPane() {
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
		TableColumn<GroupedNormalizedDataProperty, Boolean> durationColumn = new TableColumn<>(Messages.get("duration"));
		durationColumn.setPrefWidth(100);
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		TableColumn<GroupedNormalizedDataProperty, Boolean> normalizationTypeColumn = new TableColumn<>(Messages.get("normalizationType"));
		normalizationTypeColumn.setPrefWidth(140);
		normalizationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("normalizationType"));
		TableColumn<GroupedNormalizedDataProperty, Boolean> nameColumn = new TableColumn<>(Messages.get("name"));
		nameColumn.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().subtract(5 + 30 + 40 + 100 * 6 + 140));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		normalizationTable = new TableView<>();
		normalizationTable.setStyle("-fx-font-family: sylfaen;");
		normalizationTable.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().subtract(30).multiply(TABLE_HEIGHT_PART));
		normalizationTable.getColumns().addAll(idColumn, widthColumn, heightColumn, minValueColumn, maxValueColumn, countColumn, durationColumn, normalizationTypeColumn, nameColumn);
		normalizationTable.setRowFactory( tv -> {
			TableRow<GroupedNormalizedDataProperty> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty()) {
					GroupedNormalizedDataProperty groupedNormalizedDataProperty = row.getItem();
					widthField.setNumber(new BigDecimal(groupedNormalizedDataProperty.getWidth()));
					heightField.setNumber(new BigDecimal(groupedNormalizedDataProperty.getHeight()));
					minValueField.setText("" + groupedNormalizedDataProperty.getMinValue());
					maxValueField.setText("" + groupedNormalizedDataProperty.getMaxValue());
					typeComboBox.setValue(groupedNormalizedDataProperty.getNormalizationType());
					nameField.setText(groupedNormalizedDataProperty.getName());
				}
			});
			return row ;
		});
		loadGripedNormalizedData();
		this.getChildren().add(normalizationTable);
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

	private void initBottomPane() {
		widthField = new TCHNumberTextField(TCHComponentSize.SMALL);
		TCHFieldLabel widthFieldLabel = new TCHFieldLabel(Messages.get("width"), widthField);
		heightField = new TCHNumberTextField(TCHComponentSize.SMALL);
		TCHFieldLabel heightFieldLabel = new TCHFieldLabel(Messages.get("height"), heightField);
		minValueField = new TCHTextField(TCHComponentSize.SMALL);
		TCHFieldLabel minValueFieldLabel = new TCHFieldLabel(Messages.get("minValue"), minValueField);
		maxValueField = new TCHTextField(TCHComponentSize.SMALL);
		TCHFieldLabel maxValueFieldLabel = new TCHFieldLabel(Messages.get("maxValue"), maxValueField);
		typeComboBox = new TCHComboBox(Arrays.asList(NormalizationType.values()));
		TCHFieldLabel typeFieldLabel = new TCHFieldLabel(Messages.get("type"), typeComboBox);
		nameField = new TCHTextField(TCHComponentSize.LARGE);
		TCHFieldLabel nameFieldLabel = new TCHFieldLabel(Messages.get("name"), nameField);
		TCHButton normalizeButton = new TCHButton(Messages.get("normalization"));
		normalizeButton.setOnAction(event -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle(Messages.get("resultDirectory"));
			directoryChooser.setInitialDirectory(new File(hrsPathService.getPath(HRSPath.CUT_SYMBOLS_PATH)));
			File directory = directoryChooser.showDialog(ControlPanel.getStage());
			if (directory != null) {
				List<String> files = new ArrayList<>();
				files.add(directory.getAbsolutePath());
				GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
				try {
					groupedNormalizedData.setWidth(widthField.getNumber().intValue());
					groupedNormalizedData.setHeight(heightField.getNumber().intValue());
					groupedNormalizedData.setMinValue(Float.valueOf(minValueField.getText()));
					groupedNormalizedData.setMaxValue(Float.valueOf(maxValueField.getText()));
					groupedNormalizedData.setNormalizationType(NormalizationType.valueOf(typeComboBox.getValue().toString()));
					groupedNormalizedData.setName(nameField.getText());
					Thread thread = new Thread(null, () -> {
						normalizedDataService.addNormalizedDatum(groupedNormalizedData, files);
					});
					thread.start();
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
		});
		FlowPane flowPane = new FlowPane(10, 10);
		flowPane.setPadding(new Insets(10));
		flowPane.setStyle("-fx-border-color: green; -fx-border-radius: 10px; -fx-border-size: 1px;");
		flowPane.setAlignment(Pos.TOP_CENTER);
		flowPane.getChildren().addAll(widthFieldLabel, heightFieldLabel, minValueFieldLabel, maxValueFieldLabel, typeFieldLabel, nameFieldLabel, normalizeButton);
		this.getChildren().add(flowPane);
	}
}
