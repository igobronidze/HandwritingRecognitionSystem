package ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin.BlurringType;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.morphological.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.morphological.MorphologicalType;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.threshold.ThresholdParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.threshold.ThresholdType;
import ge.edu.tsu.hrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkTrainingStatus;
import ge.edu.tsu.hrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hrs.control_panel.model.network.TestingInfo;
import ge.edu.tsu.hrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedProductionNetwork;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.testinginfo.TestingInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.testinginfo.TestingInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.common.HRSPathProcessor;
import ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod.Normalization;
import ge.edu.tsu.hrs.control_panel.server.processor.stringmatching.StringMatchingProcessor;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.control_panel.server.util.CharSequenceInitializer;
import ge.edu.tsu.hrs.control_panel.server.util.GroupedNormalizedDataUtil;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.ContoursDetector;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.TextCutterParams;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextRow;
import ge.edu.tsu.hrs.image_processing.characterdetect.util.ContourUtil;
import ge.edu.tsu.hrs.image_processing.characterdetect.util.TextAdapterUtil;
import ge.edu.tsu.hrs.neural_network.exception.NNException;
import ge.edu.tsu.hrs.neural_network.neural.network.NeuralNetwork;
import ge.edu.tsu.hrs.neural_network.neural.network.NeuralNetworkParameter;
import ge.edu.tsu.hrs.neural_network.neural.network.TrainingData;
import ge.edu.tsu.hrs.neural_network.neural.network.TrainingProgress;
import ge.edu.tsu.hrs.neural_network.neural.testresult.TestResult;
import ge.edu.tsu.hrs.neural_network.transfer.TransferFunctionType;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HRSNeuralNetworkProcessor implements INeuralNetworkProcessor {

	private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

	private final NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

	private final NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

	private final TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

	private final TrainingDataInfoDAO trainingDataInfoDAO = new TrainingDataInfoDAOImpl();

	private final HRSPathProcessor hrsPathProcessor = new HRSPathProcessor();

	private final ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

	private final StringMatchingProcessor stringMatchingProcessor = new StringMatchingProcessor();

	private final Parameter updatePerIterationParameter = new Parameter("updatePerIteration", "1000");

	private final Parameter updatePerSeconds = new Parameter("updatePerSeconds", "10");

	private final Parameter savePerIteration = new Parameter("savePerIteration", "1000");

	private final Parameter minAverageSymbolPerSpace = new Parameter("minAverageSpacePerSymbol", "5.8");

	private final Parameter maxAverageSymbolPerSpace = new Parameter("maxAverageSpacePerSymbol", "9.5");

	private final Parameter imageCleaningType = new Parameter("imageCleaningType", "1");

	private final Parameter percentageOfSameForJoining = new Parameter("percentageOfSameForJoining", "60");

	private final Parameter percentageOfSamesForOneRow = new Parameter("percentageOfSamesForOneRow", "50");

	private final Parameter noiseArea = new Parameter("noiseArea","15");

	private final Parameter notUsedCharsForStringMatching = new Parameter("notUsedCharsForStringMatching", "@#,@#.@#-@#!@#?@#'@#:@#;");

	@Override
	public void trainNeural(NetworkInfo networkInfo, List<GroupedNormalizedData> groupedNormalizedDatum, boolean saveInDatabase) throws ControlPanelException {
		try {
			System.out.println("Start network training!");
			if (!GroupedNormalizedDataUtil.checkGroupedNormalizedDataList(groupedNormalizedDatum)) {
				throw new ControlPanelException();
			}
			GroupedNormalizedData groupedNormalizedData = groupedNormalizedDatum.get(0);
			int width = groupedNormalizedData.getWidth();
			int height = groupedNormalizedData.getHeight();
			CharSequence charSequence = networkInfo.getCharSequence();
			CharSequenceInitializer.initializeCharSequence(charSequence);
			List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
			List<Integer> layers = new ArrayList<>();
			layers.add(width * height);
			layers.addAll(networkInfo.getHiddenLayer());
			layers.add(charSequence.getNumberOfChars());
			NeuralNetwork neuralNetwork = new NeuralNetwork(layers);
			setNeuralNetworkParameters(neuralNetwork, networkInfo);
			for (NormalizedData normalizedData : normalizedDataList) {
				neuralNetwork.addTrainingData(NetworkDataCreator.getTrainingData(normalizedData, charSequence));
			}
			TrainingProgress trainingProgress = new TrainingProgress();
			trainingProgress.setUpdatePerIteration(systemParameterProcessor.getLongParameterValue(updatePerIterationParameter));
			trainingProgress.setSavePerIteration(systemParameterProcessor.getLongParameterValue(savePerIteration));
			networkInfo.setTrainingStatus(NetworkTrainingStatus.TRAINING);
			System.out.println("Gathered data for network info and training progress!");
			int id = networkInfoDAO.addNetworkInfo(networkInfo);
			trainingProgress.setTmpNetworksPath(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + "/");
			TrainingDataInfo trainingDataInfo = new TrainingDataInfo(id, getIdsFromFromGroupedNormalizedDatum(groupedNormalizedDatum), height, width, groupedNormalizedData.getMinValue(), groupedNormalizedData.getMaxValue(),
					groupedNormalizedData.getNormalizationType(), neuralNetwork.getTrainingDataList().size());
			trainingDataInfoDAO.addTrainingDataInfo(trainingDataInfo);
			Runnable run = () -> {
				System.out.println("Start new thread for training progress!");
				while (networkInfo.getTrainingStatus() == NetworkTrainingStatus.TRAINING) {
					try {
						networkInfoDAO.updateTrainingCurrentState(trainingProgress.getCurrentSquaredError(), trainingProgress.getCurrentIterations(), trainingProgress.getCurrentDuration(), id);
						Thread.sleep(systemParameterProcessor.getIntegerParameterValue(updatePerSeconds) * 1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			};
			new Thread(run).start();
			System.out.println("Start train method for network with id - " + id);
			long trainingDuration = neuralNetwork.train(trainingProgress);
			System.out.println("Finished train method for network with id - " + id);
			networkInfoDAO.updateTrainedState(trainingDuration, id);
			networkInfo.setTrainingStatus(NetworkTrainingStatus.TRAINED);
			networkInfoDAO.updateTrainingCurrentState(trainingProgress.getCurrentSquaredError(), trainingProgress.getCurrentIterations(), trainingProgress.getCurrentDuration(), id);
			NeuralNetworkHelper.saveNeuralNetwork(id, neuralNetwork, true, false);
			System.out.println("Finished network training!");
		} catch (NNException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId, int networkExtraId) throws ControlPanelException {
		System.out.println("Start network testing!");
		if (!GroupedNormalizedDataUtil.checkGroupedNormalizedDataList(groupedNormalizedDatum)) {
			throw new ControlPanelException();
		}
		TrainingDataInfo trainingDataInfo = trainingDataInfoDAO.getTrainingDataInfo(networkId);
		if (!GroupedNormalizedDataUtil.compareGroupedNormalizedDatum(groupedNormalizedDatum.get(0), trainingDataInfo)) {
			throw new ControlPanelException();
		}
		List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
		try {
			NeuralNetwork neuralNetwork = NeuralNetworkHelper.loadNeuralNetwork(networkId, networkExtraId, true, false);
			List<TrainingData> trainingDataList = new ArrayList<>();
			CharSequence charSequence = networkInfoDAO.getCharSequenceById(networkId);
			CharSequenceInitializer.initializeCharSequence(charSequence);
			for (NormalizedData normalizedData : normalizedDataList) {
				trainingDataList.add(NetworkDataCreator.getTrainingData(normalizedData, charSequence));
			}
			System.out.println("Start test method for network with id - " + networkId);
			TestResult testResult = neuralNetwork.test(trainingDataList);
			System.out.println("Finished test method for network with id - " + networkId);
			TestingInfo testingInfo = new TestingInfo();
			testingInfo.setNumberOfTest(testResult.getNumberOfData());
			testingInfo.setNetworkId(networkId);
			testingInfo.setNetworkExtraId(networkExtraId);
			testingInfo.setGroupedNormalizedDatum(groupedNormalizedDatum);
			testingInfo.setSquaredError(testResult.getSquaredError());
			testingInfo.setDiffBetweenAnsAndBest(testResult.getDiffBetweenAnsAndBest());
			testingInfo.setPercentageOfIncorrect(testResult.getPercentageOfIncorrect());
			testingInfo.setNormalizedGeneralError(testResult.getNormalizedGeneralError());
			testingInfo.setDuration(testResult.getDuration());
			testingInfoDAO.addTestingInfo(testingInfo);
			return testingInfo.getNormalizedGeneralError();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Finished network testing!");
		return -1;
	}

	@Override
	public NetworkResult getNetworkResult(BufferedImage image, int networkId, int networkExtraId) {
		try {
			System.out.println("Started get network result!");
			NeuralNetwork neuralNetwork = NeuralNetworkHelper.loadNeuralNetwork(networkId, networkExtraId, true, false);
			CharSequence charSequence = networkInfoDAO.getCharSequenceById(networkId);
			TrainingDataInfo trainingDataInfo = trainingDataInfoDAO.getTrainingDataInfo(networkId);
			Normalization normalization = Normalization.getInstance(trainingDataInfo.getNormalizationType());
			NormalizedData normalizedData = normalization.getNormalizedDataFromImage(image, trainingDataInfo, null);
			CharSequenceInitializer.initializeCharSequence(charSequence);
			TrainingData trainingData = NetworkDataCreator.getTrainingData(normalizedData, charSequence);
			List<Float> output = neuralNetwork.getOutputActivation(trainingData);
			NetworkResult networkResult = new NetworkResult();
			networkResult.setOutputActivation(output);
			networkResult.setAnswer(getAns(output, charSequence));
			networkResult.setCharSequence(charSequence);
			System.out.println("Finished get network result!");
			return networkResult;
		} catch (ControlPanelException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RecognitionInfo> recognizeText(List<BufferedImage> images, Integer networkId, int networkExtraId, boolean useWordMatching, boolean analyseMode) {
		System.out.println("Start text recognizing");
		List<RecognitionInfo> recognitionInfos = new ArrayList<>();
		for (BufferedImage image : images) {
			Date date = new Date();
			RecognitionInfo recognitionInfo = new RecognitionInfo();
			StringBuilder text = new StringBuilder();
			NeuralNetwork neuralNetwork;
			TrainingDataInfo trainingDataInfo;
			CharSequence charSequence;
			if (networkId == null || networkId == -1) {
				neuralNetwork = CachedProductionNetwork.getProductionNeuralNetwork();
				trainingDataInfo = CachedProductionNetwork.getProductionTrainingDataInfo();
				charSequence = CachedProductionNetwork.getProductionCharSequence();
				try {
					CharSequenceInitializer.initializeCharSequence(charSequence);
				} catch (ControlPanelException ex) {
					ex.printStackTrace();
				}
			} else {
				neuralNetwork = NeuralNetworkHelper.loadNeuralNetwork(networkId, networkExtraId, true, false);
				charSequence = networkInfoDAO.getCharSequenceById(networkId);
				try {
					CharSequenceInitializer.initializeCharSequence(charSequence);
				} catch (ControlPanelException ex) {
					ex.printStackTrace();
				}
				trainingDataInfo = trainingDataInfoDAO.getTrainingDataInfo(networkId);
			}
			Normalization normalization = Normalization.getInstance(trainingDataInfo.getNormalizationType());
			recognitionInfo.setNetworkInfoGatheringDuration(new Date().getTime() - date.getTime());
			date = new Date();
			BufferedImage cleanedImage;
			switch (systemParameterProcessor.getStringParameterValue(imageCleaningType)) {
				case "1":
					BlurringParameters blurringParameters = new BlurringParameters();
					blurringParameters.setType(BlurringType.BILATERAL_FILTER);
					blurringParameters.setAmount(3);
					ThresholdParameters thresholdParameters = new ThresholdParameters();
					thresholdParameters.setThresholdMethodType(ThresholdType.ADAPTIVE_THRESHOLD);
					thresholdParameters.setBlockSize(23);
					MorphologicalParameters morphologicalParameters = new MorphologicalParameters();
					morphologicalParameters.setMorphologicalType(MorphologicalType.NO_OPERATION);
					cleanedImage = imageProcessingProcessor.cleanImage(image, blurringParameters, thresholdParameters, morphologicalParameters);
					break;
				case "0":
					default:
						cleanedImage = imageProcessingProcessor.cleanImage(image, null, null, null);
			}
			boolean isBinary = true;
			Set<Integer> set = new HashSet<>();
			for (int i = 0; i < cleanedImage.getHeight(); i++) {
				for (int j = 0; j < cleanedImage.getWidth(); j++) {
					set.add(cleanedImage.getRGB(j, i));
					if (set.size() > 2) {
						isBinary = false;
						break;
					}
				}
				if (!isBinary) {
					break;
				}
			}
			recognitionInfo.setCleanImageDuration(new Date().getTime() - date.getTime());
			date = new Date();
			TextCutterParams textCutterParams = new TextCutterParams();
			textCutterParams.setPercentageOfSameForJoining(systemParameterProcessor.getIntegerParameterValue(percentageOfSameForJoining));
			textCutterParams.setPercentageOfSamesForOneRow(systemParameterProcessor.getIntegerParameterValue(percentageOfSamesForOneRow));
			textCutterParams.setNoiseArea(systemParameterProcessor.getIntegerParameterValue(noiseArea));
			TextAdapter textAdapter;
			if (isBinary) {
				textAdapter = ContoursDetector.detectContours(cleanedImage, textCutterParams);
			} else {
				imageProcessingProcessor.fillTextCutterParams(textCutterParams, cleanedImage, false, null);
				textAdapter = ContoursDetector.detectContours(cleanedImage, textCutterParams);
			}
			recognitionInfo.setDetectContoursDuration(new Date().getTime() - date.getTime());
			if (analyseMode) {
				recognitionInfo.setCleanedImage(cleanedImage);
				List<BufferedImage> symbolImages = new ArrayList<>();
				for (TextRow textRow : textAdapter.getRows()) {
					for (Contour contour : textRow.getContours()) {
						if (isBinary) {
							symbolImages.add(ContourUtil.getBufferedImageFromContour(contour));
						} else {
							symbolImages.add(imageProcessingProcessor.simpleClean(ContourUtil.getBufferedImageFromContour(contour)));
						}
					}
				}
				recognitionInfo.setCutSymbolImages(symbolImages);
				recognitionInfo.setNetworkResults(new ArrayList<>());
			}
			long inputDataGatheringDuration = 0;
			long activationDuration = 0;
			long extraDuration = 0;
			for (TextRow textRow : textAdapter.getRows()) {
				int rightPoint = -1;
				StringBuilder line = new StringBuilder();
				StringBuilder word = new StringBuilder();
				for (Contour contour : textRow.getContours()) {
					date = new Date();
					if (rightPoint != -1) {
						if (TextAdapterUtil.isSpace(textAdapter, contour.getLeftPoint() - rightPoint, systemParameterProcessor.getFloatParameterValue(minAverageSymbolPerSpace),
								systemParameterProcessor.getFloatParameterValue(maxAverageSymbolPerSpace))) {
							updateDoubleQuotes(word);
							if (useWordMatching) {
								word = applyWordMatching(word.toString());
							}
							line.append(word).append(" ");
							word = new StringBuilder();
						}
					}
					extraDuration += new Date().getTime() - date.getTime();
					date = new Date();
					rightPoint = contour.getRightPoint();
					NormalizedData normalizedData;
					if (isBinary) {
						normalizedData = normalization.getNormalizedDataFromContour(contour, trainingDataInfo);
					} else {
						normalizedData = normalization.getNormalizedDataFromImage(imageProcessingProcessor.simpleClean(ContourUtil.getBufferedImageFromContour(contour)), trainingDataInfo, null);
					}
					TrainingData trainingData = NetworkDataCreator.getTrainingData(normalizedData, charSequence);
					inputDataGatheringDuration += new Date().getTime() - date.getTime();
					date = new Date();
					List<Float> output = neuralNetwork.getOutputActivation(trainingData);
					activationDuration += new Date().getTime() - date.getTime();
					date = new Date();
					word.append(getAns(output, charSequence));
					extraDuration += new Date().getTime() - date.getTime();
					if (analyseMode) {
						NetworkResult networkResult = new NetworkResult();
						networkResult.setOutputActivation(output);
						networkResult.setAnswer(getAns(output, charSequence));
						networkResult.setCharSequence(charSequence);
						recognitionInfo.getNetworkResults().add(networkResult);
					}
				}
				updateDoubleQuotes(word);
				line.append(word);
				text.append(line).append(System.lineSeparator());
			}
			recognitionInfo.setText(text.toString());
			recognitionInfo.setInputDataGatheringDuration(inputDataGatheringDuration);
			recognitionInfo.setActivationDuration(activationDuration);
			recognitionInfo.setExtraDuration(extraDuration);
			recognitionInfos.add(recognitionInfo);
		}
		System.out.println("Finished text recognizing");
		return recognitionInfos;
	}

	private void updateDoubleQuotes(StringBuilder text) {
		if (text.length() <= 1) {
			return;
		}
		int lastIndex = text.length() - 1;
		if ((text.charAt(lastIndex) == ',' || text.charAt(lastIndex) == '\'') && (text.charAt(lastIndex - 1) == ',' || text.charAt(lastIndex - 1) == '\'')) {
			text.delete(lastIndex - 1, lastIndex);
		}
	}

	private char getAns(List<Float> output, CharSequence charSequence) {
		int ans = 0;
		for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
			if (output.get(i) > output.get(ans)) {
				ans = i;
			}
		}
		return charSequence.getIndexToCharMap().get(ans);
	}

	private List<Integer> getIdsFromFromGroupedNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum) {
		List<Integer> ids = new ArrayList<>();
		for (GroupedNormalizedData groupedNormalizedData : groupedNormalizedDatum) {
			ids.add(groupedNormalizedData.getId());
		}
		return ids;
	}

	private void setNeuralNetworkParameters(NeuralNetwork neuralNetwork, NetworkInfo networkInfo) {
		NeuralNetworkParameter parameter = neuralNetwork.getNeuralNetworkParameter();
		parameter.setWeightMinValue(networkInfo.getWeightMinValue());
		parameter.setWeightMaxValue(networkInfo.getWeightMaxValue());
		parameter.setBiasMinValue(networkInfo.getBiasMinValue());
		parameter.setBiasMaxValue(networkInfo.getBiasMaxValue());
		parameter.setTransferFunctionType(TransferFunctionType.valueOf(networkInfo.getTransferFunction().name()));
		parameter.setLearningRate(networkInfo.getLearningRate());
		parameter.setMinError(networkInfo.getMinError());
		parameter.setTrainingMaxIteration(networkInfo.getTrainingMaxIteration());
		parameter.setNumberOfTrainingDataInOneIteration(networkInfo.getNumberOfTrainingDataInOneIteration());
	}

	private StringBuilder applyWordMatching(String word) {
		Set<String> notUsedChars = new HashSet<>(Arrays.asList(systemParameterProcessor.getStringParameterValue(notUsedCharsForStringMatching).split("@#")));
		int i = word.length() - 1;
		while (i >= 0) {
			String symbol = "" + word.charAt(i);
			if (notUsedChars.contains(symbol)) {
				i--;
			} else {
				break;
			}
		}
		String realSymbols = word.substring(0, i + 1);
		String extraSymbols = word.substring(i + 1, word.length());
		return new StringBuilder(stringMatchingProcessor.getNearestString(realSymbols)).append(extraSymbols);
	}
}
