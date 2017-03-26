package ge.edu.tsu.hcrs.control_panel.service;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hcrs.control_panel.model.network.TransferFunction;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.util.CharSequenceInitializer;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import ge.edu.tsu.hcrs.control_panel.service.normalizeddata.GroupedNormalizedDataService;
import ge.edu.tsu.hcrs.control_panel.service.normalizeddata.GroupedNormalizedDataServiceImpl;
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
		networkInfo.setDescription("პირველი რეალური ქსელი");
		networkInfo.setBiasMinValue(-0.5F);
		networkInfo.setBiasMaxValue(-0.5F);
		CharSequence charSequence = new CharSequence("[ა-ჰ],.!?");
		CharSequenceInitializer.initializeCharSequence(charSequence);
		networkInfo.setCharSequence(charSequence);
		List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
		GroupedNormalizedDataService groupedNormalizedDataService = new GroupedNormalizedDataServiceImpl();
		groupedNormalizedDatum.add(groupedNormalizedDataService.getGroupedNormalizedData(32));
		networkInfo.setGroupedNormalizedDatum(groupedNormalizedDatum);
		networkInfo.setWeightMinValue(-0.5F);
		networkInfo.setWeightMaxValue(0.5F);
		networkInfo.setHiddenLayer(new ArrayList<>(Arrays.asList(35,35,35,35)));
		networkInfo.setLearningRate(0.25F);
		networkInfo.setMinError(0.00005F);
		networkInfo.setNetworkMetaInfo("ქსელი გაშებულია 11 განსხვავებულ ფონტზე, ერთი ტექსტით");
		networkInfo.setNetworkProcessorType(NetworkProcessorType.HCRS_NEURAL_NETWORK);
		networkInfo.setNumberOfTrainingDataInOneIteration(300);
		networkInfo.setTrainingMaxIteration(8000);
		networkInfo.setTransferFunction(TransferFunction.SIGMOID);
		neuralNetworkService.trainNeural(networkInfo, true);
	}

	@Test
	@Ignore
	public void testTestNeural() throws ControlPanelException {
		NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HCRS_NEURAL_NETWORK);
		List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
		GroupedNormalizedDataService groupedNormalizedDataService = new GroupedNormalizedDataServiceImpl();
		groupedNormalizedDatum.add(groupedNormalizedDataService.getGroupedNormalizedData(33));
		System.out.println(neuralNetworkService.testNeural(groupedNormalizedDatum, 19));
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
//		NetworkResult networkResult = neuralNetworkService.getNetworkResult(normalizedDatum.get(0), 4);
//		System.out.println(networkResult.getAnswer());
	}
}
