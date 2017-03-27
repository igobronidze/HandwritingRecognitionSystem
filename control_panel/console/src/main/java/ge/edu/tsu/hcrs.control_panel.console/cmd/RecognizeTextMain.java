package ge.edu.tsu.hcrs.control_panel.console.cmd;

import ge.edu.tsu.hcrs.control_panel.model.common.HCRSPath;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hcrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hcrs.control_panel.server.processor.common.HCRSPathProcessor;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecognizeTextMain {

	private static final HCRSPathProcessor hcrsPathProcessor = new HCRSPathProcessor();

	private static final String originalImageRootDirectory = hcrsPathProcessor.getPath(HCRSPath.ORIGINAL_IMAGES_PATH);

	private static final NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HCRS_NEURAL_NETWORK);

	public static void main(String[] args) {
		while(true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println();
			System.out.println("ჩაირთო ტექსტის ამოცნობის აპლიკაცია!");
			System.out.println("ნებისმიერ მომენტში შეიყვანეთ retry აპლიკაციის თავიდან გასაშვებად");
			System.out.println();

			System.out.println("ტექსტის მისამართი: root მისამართიდან - " + originalImageRootDirectory);
			String s = scanner.nextLine();
			if (isRetry(s)) {
				continue;
			}
			String textPath = originalImageRootDirectory + s;
			System.out.println("ტექსტის სრული მისამართია - " + textPath);
			System.out.println();

			System.out.println("ქსელის id:");
			s = scanner.nextLine();
			if (isRetry(s)) {
				continue;
			}
			int networkId = -1;
			try {
				networkId = Integer.parseInt(s);
			} catch (NumberFormatException ex) {
				System.out.println(ex.getMessage());
				continue;
			}

			System.out.println("პარამეტრების შევსება დასრულდა. გსურთ დაპროცესირება? (true/false)");
			s = scanner.nextLine();
			if (isRetry(s)) {
				continue;
			}
			if (!Boolean.parseBoolean(s)) {
				continue;
			}

			try {
				BufferedImage image = ImageIO.read(new File(textPath));
				List<BufferedImage> images = new ArrayList<>();
				images.add(image);
				List<RecognitionInfo> recognitionInfos = neuralNetworkService.recognizeText(images, networkId);
				for (RecognitionInfo recognitionInfo : recognitionInfos) {
					System.out.println(recognitionInfo.getText());
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				continue;
			}
		}
	}

	private static boolean isRetry(String text) {
		return text.equals("retry");
	}
}
