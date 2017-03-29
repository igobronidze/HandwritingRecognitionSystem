package ge.edu.tsu.hrs.control_panel.console.spring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathService;
import ge.edu.tsu.hrs.control_panel.service.common.HRSPathServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.NormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.NormalizedDataServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class NormalizationController {

    private NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();

    private GroupedNormalizedDataService groupedNormalizedDataService = new GroupedNormalizedDataServiceImpl();

    private HRSPathService hrsPathService = new HRSPathServiceImpl();

    @RequestMapping(value = "/normalization", method=RequestMethod.GET)
    public String index(Map<String, Object> model) {
        List<GroupedNormalizedData> groupedNormalizedDatum = groupedNormalizedDataService.getGroupedNormalizedDatum(null, null, null, null, null, null, null);
        model.put("groupedNormalizedDatum", groupedNormalizedDatum);
        model.put("normalizationTypes", NormalizationType.values());
        return "normalization";
    }

    @RequestMapping(value = "/fs", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String fs() {
        String HRSDataPath = hrsPathService.getPath(HRSPath.CUT_CHARACTERS_PATH);
        File root = new File(HRSDataPath);
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode fsJson = jsonNodeFactory.objectNode();
        traverseFs(fsJson, root);
        ObjectNode fullJson = jsonNodeFactory.objectNode();
        ArrayNode arrayNode = jsonNodeFactory.arrayNode();
        arrayNode.add(fsJson);
        fullJson.put("data", arrayNode);
        return fullJson.toString();
    }

    @RequestMapping(value = "/normalize", method=RequestMethod.POST)
    public String normalize(@RequestParam("norm-name") String name,
                              @RequestParam("norm-width") String width,
                              @RequestParam("norm-height") String height,
                              @RequestParam("norm-type") String type,
                              @RequestParam("norm-min-value") String minValue,
                              @RequestParam("norm-max-value") String maxValue,
                              @RequestParam(value = "files", required = false, defaultValue = "{}") String jsonString) throws IOException {
        List<String> files = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode filesJson = mapper.readTree(jsonString);
        JsonNode data = filesJson.findValue("data");
        String rootPath = hrsPathService.getPath(HRSPath.CUT_CHARACTERS_PATH).substring(0, hrsPathService.getPath(HRSPath.CUT_CHARACTERS_PATH).length() - 1);
        rootPath = rootPath.substring(0, rootPath.lastIndexOf("/"));
        for(JsonNode jsonNode : data) {
            files.add(rootPath + jsonNode.toString().substring(1, jsonNode.toString().length() - 1));
        }
        GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
        try {
            groupedNormalizedData.setName(name);
            groupedNormalizedData.setWidth(Integer.parseInt(width));
            groupedNormalizedData.setHeight(Integer.parseInt(height));
            groupedNormalizedData.setNormalizationType(NormalizationType.valueOf(type));
            groupedNormalizedData.setMaxValue(Float.parseFloat(minValue));
            groupedNormalizedData.setMaxValue(Float.parseFloat(maxValue));
            normalizedDataService.addNormalizedDatum(groupedNormalizedData, files);
        } catch (Exception ex) {

        }
        return "redirect:normalization";
    }

    private void traverseFs(ObjectNode parentNode, File parentFile) {
        if(parentFile.isDirectory()) {
            ObjectNode tempJson = parentNode.objectNode();
            tempJson.put("is_dir", "true");
            parentNode.put(parentFile.getName(), tempJson);
            try {
                File[] files = parentFile.listFiles();
                for(File file : files) {
                    traverseFs(tempJson, file);
                }
            } catch(NullPointerException ex) {
            }
        } else {
            parentNode.put(parentFile.getName(), parentNode.objectNode());
        }
    }
}
