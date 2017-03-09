package ge.edu.tsu.hcrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;

import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private GroupedNormalizedDataDAO groupedNormalizedDataDAO = new GroupedNormalizedDataDAOImpl();

    @Override
    public void addNormalizedDatum(List<NormalizedData> normalizedDatum, int groupedNormalizedDataId) {
        normalizedDataDAO.addNormalizedDatum(normalizedDatum, groupedNormalizedDataId);
    }

    @Override
    public List<GroupedNormalizedData> getGroupedNormalizedDatum(Integer height, Integer width, Float minValue, Float maxValue, NormalizationType normalizationType, String name) {
        return groupedNormalizedDataDAO.getGroupedNormalizedDatum(height, width, minValue, maxValue, normalizationType, name);
    }

}