package ge.edu.tsu.hcrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.NormalizedDataProcessor;

import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private GroupedNormalizedDataDAO groupedNormalizedDataDAO = new GroupedNormalizedDataDAOImpl();

    private NormalizedDataProcessor normalizedDataProcessor = new NormalizedDataProcessor();

    @Override
    public List<GroupedNormalizedData> getGroupedNormalizedDatum(Integer id, Integer height, Integer width, Float minValue, Float maxValue, NormalizationType normalizationType, String name) {
        return groupedNormalizedDataDAO.getGroupedNormalizedDatum(id, height, width, minValue, maxValue, normalizationType, name);
    }

    @Override
    public void addNormalizedDatum(GroupedNormalizedData groupedNormalizedData, List<String> directories) {
        normalizedDataProcessor.addNormalizedDatum(groupedNormalizedData, directories);
    }
}