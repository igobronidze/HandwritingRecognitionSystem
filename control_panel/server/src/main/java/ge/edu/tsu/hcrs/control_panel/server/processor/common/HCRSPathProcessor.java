package ge.edu.tsu.hcrs.control_panel.server.processor.common;

import ge.edu.tsu.hcrs.control_panel.model.common.HCRSPath;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hcrs.control_panel.server.util.HcrsPropertiesUtil;

public class HCRSPathProcessor {

    private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private final Parameter originalImagesPath = new Parameter("originalImagesPath", "hcrs/images/original_images/");

    private final Parameter cutCharactersPath = new Parameter("cutCharactersPath", "hcrs/images/cut_characters/");

    private final Parameter textsPath = new Parameter("textsPath", "hcrs/texts/");

    private final Parameter neuralNetworksPath = new Parameter("neuralNetworksPath", "hcrs/networks/");

    public String getPath(HCRSPath hcrsPath) {
        switch (hcrsPath) {
            case ORIGINAL_IMAGES_PATH:
                return HcrsPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(originalImagesPath);
            case CUT_CHARACTERS_PATH:
                return HcrsPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(cutCharactersPath);
            case TEXTS_PATH:
                return HcrsPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(textsPath);
            case NEURAL_NETWORKS_PATH:
                return HcrsPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(neuralNetworksPath);
            default:
                return "";
        }
    }
}
