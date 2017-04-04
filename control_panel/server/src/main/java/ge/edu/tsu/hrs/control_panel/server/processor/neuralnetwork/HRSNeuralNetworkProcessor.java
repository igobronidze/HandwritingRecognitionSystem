package ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
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
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.testinginfo.TestingInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.testinginfo.TestingInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod.Normalization;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.control_panel.server.util.CharSequenceInitializer;
import ge.edu.tsu.hrs.control_panel.server.util.GroupedNormalizedDataUtil;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.ContoursDetector;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.TextCutterParams;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextRow;
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
import java.util.Date;
import java.util.List;

public class HRSNeuralNetworkProcessor implements INeuralNetworkProcessor {

	private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

	private final NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

	private final NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

	private final TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

	private final TrainingDataInfoDAO trainingDataInfoDAO = new TrainingDataInfoDAOImpl();

	private final ProductionNetworkProcessor productionNetworkProcessor = new ProductionNetworkProcessor();

	private final Parameter updatePerIterationParameter = new Parameter("updatePerIteration", "1000");

	private final Parameter updatePerSeconds = new Parameter("updatePerSeconds", "10");

	private final Parameter deltaForNotSpaces = new Parameter("deltaForNotSpaces", "3");

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
			List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
			List<Integer> layers = new ArrayList<>();
			layers.add(width * height);
			layers.addAll(networkInfo.getHiddenLayer());
			layers.add(charSequence.getNumberOfChars());
			NeuralNetwork neuralNetwork = new NeuralNetwork(layers);
			setNeuralNetworkParameters(neuralNetwork, networkInfo);
			for (NormalizedData aNormalizedDataList : normalizedDataList) {
				neuralNetwork.addTrainingData(NetworkDataCreator.getTrainingData(aNormalizedDataList, charSequence));
			}
			TrainingProgress trainingProgress = new TrainingProgress();
			trainingProgress.setUpdatePerIteration(systemParameterProcessor.getLongParameterValue(updatePerIterationParameter));
			networkInfo.setTrainingStatus(NetworkTrainingStatus.TRAINING);
			System.out.println("Gathered data for network info and training progress!");
			int id = networkInfoDAO.addNetworkInfo(networkInfo);
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
						System.out.println(ex.getMessage());
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
			NeuralNetworkHelper.saveNeuralNetwork(id, neuralNetwork, true, null);
			System.out.println("Finished network training!");
		} catch (NNException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId) throws ControlPanelException {
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
			NeuralNetwork neuralNetwork = NeuralNetworkHelper.loadNeuralNetwork(networkId, true, null);
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
			testingInfo.setGroupedNormalizedDatum(groupedNormalizedDatum);
			testingInfo.setSquaredError(testResult.getSquaredError());
			testingInfo.setDiffBetweenAnsAndBest(testResult.getDiffBetweenAnsAndBest());
			testingInfo.setPercentageOfIncorrect(testResult.getPercentageOfIncorrect());
			testingInfo.setNormalizedGeneralError(testResult.getNormalizedGeneralError());
			testingInfo.setDuration(testResult.getDuration());
			testingInfoDAO.addTestingInfo(testingInfo);
			return testingInfo.getNormalizedGeneralError();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("Finished network testing!");
		return -1;
	}

	@Override
	public NetworkResult getNetworkResult(BufferedImage image, int networkId) {
		try {
			System.out.println("Started get network result!");
			NeuralNetwork neuralNetwork = NeuralNetworkHelper.loadNeuralNetwork(networkId, true, null);
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
			System.out.println(ex.getMessage());
		}
		return null;
	}

	@Override
	public List<RecognitionInfo> recognizeText(List<BufferedImage> images, Integer networkId) {
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
				neuralNetwork = productionNetworkProcessor.getProductionNeuralNetwork();
				trainingDataInfo = productionNetworkProcessor.getProductionTrainingDataInfo();
				charSequence = productionNetworkProcessor.getProductionCharSequence();
				try {
					CharSequenceInitializer.initializeCharSequence(charSequence);
				} catch (ControlPanelException ex) {}
			} else {
				neuralNetwork = NeuralNetworkHelper.loadNeuralNetwork(networkId, true, null);
				charSequence = networkInfoDAO.getCharSequenceById(networkId);
				try {
					CharSequenceInitializer.initializeCharSequence(charSequence);
				} catch (ControlPanelException ex) {}
				trainingDataInfo = trainingDataInfoDAO.getTrainingDataInfo(networkId);
			}
			Normalization normalization = Normalization.getInstance(trainingDataInfo.getNormalizationType());
			recognitionInfo.setNetworkInfoGatheringDuration(new Date().getTime() - date.getTime());
			date = new Date();
			TextAdapter textAdapter = ContoursDetector.detectContours(image, new TextCutterParams());
			recognitionInfo.setDetectContoursDuration(new Date().getTime() - date.getTime());
			long inputDataGatheringDuration = 0;
			long activationDuration = 0;
			long extraDuration = 0;
			for (TextRow textRow : textAdapter.getRows()) {
				int rightPoint = -1;
				for (Contour contour : textRow.getContours()) {
					date = new Date();
					if (rightPoint != -1) {
						if (TextAdapterUtil.isSpace(textAdapter, contour.getLeftPoint() - rightPoint + 1, systemParameterProcessor.getIntegerParameterValue(deltaForNotSpaces))) {
							text.append(" ");
						}
					}
					extraDuration += new Date().getTime() - date.getTime();
					date = new Date();
					rightPoint = contour.getRightPoint();
					NormalizedData normalizedData = normalization.getNormalizedDataFromContour(contour, trainingDataInfo);
					TrainingData trainingData = NetworkDataCreator.getTrainingData(normalizedData, charSequence);
					inputDataGatheringDuration += new Date().getTime() - date.getTime();
					date = new Date();
					List<Float> output = neuralNetwork.getOutputActivation(trainingData);
					activationDuration += new Date().getTime() - date.getTime();
					date = new Date();
					text.append(getAns(output, charSequence));
					updateDoubleQuotes(text);
					extraDuration += new Date().getTime() - date.getTime();
				}
				text.append(System.lineSeparator());
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
}
