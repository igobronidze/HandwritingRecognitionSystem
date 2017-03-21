package ge.edu.tsu.hcrs.control_panel.server.util;

import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;

public class SystemPathUtil {

    private static SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private static Parameter originalImagesPath = new Parameter("originalImagesPath", "hcrs/images/original_images/");

    private static Parameter cutCharactersPath = new Parameter("cutCharactersPath", "hcrs/images/cut_characters/");

    private static Parameter textsPath = new Parameter("textsPath", "hcrs/texts/");

    public static String getOriginalImagesPath() {
        return HcrsPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(originalImagesPath);
    }

    public static String getCutCharactersPath() {
        return HcrsPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(cutCharactersPath);
    }

    public static String getTextsPath() {
        return HcrsPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(textsPath);
    }
}
