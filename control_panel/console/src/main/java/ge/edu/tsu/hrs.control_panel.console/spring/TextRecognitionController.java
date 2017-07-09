package ge.edu.tsu.hrs.control_panel.console.spring;

import ge.edu.tsu.hrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TextRecognitionController {

    private final NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HRS_NEURAL_NETWORK);

    @RequestMapping(value = "/recognition", method = RequestMethod.GET)
    public String recognition(Map<String, Object> model) {
        return "recognition";
    }

    @RequestMapping(value = "/recognize", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileHandler(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            InputStream in = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(in);
            List<BufferedImage> images = new ArrayList<>();
            images.add(image);
            List<RecognitionInfo> recognitionInfos = neuralNetworkService.recognizeText(images, -1, 0, false, false);
            return recognitionInfos.get(0).getText();
        } catch (Exception e) {
            return "You failed to upload";
        }
    }
}
