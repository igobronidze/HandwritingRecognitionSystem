package ge.edu.tsu.hrs.control_panel.service.productionnetwork;

import ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork.ProductionNetworkProcessor;

public class ProductionNetworkServiceImpl implements ProductionNetworkService {

    private final ProductionNetworkProcessor productionNetworkProcessor = new ProductionNetworkProcessor();

    @Override
    public void updateProductionNetwork(int networkId) {
        productionNetworkProcessor.updateProductionNetwork(networkId);
    }
}
