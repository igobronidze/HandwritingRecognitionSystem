package ge.edu.tsu.hrs.control_panel.server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HRSPropertiesUtil {

	private static final String hrsPropertyPath = "properties/hrs.properties";

	private static Properties properties;

	public static String getProperty(String key) {
		if (properties == null) {
			init();
		}
		return properties.getProperty(key);
	}

	private static void init() {
		properties = new Properties();
		try (FileInputStream in = new FileInputStream(hrsPropertyPath)) {
			properties.load(in);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
