package ge.edu.tsu.hcrs.control_panel.console.cmd;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.server.util.SystemPathUtil;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.hcrs.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import ge.edu.tsu.hcrs.control_panel.service.normalizeddata.GroupedNormalizedDataService;
import ge.edu.tsu.hcrs.control_panel.service.normalizeddata.GroupedNormalizedDataServiceImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class NetworkResultMain {

    private static String s;

    private static final String cutCharactersRootDirectory = SystemPathUtil.getCutCharactersPath();

    private static final NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NetworkProcessorType.HCRS_NEURAL_NETWORK);

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("ჩაირთო სიმბოლოს ამოცნობის აპლიკაცია აპლიკაცია!");
            System.out.println("ნებისმიერ მომენტში შეიყვანეთ retry აპლიკაციის თავიდან გასაშვებად");
            System.out.println();

            System.out.println("სიმბოლოს მისამართი: root მისამართიდან - " + cutCharactersRootDirectory);
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            String symbolPath = cutCharactersRootDirectory + s;
            System.out.println("სიმბოლოს სრული მისამართია - " + symbolPath);
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
                BufferedImage image = ImageIO.read(new File(symbolPath));
                NetworkResult networkResult = neuralNetworkService.getNetworkResult(image, networkId);
                System.out.println("ქსელის პასუხია - " + networkResult.getAnswer());
                System.out.println();

                System.out.println("გსურთ დეტალური ინფორმაციის ნახვა? (true/false)");
                s = scanner.nextLine();
                if (isRetry(s)) {
                    continue;
                }
                boolean info = Boolean.parseBoolean(s);
                if (info) {
                    for (int i = 0; i < networkResult.getOutputActivation().size(); i++) {
                        System.out.print(networkResult.getCharSequence().getIndexToCharMap().get(i) + " - " + networkResult.getOutputActivation().get(i) + "    ");
                        if ((i + 1) % 6 == 0) {
                            System.out.println();
                        }
                    }
                    System.out.println();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                continue;
            }

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
