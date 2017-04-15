package ge.edu.tsu.hrs.control_panel.console.fx.ui.cutsymbols;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComboBox;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHComponentSize;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHFieldLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHLabel;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHNumberTextField;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHTextArea;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.ImageFactory;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathService;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathServiceImpl;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;

public class CutSymbolsPane extends VBox {

    private final HRSPathService hrsPathService = new HRSPathServiceImpl();

    private static final double TOP_PANE_PART = 0.80;

    private static final double IMAGE_WIDTH_PART = 0.30;

    private ImageView srcImageView;

    private TCHButton cutButton;

    private TCHTextArea textArea;

    public CutSymbolsPane() {
        this.setSpacing(8);
        this.getChildren().addAll(getTopPane(), getBottomPane());
    }

    private HBox getTopPane() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(4, 4, 0, 4));
        hBox.setSpacing(10);
        hBox.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(TOP_PANE_PART));
        VBox leftVBox = new VBox();
        leftVBox.setSpacing(10);
        srcImageView = new ImageView();
        srcImageView.setImage(ImageFactory.getImage("no_photo.png"));
        srcImageView.fitHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(TOP_PANE_PART).divide(2).subtract(5));
        srcImageView.fitWidthProperty().bind(ControlPanel.getCenterWidthBinding().multiply(IMAGE_WIDTH_PART).subtract(5));
        srcImageView.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
        srcImageView.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));
        srcImageView.setOnMouseClicked(event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(hrsPathService.getPath(HRSPath.CLEANED_IMAGES_PATH)));
                fileChooser.setTitle(Messages.get("openSrcImage"));
                File file = fileChooser.showOpenDialog(ControlPanel.getStage());
                srcImageView.setImage(new Image(file.toURI().toURL().toExternalForm()));
                cutButton.setDisable(false);
            } catch (Exception ex) {
                srcImageView.setImage(ImageFactory.getImage("no_photo.png"));
                cutButton.setDisable(true);
                System.out.println(ex.getMessage());
            }
        });
        textArea = new TCHTextArea();
        textArea.setPromptText(Messages.get("noText"));
        textArea.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(TOP_PANE_PART).divide(2).subtract(5));
        textArea.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding().multiply(IMAGE_WIDTH_PART).subtract(5));
        leftVBox.getChildren().addAll(srcImageView, textArea);
        hBox.getChildren().addAll(leftVBox);
        return hBox;
    }

    private ScrollPane getBottomPane() {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setStyle("-fx-border-color: green; -fx-background-radius: 2px; -fx-background-size: 1px;");
        vBox.prefHeightProperty().bind(ControlPanel.getCenterHeightBinding().multiply(1 - TOP_PANE_PART));
        TCHLabel titleLabel = new TCHLabel(Messages.get("cutSymbolsParams"));
        titleLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 16px;");
        titleLabel.setTextFill(Color.GREEN);
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setHgap(15);
        flowPane.setVgap(5);
        flowPane.prefWidthProperty().bind(ControlPanel.getCenterWidthBinding());
        TCHNumberTextField checkedRGBMaxValueField = new TCHNumberTextField(new BigDecimal("-2"), TCHComponentSize.SMALL);
        TCHFieldLabel checkedRGBMaxValueFieldLabel = new TCHFieldLabel(Messages.get("checkedRGBMaxValue"), checkedRGBMaxValueField);
        TCHNumberTextField checkNeighborRGBMaxValueField = new TCHNumberTextField(new BigDecimal("-5777216"), TCHComponentSize.SMALL);
        TCHFieldLabel checkNeighborRGBMaxValueFieldLabel = new TCHFieldLabel(Messages.get("checkNeighborRGBMaxValue"), checkNeighborRGBMaxValueField);
        TCHComboBox doubleQuoteAsTwoCharComboBox = new TCHComboBox(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
        TCHFieldLabel doubleQuoteAsTwoCharFieldLabel = new TCHFieldLabel(Messages.get("doubleQuoteAsTwoChar"), doubleQuoteAsTwoCharComboBox);
        TCHComboBox useJoiningFunctionalComboBox = new TCHComboBox(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
        TCHFieldLabel useJoiningFunctionalFieldLabel = new TCHFieldLabel(Messages.get("useJoiningFunctional"), useJoiningFunctionalComboBox);
        TCHNumberTextField percentageOfSameForJoiningField = new TCHNumberTextField(new BigDecimal(35), TCHComponentSize.SMALL);
        TCHFieldLabel percentageOfSameForJoiningFieldLabel = new TCHFieldLabel(Messages.get("percentageOfSameForJoining"), percentageOfSameForJoiningField);
        cutButton = new TCHButton("cut");
        cutButton.setDisable(true);
        flowPane.getChildren().addAll(checkedRGBMaxValueFieldLabel, checkNeighborRGBMaxValueFieldLabel, doubleQuoteAsTwoCharFieldLabel, useJoiningFunctionalFieldLabel,
                percentageOfSameForJoiningFieldLabel, cutButton);
        vBox.getChildren().addAll(titleLabel, flowPane);
        scrollPane.setContent(vBox);
        return scrollPane;
    }
}
