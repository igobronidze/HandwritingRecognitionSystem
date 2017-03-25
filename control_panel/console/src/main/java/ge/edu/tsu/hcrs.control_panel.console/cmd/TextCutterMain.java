package ge.edu.tsu.hcrs.control_panel.console.cmd;

import ge.edu.tsu.hcrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;
import ge.edu.tsu.hcrs.control_panel.server.util.SystemPathUtil;
import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.TextCutterParams;

import java.util.Scanner;

public class TextCutterMain {

    private static ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    private static final String srcImageRootDirectory = SystemPathUtil.getOriginalImagesPath();

    private static final String srcTextRootDirectory = SystemPathUtil.getTextsPath();

    private static final String resultImagesRootDirectory = SystemPathUtil.getCutCharactersPath();

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

            TextCutterParams textCutterParams = new TextCutterParams();
            System.out.println("დაიწყო პარამეტრების შერჩევის რეჟიმი(ჯერ აირჩიეთ პარამეტრის ნომერი შემდეგ კი სასურველი პარამეტრი)");
            boolean retry = false;
            while (true) {
                System.out.println("0 - პარამეტრების რეჟიმიდან გასვლა");
                System.out.println("1 - შესამოწმებელი პიქსელის RGB მაქსიმალური მნიშვნელობა: არსებული მნიშვნელობა - " + textCutterParams.getCheckedRGBMaxValue());
                System.out.println("2 - მეზობლებზე გადასასვლელი პიქსელი RGB მაქსიმალური მნიშვნელობა: არსებული მნიშვნელობა - " + textCutterParams.getCheckNeighborRGBMaxValue());
                System.out.println("3 - ბრჭყალების ორ სიმბოლოდ ჩათვლა: არსებული მნიშვნელობა - " + textCutterParams.isDoubleQuoteAsTwoChar());
                System.out.println("4 - გამოიყენოს შეერთების ფუნქციონალი: არსებული მნიშვნელობა - " + textCutterParams.isUseJoiningFunctional());
                System.out.println("5 - შეცდომის მიუხედავად სურათების ბეჭდვა: არსებული მნიშვნელობა - " + textCutterParams.isSaveAnyway());
                System.out.println("6 - პროცენტული დამთხვევა, რომ ორი სიმბოლო ერთად გამოცხადდეს: არსებული მნიშვნელობა -  " + textCutterParams.getPercentageOfSameForJoining());
                s = scanner.nextLine();
                if (isRetry(s)) {
                    retry = true;
                    break;
                }
                boolean end = false;
                try {
                    switch (s) {
                        case "1":
                            System.out.println("შესაძლო მნიშვნელობები: [-1  -  -16777216]");
                            s = scanner.nextLine();
                            if (isRetry(s)) {
                                retry = true;
                                break;
                            }
                            textCutterParams.setCheckedRGBMaxValue(Integer.parseInt(s));
                            break;
                        case "2":
                            System.out.println("შესაძლო მნიშვნელობები: [-1  -  -16777216]");
                            s = scanner.nextLine();
                            if (isRetry(s)) {
                                retry = true;
                                break;
                            }
                            textCutterParams.setCheckNeighborRGBMaxValue(Integer.parseInt(s));
                            break;
                        case "3":
                            System.out.println("შესაძლო მნიშვნელობები: true/false");
                            s = scanner.nextLine();
                            if (isRetry(s)) {
                                retry = true;
                                break;
                            }
                            textCutterParams.setDoubleQuoteAsTwoChar(Boolean.parseBoolean(s));
                            break;
                        case "4":
                            System.out.println("შესაძლო მნიშვნელობები: true/false");
                            s = scanner.nextLine();
                            if (isRetry(s)) {
                                retry = true;
                                break;
                            }
                            textCutterParams.setUseJoiningFunctional(Boolean.parseBoolean(s));
                            break;
                        case "5":
                            System.out.println("შესაძლო მნიშვნელობები: true/false");
                            s = scanner.nextLine();
                            if (isRetry(s)) {
                                retry = true;
                                break;
                            }
                            textCutterParams.setSaveAnyway(Boolean.parseBoolean(s));
                            break;
                        case "6":
                            s = scanner.nextLine();
                            if (isRetry(s)) {
                                retry = true;
                                break;
                            }
                            textCutterParams.setPercentageOfSameForJoining(Integer.parseInt(s));
                            break;
                        default:
                            end = true;
                    }
                } catch (NumberFormatException ex) {
                }
                if (end) {
                    break;
                }
            }
            if (retry) {
                continue;
            }

            System.out.println("პარამეტრების შევსება დასრულდა. გსურთ დაპროცესირება? (true/false)");
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            boolean process = Boolean.parseBoolean(s);
            if (process) {
                imageProcessingProcessor.cutAndSaveCharactersFromText(srcImagePath, srcTextPath, resultImagesDirectory, textCutterParams);
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
