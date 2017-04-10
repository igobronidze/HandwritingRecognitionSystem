package ge.edu.tsu.hrs.control_panel.console.fx.util;

import javafx.scene.image.Image;

public class ImageFactory {

    public static Image getImage(String name) {
        return new Image("static/images/" + name);
    }
}
