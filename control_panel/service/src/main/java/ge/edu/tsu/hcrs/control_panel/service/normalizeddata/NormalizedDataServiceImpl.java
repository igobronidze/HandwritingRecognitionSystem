package ge.edu.tsu.hcrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;

import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    @Override
    public void addNormalizedDatum(List<NormalizedData> normalizedDatum, int groupedNormalizedDataId) {
        normalizedDataDAO.addNormalizedDatum(normalizedDatum, groupedNormalizedDataId);
    }

    @Override
    public List<NormalizedData> getNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum) {
        return normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
    }
}