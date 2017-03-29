package ge.edu.tsu.hrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAOImpl;

import java.util.List;

public class GroupedNormalizedDataServiceImpl implements GroupedNormalizedDataService {

    private GroupedNormalizedDataDAO groupedNormalizedDataDAO = new GroupedNormalizedDataDAOImpl();

    @Override
    public GroupedNormalizedData getGroupedNormalizedData(Integer id) {
        List<GroupedNormalizedData> groupedNormalizedDatum = groupedNormalizedDataDAO.getGroupedNormalizedDatum(id, null, null, null, null, null, null);
        if (groupedNormalizedDatum.size() != 0) {
            return groupedNormalizedDatum.get(0);
        }
        return null;
    }

    @Override
    public List<GroupedNormalizedData> getGroupedNormalizedDatum(Integer id, Integer height, Integer width, Float minValue, Float maxValue, NormalizationType normalizationType, String name) {
        return groupedNormalizedDataDAO.getGroupedNormalizedDatum(id, height, width, minValue, maxValue, normalizationType, name);
    }
}
