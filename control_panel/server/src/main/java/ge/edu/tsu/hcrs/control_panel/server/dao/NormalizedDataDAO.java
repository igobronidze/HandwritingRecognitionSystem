package ge.edu.tsu.hcrs.control_panel.server.dao;

import ge.edu.tsu.hcrs.control_panel.model.network.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;

import java.util.List;

public interface NormalizedDataDAO {

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generation);

    int countNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generations);
}
