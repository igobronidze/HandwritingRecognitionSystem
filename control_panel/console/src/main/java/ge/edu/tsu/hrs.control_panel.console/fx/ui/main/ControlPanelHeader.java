package ge.edu.tsu.hrs.control_panel.console.fx.ui.main;

import ge.edu.tsu.hrs.control_panel.console.fx.util.ImageFactory;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ControlPanelHeader extends BorderPane {

    public static final int LOGO_HEIGHT = 120;

    private static final int LOGO_WIDTH = 200;

    private ImageView logoImageView;

    private Label mainLabel;

    private HBox localLogos;

    public ControlPanelHeader(String text) {
        initComponents(text);
        initUI();
    }

    private void initUI() {
        this.setHeight(LOGO_HEIGHT);
        this.setStyle("-fx-background-color: #EBE6E5");
        this.setLeft(logoImageView);
        this.setCenter(mainLabel);
        this.setRight(localLogos);
    }

    private void initComponents(String text) {
        Image logo = ImageFactory.getImage("logo_1.png");
        logoImageView = new ImageView();
        logoImageView.setOnMouseEntered(event -> ControlPanelHeader.this.setCursor(Cursor.HAND));
        logoImageView.setOnMouseExited(event -> ControlPanelHeader.this.setCursor(Cursor.DEFAULT));
        logoImageView.setOnMouseClicked(event -> ControlPanel.initComponents(SystemPageType.ADMIN_DASHBOARD));
        logoImageView.setImage(logo);
        logoImageView.setFitWidth(LOGO_WIDTH);
        logoImageView.setFitHeight(LOGO_HEIGHT);

        mainLabel = new Label(text);
        mainLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 28px;");

        Image geoLogo = ImageFactory.getImage("geo.png");
        ImageView geoImageView = new ImageView();
        geoImageView.setOnMouseEntered(event -> ControlPanelHeader.this.setCursor(Cursor.HAND));
        geoImageView.setOnMouseExited(event -> ControlPanelHeader.this.setCursor(Cursor.DEFAULT));
        geoImageView.setOnMouseClicked(event -> {
            Messages.setLanguageCode("KA");
            ControlPanel.initComponents(null);
        });
        geoImageView.setImage(geoLogo);
        geoImageView.setFitWidth(35);
        geoImageView.setFitHeight(30);
        Image engLogo = ImageFactory.getImage("eng.png");
        ImageView engImageView = new ImageView();
        engImageView.setOnMouseEntered(event -> ControlPanelHeader.this.setCursor(Cursor.HAND));
        engImageView.setOnMouseExited(event -> ControlPanelHeader.this.setCursor(Cursor.DEFAULT));
        engImageView.setOnMouseClicked(event -> {
            Messages.setLanguageCode("EN");
            ControlPanelHeader.this.requestLayout();
            ControlPanel.initComponents(null);
        });
        engImageView.setImage(engLogo);
        engImageView.setFitWidth(35);
        engImageView.setFitHeight(30);
        localLogos = new HBox();
        localLogos.setAlignment(Pos.TOP_RIGHT);
        localLogos.setSpacing(5);
        localLogos.setPadding(new Insets(5, 5, 5, 5));
        localLogos.setPrefWidth(LOGO_WIDTH);
        localLogos.getChildren().addAll(geoImageView, engImageView);
    }
}
