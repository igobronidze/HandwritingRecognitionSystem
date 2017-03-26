package ge.edu.tsu.hcrs.control_panel.server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HcrsPropertiesUtil {

    private static final String hcrsPropertyPath = "properties/hcrs.properties";

    private static Properties properties;

    public static String getProperty(String key) {
        if (properties == null) {
            init();
        }
        return properties.getProperty(key);
    }

    private static void init() {
        properties = new Properties();
        try (FileInputStream in = new FileInputStream(hcrsPropertyPath)) {
            properties.load(in);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
