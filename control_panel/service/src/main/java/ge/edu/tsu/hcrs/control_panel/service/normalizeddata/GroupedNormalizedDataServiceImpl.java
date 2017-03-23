package ge.edu.tsu.hcrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAOImpl;

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
}
