package ge.edu.tsu.hcrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.server.dao.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.NormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.processor.NormalizedDataProcessor;

import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private NormalizedDataProcessor normalizedDataProcessor = new NormalizedDataProcessor();

    @Override
    public void addNormalizedData(NormalizedData normalizedData) {
        normalizedDataDAO.addNormalizedData(normalizedData);
    }

    @Override
    public List<NormalizedData> getNormalizedDatas(Integer widht, Integer height, CharSequence charSequence, String generation) {
        return normalizedDataDAO.getNormalizedDatas(widht, height, charSequence, generation);
    }

    @Override
    public int countNormalizedDatas(Integer width, Integer height, CharSequence charSequence, List<String> generations) {
        return normalizedDataProcessor.countNormalizedDatas(width, height, charSequence, generations);
    }

    @Override
    public List<GroupedNormalizedData> getGroupedNormalizedDatas() {
        return normalizedDataDAO.getGroupedNormalizedDatas();
    }
}