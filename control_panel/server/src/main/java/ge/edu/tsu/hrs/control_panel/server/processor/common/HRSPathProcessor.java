package ge.edu.tsu.hrs.control_panel.server.processor.common;

import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.control_panel.server.util.HRSPropertiesUtil;

public class HRSPathProcessor {

	private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

	private final Parameter originalImagesPath = new Parameter("originalImagesPath", "hrs/images/original_images/");

	private final Parameter cutCharactersPath = new Parameter("cutCharactersPath", "hrs/images/cut_characters/");

	private final Parameter textsPath = new Parameter("textsPath", "hrs/texts/");

	private final Parameter neuralNetworksPath = new Parameter("neuralNetworksPath", "hrs/networks/");

	public String getPath(HRSPath hrsPath) {
		switch (hrsPath) {
			case ORIGINAL_IMAGES_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(originalImagesPath);
			case CUT_CHARACTERS_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(cutCharactersPath);
			case TEXTS_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(textsPath);
			case NEURAL_NETWORKS_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(neuralNetworksPath);
			default:
				return "";
		}
	}
}
