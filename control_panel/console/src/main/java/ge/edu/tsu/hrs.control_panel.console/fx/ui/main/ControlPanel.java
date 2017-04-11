package ge.edu.tsu.hrs.control_panel.console.fx.ui.main;

import ge.edu.tsu.hrs.control_panel.console.fx.ui.cleanimage.CleanImagePane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.cutsymbols.CutSymbolsPane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.dashboard.AdminDashboard;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol.NetworkControlPane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.normalization.NormalizationPane;
import ge.edu.tsu.hrs.control_panel.console.fx.ui.sysparams.SystemParametersPane;
import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControlPanel extends Application {

    private static BorderPane root;

    private static SystemPageType currPage;

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        }
        root.setTop(new ControlPanelHeader(text));
        root.setBottom(new ControlPanelFooter());
        currPage = systemPageType;
    }
}