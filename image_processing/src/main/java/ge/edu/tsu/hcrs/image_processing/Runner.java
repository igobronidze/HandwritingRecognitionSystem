package ge.edu.tsu.hcrs.image_processing;

import ge.edu.tsu.hcrs.image_processing.opencv.LinesRemover;
import ge.edu.tsu.hcrs.image_processing.opencv.PaperCutter;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper.CutPaperParams;
import ge.edu.tsu.hcrs.image_processing.util.OpenCVUtil;

import java.io.IOException;

public class Runner {

	public static final String testImageName = "music.jpg";

	public static final String testImagePath = "resources\\opencv\\test_images\\" + testImageName;

	private static final String resultImageName = "r_music.jpg";

	public static final String resultImagePath = "resources\\opencv\\result_images\\" + resultImageName;

	public static void main(String[] args) throws IOException {
//		PaperCutter.cutPaperFromImage(testImagePath, resultImagePath, new CutPaperParams());
		LinesRemover.removeLines(OpenCVUtil.getAbsPath(testImagePath), OpenCVUtil.getAbsPath(resultImagePath));
	}
}
