package ge.edu.tsu.hrs.control_panel.console.fx.ui;

import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControlPanel extends Application {

    private static BorderPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(Messages.get("controlPanel"));
        root = new BorderPane();

        initComponents();
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void initComponents() {
        root.setTop(new ControlPanelHeader(Messages.get("controlPanel")));
        root.setBottom(new ControlPanelFooter());
    }
}