package ge.edu.tsu.hrs.control_panel.console.fx.ui.cutsymbols;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.component.TCHButton;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.main.ControlPanel;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.TextCutterParameters;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathService;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.imageprocessing.ImageProcessingService;
import ge.edu.tsu.hrs.control_panel.service.imageprocessing.ImageProcessingServiceImpl;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class SymbolsPane extends FlowPane {

    private ImageProcessingService imageProcessingService = new ImageProcessingServiceImpl();

    private HRSPathService hrsPathService = new HRSPathServiceImpl();

    private List<BufferedImage> images;

    private List<String> symbols;

    private TextCutterParameters parameters;

    public SymbolsPane() {
        this.setPadding(new Insets(8, 8, 8, 8));
        this.setHgap(15);
        this.setVgap(15);
    }

    public void initSymbols(List<BufferedImage> images, List<String> symbols, TextCutterParameters parameter) {
        this.images = images;
        this.symbols = symbols;
        this.parameters = parameter;
        for (int i = symbols.size(); i < images.size(); i++) {
            symbols.add("");
        }
        updateSymbols();
    }

    public void updateSymbols() {
        this.getChildren().clear();
        for (int i = 0; i < images.size(); i++) {
            String symbol = i < symbols.size() ? "" + symbols.get(i) : "";
            SymbolPane symbolPane = new SymbolPane(images.get(i), symbol, i, parameters, this);
            this.getChildren().add(symbolPane);
        }
        TCHButton saveButton = new TCHButton(Messages.get("save"));
        saveButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle(Messages.get("resultDirectory"));
            directoryChooser.setInitialDirectory(new File(hrsPathService.getPath(HRSPath.CUT_SYMBOLS_PATH)));
            File directory = directoryChooser.showDialog(ControlPanel.getStage());
            if (directory != null) {
                imageProcessingService.saveCutSymbols(images, symbols, directory.getAbsolutePath());
            }
        });
        this.getChildren().add(saveButton);
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public List<String> getSymbols() {
        return symbols;
    }
}
