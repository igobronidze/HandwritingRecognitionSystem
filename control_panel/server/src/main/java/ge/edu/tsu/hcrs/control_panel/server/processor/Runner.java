package ge.edu.tsu.hcrs.control_panel.server.processor;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static void main(String[] args) throws IOException {
//		ImageProcessingManager imageProcessingManager = new ImageProcessingManagerImpl();
//		BufferedImage srcImage = ImageIO.read(new File("C:\\dev\\HandwritingRecognitionSystem\\test_images\\src_images\\asi.JPG"));
//		BufferedImage bufferedImage = imageProcessingManager.resizeImage(srcImage, true, 2, 2);
//		ImageIO.write(bufferedImage, "jpg", new File("C:\\dev\\HandwritingRecognitionSystem\\test_images\\src_images\\asi1.JPG"));
		NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();
		List<GroupedNormalizedData> groupedNormalizedDatas = new ArrayList<>();
		GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
		groupedNormalizedData.setId(23);
		groupedNormalizedDatas.add(groupedNormalizedData);
		List<NormalizedData> normalizedDatum = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatas);
		NormalizedData normalizedData = normalizedDatum.get(0);
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 23; j++) {
				System.out.print(formatter.format(normalizedData.getData()[i * 23 + j]) + " ");
			}
			System.out.println();
		}
	}
}
