package ge.edu.tsu.hcrs.control_panel.service;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.service.normalizeddata.NormalizedDataService;
import ge.edu.tsu.hcrs.control_panel.service.normalizeddata.NormalizedDataServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class TMPRunner {

    public static void main(String[] args) {
        GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
        groupedNormalizedData.setHeight(29);
        groupedNormalizedData.setWidth(23);
        groupedNormalizedData.setMinValue(0);
        groupedNormalizedData.setMaxValue(1);
        groupedNormalizedData.setName("კრწანისის ყაყაჩოების პირველი სტროფი ერთი ფონტით სატესტოდ");
        groupedNormalizedData.setNormalizationType(NormalizationType.LINEAR_BY_AREA);

        String file = "D:\\hcrs\\images\\cut_characters\\test\\krw_yay\\alk_life";
        List<String> files = new ArrayList<>();
        files.add(file);

        NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();
        normalizedDataService.addNormalizedDatum(groupedNormalizedData, files);
    }
}
