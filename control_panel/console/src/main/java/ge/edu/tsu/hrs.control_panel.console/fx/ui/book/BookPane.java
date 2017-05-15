package ge.edu.tsu.hrs.control_panel.console.fx.ui.book;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.book.Book;
import ge.edu.tsu.hrs.control_panel.service.book.BookService;
import ge.edu.tsu.hrs.control_panel.service.book.BookServiceImpl;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class BookPane extends VBox {

	private TableView<BookProperty> tableView;

	private BookService bookService = new BookServiceImpl();

	private void initUI() {
		this.setSpacing(20);
		this.setPadding(new Insets(10));
		initTable();
	}

	private void initTable() {
		DoubleBinding doubleProperty = ControlPanel.getCenterWidthBinding().subtract(100);
		tableView = new TableView<>();
		tableView.setStyle("-fx-font-family: sylfaen; -fx-text-alignment: center; -fx-font-size: 16px;");
		TableColumn<BookProperty, Boolean> idColumn = new TableColumn<>(Messages.get("id"));
		idColumn.setPrefWidth(60);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<BookProperty, Boolean> nameColumn = new TableColumn<>(Messages.get("name"));
		nameColumn.prefWidthProperty().bind(doubleProperty.divide(3));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<BookProperty, Boolean> descriptionColumn = new TableColumn<>(Messages.get("description"));
		descriptionColumn.prefWidthProperty().bind(doubleProperty.divide(3));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn<BookProperty, Boolean> totalWordsColumn = new TableColumn<>(Messages.get("totalWords"));
		totalWordsColumn.prefWidthProperty().bind(doubleProperty.divide(3));
		totalWordsColumn.setCellValueFactory(new PropertyValueFactory<>("totalWords"));
		TableColumn<BookProperty, Boolean> distinctWordsColumn = new TableColumn<>(Messages.get("distinctWords"));
		distinctWordsColumn.prefWidthProperty().bind(doubleProperty.divide(3));
		distinctWordsColumn.setCellValueFactory(new PropertyValueFactory<>("distinctWords"));
		TableColumn<BookProperty, Boolean> savedWordsColumn = new TableColumn<>(Messages.get("savedWords"));
		savedWordsColumn.prefWidthProperty().bind(doubleProperty.divide(3));
		savedWordsColumn.setCellValueFactory(new PropertyValueFactory<>("savedWords"));
		TableColumn<BookProperty, Boolean> durationColumn = new TableColumn<>(Messages.get("duration"));
		durationColumn.prefWidthProperty().bind(doubleProperty.divide(3));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		tableView.getColumns().addAll(idColumn, nameColumn, descriptionColumn, totalWordsColumn, distinctWordsColumn, savedWordsColumn, durationColumn);
		loadBooks();
		this.getChildren().add(tableView);
	}

	private void loadBooks() {
		List<Book> books = bookService.getBooks();
		List<BookProperty> bookProperties = new ArrayList<>();
		for (Book book : books) {
			bookProperties.add(new BookProperty(book));
		}
		ObservableList<BookProperty> data = FXCollections.observableArrayList(bookProperties);
		tableView.setItems(data);
	}
}
