package ge.edu.tsu.hcrs.control_panel.console.spring;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Map;

@Controller
public class NormalizationController {

    @RequestMapping(value = "/normalization", method=RequestMethod.GET)
    public String index(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping(value = "/fs", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String fs() {
        String HRSDataPath = "/home/iliakp/Desktop";
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

    private void traverseFs(ObjectNode parentNode, File parentFile) {
        if(parentFile.isDirectory()) {
            ObjectNode tempJson = parentNode.objectNode();
            parentNode.put(parentFile.getName(), tempJson);
            parentNode.put("is_dir", "true");
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
