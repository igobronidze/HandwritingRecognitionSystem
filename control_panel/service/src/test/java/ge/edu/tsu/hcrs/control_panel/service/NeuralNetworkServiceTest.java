package ge.edu.tsu.hcrs.control_panel.service;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.*;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.util.CharSequenceInitializer;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuralNetworkServiceTest {

	@Test
	@Ignore
	public void testTrainNeural() throws ControlPanelException {
		NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HCRS_NEURAL_NETWORK);
		NetworkInfo networkInfo = new NetworkInfo();
		networkInfo.setDescription("Description");
		networkInfo.setBiasMinValue(-0.5F);
		networkInfo.setBiasMaxValue(-0.5F);
		CharSequence charSequence = new CharSequence("[ა-ჰ],.!?[0-9]");
		CharSequenceInitializer.initializeCharSequence(charSequence);
		networkInfo.setCharSequence(charSequence);
		List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
		GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
		groupedNormalizedData.setId(17);
		groupedNormalizedData.setWidth(23);
		groupedNormalizedData.setHeight(29);
		groupedNormalizedData.setMinValue(0);
		groupedNormalizedData.setMaxValue(1F);
		groupedNormalizedData.setNormalizationType(NormalizationType.LINEAR_BY_AREA);
		groupedNormalizedDatum.add(groupedNormalizedData);
		networkInfo.setGroupedNormalizedDatum(groupedNormalizedDatum);
		networkInfo.setWeightMinValue(-0.5F);
		networkInfo.setWeightMaxValue(0.5F);
		networkInfo.setHiddenLayer(new ArrayList<>(Arrays.asList(27,27,27)));
		networkInfo.setLearningRate(0.7F);
		networkInfo.setMinError(0.00005F);
		networkInfo.setNetworkMetaInfo("Network meta info");
		networkInfo.setNetworkProcessorType(NetworkProcessorType.HCRS_NEURAL_NETWORK);
		networkInfo.setNumberOfTrainingDataInOneIteration(100);
		networkInfo.setTrainingMaxIteration(5000);
		networkInfo.setTransferFunction(TransferFunction.SIGMOID);
		neuralNetworkService.trainNeural(networkInfo);
	}

	@Test
	@Ignore
	public void testTestNeural() throws ControlPanelException {
		NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HCRS_NEURAL_NETWORK);
		List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
		GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
		groupedNormalizedData.setId(17);
		groupedNormalizedData.setWidth(23);
		groupedNormalizedData.setHeight(29);
		groupedNormalizedData.setMinValue(0);
		groupedNormalizedData.setMaxValue(1F);
		groupedNormalizedData.setNormalizationType(NormalizationType.LINEAR_BY_AREA);
		groupedNormalizedDatum.add(groupedNormalizedData);
		System.out.println(neuralNetworkService.testNeural(groupedNormalizedDatum, 4));
	}

	@Test
	@Ignore
	public void testGetNetworkResult() {
		NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();
		NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HCRS_NEURAL_NETWORK);
		GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
		groupedNormalizedData.setId(17);
		List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
		groupedNormalizedDatum.add(groupedNormalizedData);
		List<NormalizedData> normalizedDatum = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
		NetworkResult networkResult = neuralNetworkService.getNetworkResult(normalizedDatum.get(0), 4);
		System.out.println(networkResult.getAnswer());
	}
}
