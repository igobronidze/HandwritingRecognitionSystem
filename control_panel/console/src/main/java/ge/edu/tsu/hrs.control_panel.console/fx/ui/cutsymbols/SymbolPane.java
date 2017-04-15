package ge.edu.tsu.hrs.control_panel.console.fx.ui.cutsymbols;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;

public class SymbolPane extends VBox {

    private final int imageWidth = 30;

    private final int imageHeight = 30;

    private BufferedImage image;

    private int index;

    public SymbolPane(BufferedImage image, int index) {
        this.image = image;
        this.index = index;
        this.setSpacing(3);
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(getImageView(), getHBox());
    }

    private ImageView getImageView() {
        ImageView imageView = new ImageView();
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);
        return imageView;
    }

    private HBox getHBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(2);
        hBox.setAlignment(Pos.TOP_CENTER);
        Button leftButton = new Button("<");
        leftButton.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        TextField textField = new TextField();
        textField.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        Button rightButton = new Button(">");
        rightButton.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        Button deleteButton = new Button("-");
        deleteButton.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        hBox.getChildren().addAll(leftButton, textField, rightButton, deleteButton);
        return hBox;
    }
}
