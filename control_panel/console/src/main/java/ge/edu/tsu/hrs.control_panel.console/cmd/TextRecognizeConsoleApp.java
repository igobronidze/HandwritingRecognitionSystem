package ge.edu.tsu.hrs.control_panel.console.cmd;

import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hrs.control_panel.server.processor.common.HRSPathProcessor;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextRecognizeConsoleApp {

	private static final HRSPathProcessor hrsPathProcessor = new HRSPathProcessor();

	private static final String originalImageRootDirectory = hrsPathProcessor.getPath(HRSPath.ORIGINAL_IMAGES_PATH);

	private static final NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HRS_NEURAL_NETWORK);

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

			System.out.println("ქსელის id:  (-1 თუ იყენებთ default ქსელს)");
			s = scanner.nextLine();
			if (isRetry(s)) {
				continue;
			}
			int networkId = -1;
			try {
				networkId = Integer.parseInt(s);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				continue;
			}

			System.out.println("ქსელის დამატებითი id:  (0 თუ იყენებთ მთავარ ქსელს)");
			s = scanner.nextLine();
			if (isRetry(s)) {
				continue;
			}
			int networkExtraId = 0;
			try {
				networkExtraId = Integer.parseInt(s);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
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
				List<RecognitionInfo> recognitionInfos = neuralNetworkService.recognizeText(images, networkId, networkExtraId, false);
				for (RecognitionInfo recognitionInfo : recognitionInfos) {
					System.out.println(recognitionInfo.getText());
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				continue;
			}
		}
	}

	private static boolean isRetry(String text) {
		return text.equals("retry");
	}
}
