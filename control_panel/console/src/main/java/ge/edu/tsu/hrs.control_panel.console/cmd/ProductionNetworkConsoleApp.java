package ge.edu.tsu.hrs.control_panel.console.cmd;

import ge.edu.tsu.hrs.control_panel.service.productionnetwork.ProductionNetworkService;
import ge.edu.tsu.hrs.control_panel.service.productionnetwork.ProductionNetworkServiceImpl;

import java.util.Scanner;

public class ProductionNetworkConsoleApp {

    private static final ProductionNetworkService productionNetworkService = new ProductionNetworkServiceImpl();

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("ჩაირთო კლიენტის ქსელის განახლების აპლიკაცია!");
            System.out.println("ნებისმიერ მომენტში შეიყვანეთ retry აპლიკაციის თავიდან გასაშვებად");
            System.out.println();

            System.out.println("ქსელის id:");
            String s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            int networkId;
            try {
                networkId = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                continue;
            }

            System.out.println("ქსელის დამატებითი id:");
            s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            int networkExtraId;
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
            productionNetworkService.updateProductionNetwork(networkId, networkExtraId);

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
