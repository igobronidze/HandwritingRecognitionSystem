package ge.edu.tsu.hcrs.control_panel.server.util;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;

import java.util.List;

public class GroupedNormalizedDataUtil {

    public static boolean checkGroupedNormalizedDataList(List<GroupedNormalizedData> groupedNormalizedDatum) {
        for (int i = 1; i < groupedNormalizedDatum.size(); i++) {
            if (!compareGroupedNormalizedDatum(groupedNormalizedDatum.get(i - 1), groupedNormalizedDatum.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareGroupedNormalizedDatum(GroupedNormalizedData data1, GroupedNormalizedData data2) {
        return data1.getHeight() == data2.getHeight() && data1.getWidth() == data2.getWidth() && data1.getNormalizationType() == data2.getNormalizationType() &&
                data1.getMinValue() == data2.getMinValue() && data1.getMaxValue() == data2.getMaxValue();
    }
}
