package ge.edu.tsu.hrs.control_panel.console.fx.ui.main;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.cleanimage.CleanImagePane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.cutsymbols.CutSymbolsPane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.dashboard.AdminDashboard;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol.NetworkControlPane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.normalization.NormalizationPane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.sysparams.SystemParametersPane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.textrecognition.TextRecognitionPane;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.service.startup.StartUpService;
import ge.edu.tsu.hrs.control_panel.service.startup.StartUpServiceImpl;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class ControlPanel extends Application {

    private StartUpService startUpService = new StartUpServiceImpl();

    private static Stage stage;

    private static BorderPane root;

    private static SystemPageType currPage;

    private static final int EXTRA_HEIGHT = 50;

    private static final int EXTRA_WIDTH = 20;

    @Override
    public void start(Stage primaryStage) throws Exception {
        startUpService.process();
        stage = primaryStage;
        primaryStage.setTitle(Messages.get("controlPanel"));
        root = new BorderPane();

        initComponents(SystemPageType.ADMIN_DASHBOARD);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void initComponents(SystemPageType systemPageType) {
        String text = "";
        if (systemPageType == null) {
            systemPageType = currPage;
        }
        switch (systemPageType) {
            case ADMIN_DASHBOARD:
                text = Messages.get("controlPanel");
                root.setCenter(new AdminDashboard());
                break;
            case NETWORK_CONTROL:
                text = Messages.get("networkControl");
                root.setCenter(new NetworkControlPane());
                break;
            case CLEAN_IMAGE:
                text = Messages.get("cleanImage");
                root.setCenter(new CleanImagePane());
                break;
            case CUT_SYMBOLS:
                text = Messages.get("cutSymbols");
                root.setCenter(new CutSymbolsPane());
                break;
            case NORMALIZATION:
                text = Messages.get("normalization");
                root.setCenter(new NormalizationPane());
                break;
            case SYSTEM_PARAMETERS:
                text = Messages.get("systemParameters");
                root.setCenter(new SystemParametersPane());
                break;
            case TEXT_RECOGNITION:
                text = Messages.get("textRecognition");
                root.setCenter(new TextRecognitionPane());
                break;
        }
        root.setTop(new ControlPanelHeader(text));
        root.setBottom(new ControlPanelFooter());
        currPage = systemPageType;
    }

    public static void loadCutSymbolPane(BufferedImage image) {
        root.setCenter(new CutSymbolsPane(image));
        root.setTop(new ControlPanelHeader(Messages.get("cutSymbols")));
        root.setBottom(new ControlPanelFooter());
        currPage = SystemPageType.CUT_SYMBOLS;
    }

    public static Stage getStage() {
        return stage;
    }

    public static DoubleBinding getCenterHeightBinding() {
        return stage.heightProperty().subtract(ControlPanelHeader.LOGO_HEIGHT).subtract(ControlPanelFooter.HEIGHT).subtract(EXTRA_HEIGHT);
    }

    public static DoubleBinding getCenterWidthBinding() {
        return stage.widthProperty().subtract(EXTRA_WIDTH);
    }
}