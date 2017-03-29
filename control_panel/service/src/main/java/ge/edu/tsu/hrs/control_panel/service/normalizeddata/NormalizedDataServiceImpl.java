package ge.edu.tsu.hrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.NormalizedDataProcessor;

import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private NormalizedDataProcessor normalizedDataProcessor = new NormalizedDataProcessor();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    @Override
    public List<NormalizedData> getNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum) {
        return normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
    }

    @Override
    public void addNormalizedDatum(GroupedNormalizedData groupedNormalizedData, List<String> files) {
        normalizedDataProcessor.addNormalizedDatum(groupedNormalizedData, files);
    }
}