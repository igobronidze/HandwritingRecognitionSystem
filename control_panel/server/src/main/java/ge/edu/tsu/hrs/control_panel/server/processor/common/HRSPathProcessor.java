package ge.edu.tsu.hrs.control_panel.server.processor.common;

import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.control_panel.server.util.HRSPropertiesUtil;

public class HRSPathProcessor {

	private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

	private final Parameter originalImagesPath = new Parameter("originalImagesPath", "HRSImageData/original_images/");

	private final Parameter cutSymbolsPath = new Parameter("cutSymbolsPath", "HRSImageData/cut_symbols/");

	private final Parameter textsPath = new Parameter("textsPath", "HRSImageData/real_text/");

	private final Parameter neuralNetworksPath = new Parameter("neuralNetworksPath", "HRSNetworkData/");

	public String getPath(HRSPath hrsPath) {
		switch (hrsPath) {
			case ORIGINAL_IMAGES_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(originalImagesPath);
			case CUT_SYMBOLS_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(cutSymbolsPath);
			case TEXTS_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(textsPath);
			case NEURAL_NETWORKS_PATH:
				return HRSPropertiesUtil.getProperty("fileSystemRootPath") + systemParameterProcessor.getStringParameterValue(neuralNetworksPath);
			default:
				return "";
		}
	}
}
