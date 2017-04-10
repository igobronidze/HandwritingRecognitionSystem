package ge.edu.tsu.hrs.control_panel.console.fx.util;

import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.service.systemparameter.SystemParameterService;
import ge.edu.tsu.hrs.control_panel.service.systemparameter.SystemParameterServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Messages {

    private static SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private static Parameter systemLanguageCode = new Parameter("systemLanguageCode", "KA");

    private static Map<String, Properties> messages = new HashMap<>();

    private static String languageCode;

    private static String get(String key, String langCode) {
        if (key != null) {
            Properties properties = messages.get(langCode);
            if (properties == null) {
                properties = new Properties();
                try {
                    InputStream is = Messages.class.getResourceAsStream("/messages_" + langCode + ".properties");
                    Reader bufferedReader = new InputStreamReader(is, "UTF-8");
                    properties.load(bufferedReader);
                    messages.put(langCode, properties);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return properties.getProperty(key, key);
        }
        return "";
    }

    public static String get(String key) {
        if (languageCode == null) {
            messages = new HashMap<>();
            languageCode = systemParameterService.getStringParameterValue(systemLanguageCode);
        }
        return get(key, languageCode);
    }

    public static void setLanguageCode(String code) {
        languageCode = code;
        messages = new HashMap<>();
    }
}
