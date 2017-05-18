package ge.edu.tsu.hrs.control_panel.console.fx.ui.book;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.book.Book;
import ge.edu.tsu.hrs.control_panel.service.book.BookService;
import ge.edu.tsu.hrs.control_panel.service.book.BookServiceImpl;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookPane extends VBox {

	private long totalWords = 0;

	private TableView<BookProperty> tableView;

	private BookService bookService = new BookServiceImpl();

	public BookPane() {
		initUI();
	}

	private void initUI() {
		this.setAlignment(Pos.TOP_CENTER);
		this.setSpacing(15);
		this.setPadding(new Insets(30, 10, 10, 10));
		initTable();
		initParamsPane();
	}

	private void initParamsPane() {
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(10);
		TCHLabel label = new TCHLabel(Messages.get("sumOfWords") + ": " + totalWords);
		TCHTextField descriptionField = new TCHTextField(TCHComponentSize.MEDIUM);
		TCHFieldLabel descriptionFieldLabel = new TCHFieldLabel(Messages.get("description"), descriptionField);
		TCHButton button = new TCHButton(Messages.get("bookProcessing"));
		button.setOnAction(event -> {
			try {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle(Messages.get("bookProcessing"));
				File directory = directoryChooser.showDialog(ControlPanel.getStage());
				if (directory != null) {
					Thread thread = new Thread(null, () -> {
						bookService.bookProcessing(directory, descriptionField.getText());
					});
					thread.start();
				}
			} catch (Exception ignored) {}
		});
		hBox.getChildren().addAll(descriptionFieldLabel, button);
		this.getChildren().addAll(label, hBox);
	}

	private void initTable() {
		DoubleBinding doubleProperty = ControlPanel.getCenterWidthBinding().subtract(80);
		tableView = new TableView<>();
		tableView.setStyle("-fx-font-family: sylfaen; -fx-text-alignment: center; -fx-font-size: 16px;");
		TableColumn<BookProperty, Boolean> idColumn = new TableColumn<>(Messages.get("id"));
		idColumn.setPrefWidth(60);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<BookProperty, Boolean> nameColumn = new TableColumn<>(Messages.get("name"));
		nameColumn.prefWidthProperty().bind(doubleProperty.divide(8).multiply(3));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<BookProperty, Boolean> descriptionColumn = new TableColumn<>(Messages.get("description"));
		descriptionColumn.prefWidthProperty().bind(doubleProperty.divide(8));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn<BookProperty, Boolean> totalWordsColumn = new TableColumn<>(Messages.get("totalWords"));
		totalWordsColumn.prefWidthProperty().bind(doubleProperty.divide(8));
		totalWordsColumn.setCellValueFactory(new PropertyValueFactory<>("totalWords"));
		TableColumn<BookProperty, Boolean> distinctWordsColumn = new TableColumn<>(Messages.get("distinctWords"));
		distinctWordsColumn.prefWidthProperty().bind(doubleProperty.divide(8));
		distinctWordsColumn.setCellValueFactory(new PropertyValueFactory<>("distinctWords"));
		TableColumn<BookProperty, Boolean> savedWordsColumn = new TableColumn<>(Messages.get("savedWords"));
		savedWordsColumn.prefWidthProperty().bind(doubleProperty.divide(8));
		savedWordsColumn.setCellValueFactory(new PropertyValueFactory<>("savedWords"));
		TableColumn<BookProperty, Boolean> durationColumn = new TableColumn<>(Messages.get("duration"));
		durationColumn.prefWidthProperty().bind(doubleProperty.divide(8));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		tableView.getColumns().addAll(idColumn, nameColumn, descriptionColumn, totalWordsColumn, distinctWordsColumn, savedWordsColumn, durationColumn);
		loadBooks();
		this.getChildren().add(tableView);
	}

	private void loadBooks() {
		List<Book> books = bookService.getBooks();
		List<BookProperty> bookProperties = new ArrayList<>();
		totalWords = 0;
		for (Book book : books) {
			totalWords += book.getSavedWords();
			bookProperties.add(new BookProperty(book));
		}
		ObservableList<BookProperty> data = FXCollections.observableArrayList(bookProperties);
		tableView.setItems(data);
	}
}
