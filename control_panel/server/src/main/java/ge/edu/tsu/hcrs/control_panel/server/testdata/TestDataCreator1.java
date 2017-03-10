package ge.edu.tsu.hcrs.control_panel.server.testdata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.NormalizedDataProcessor;

import java.util.ArrayList;
import java.util.List;

public class TestDataCreator1 {

    private static NormalizedDataProcessor normalizedDataProcessor = new NormalizedDataProcessor();

    private static String directory = "D:\\Bachelor Project\\HandwrittenRecognitionSystem\\test_images\\result_images\\sylfaen_13";

    public static void main(String[] args) {
        addGroupedNormalizedData(NormalizationType.DISCRETE_BY_AREA);
    }

    private static void addGroupedNormalizedData(NormalizationType type) {
        List<String> directories = new ArrayList<>();
//        directories.add(directory);
        directories.add("D:\\Bachelor Project\\HandwrittenRecognitionSystem\\test_images\\result_images\\asi");
        GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
        groupedNormalizedData.setHeight(29);
        groupedNormalizedData.setWidth(23);
        groupedNormalizedData.setNormalizationType(type);
        groupedNormalizedData.setMinValue(0);
        groupedNormalizedData.setMaxValue(1);
        groupedNormalizedData.setName("asi");
        normalizedDataProcessor.addNormalizedDatum(groupedNormalizedData, directories);
    }
}
