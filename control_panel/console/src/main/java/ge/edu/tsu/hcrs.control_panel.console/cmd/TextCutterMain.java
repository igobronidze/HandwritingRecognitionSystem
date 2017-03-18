package ge.edu.tsu.hcrs.control_panel.console.cmd;

import ge.edu.tsu.hcrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;
import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.ContoursDetectorParams;

import java.util.Scanner;

public class TextCutterMain {

    private static ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    private static final String srcImageRootDirectory = "test_directory/src_images/";

    private static final String srcTextRootDirectory = "test_directory/src_texts/";

    private static final String resultImagesRootDirectory = "test_directory/result_images/";

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("ჩაირთო ტექსტის დაჭრის კონსოლური აპლიკაცია!");
            System.out.println("ნებისმიერ მომენტში შეიყვანეთ retry აპლიკაციის თავიდან გასაშვებად");
            System.out.println();

            System.out.println("სურათის წყაროს მისამართი: root მისამართიდან - " + srcImageRootDirectory);
            String s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            String srcImagePath = srcImageRootDirectory + s;
            System.out.println("სურათის სრული მისამართია - " + srcImagePath);
            System.out.println();

            System.out.println("ტექსტის წყაროს მისამართი: root მისამართიდან - " + srcTextRootDirectory);
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            String srcTextPath = srcTextRootDirectory + s;
            System.out.println("ტექსტის სრული მისამართია - " + srcTextPath);
            System.out.println();

            System.out.println("სურათების რეზულტატის დირექტორიის მისამართი: root მისამართიდან - " + resultImagesRootDirectory);
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            String resultImagesDirectory = resultImagesRootDirectory + s;
            System.out.println("რეზულტატური სურათების დირექტორიის სრული მისამართია - " + resultImagesDirectory);
            System.out.println();

            System.out.println("შესამოწმებელი პიქსელის RGB მაქსიმალური მნიშვნელობა:");
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            int checkedRGBMaxValue = -2;
            try {
                checkedRGBMaxValue = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                System.out.println("დაფიქსირდა შეცდომა! პარამეტრი უნდა იყოს ნამდვილი მნიშნელობა!");
                break;
            }
            System.out.println();

            System.out.println("გსურთ თუ არა სურათების შენახვა შეცდომის მიუხედავად (true/false)");
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            boolean saveAnyway = Boolean.parseBoolean(s);
            System.out.println();

            System.out.println("პარამეტრების შევსება დასრულდა. გსურთ დაპროცესირება? (true/false)");
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            boolean process = Boolean.parseBoolean(s);
            if (process) {
                imageProcessingProcessor.cutAndSaveCharactersFromText(srcImagePath, srcTextPath, resultImagesDirectory, new ContoursDetectorParams(checkedRGBMaxValue), saveAnyway);
            }
            System.out.println();

            System.out.println("გსურთ თავიდან გაშვება? (true/false)");
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            boolean again = Boolean.parseBoolean(s);
            if (!again) {
                break;
            }
        }
    }

    private static boolean isRetry(String text) {
        return text.equals("retry");
    }
}
