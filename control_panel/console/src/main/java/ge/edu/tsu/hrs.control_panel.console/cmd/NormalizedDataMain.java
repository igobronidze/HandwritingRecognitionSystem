package ge.edu.tsu.hrs.control_panel.console.cmd;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.GroupedNormalizedDataServiceImpl;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.NormalizedDataService;
import ge.edu.tsu.hrs.control_panel.service.normalizeddata.NormalizedDataServiceImpl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NormalizedDataMain {

    private static final NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();

    private static final GroupedNormalizedDataService groupedNormalizedDataService = new GroupedNormalizedDataServiceImpl();

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("ჩაირთო ნორმალიზებული ინფორმაციის ნახვის აპლიკაცია!");
            System.out.println("ნებისმიერ მომენტში შეიყვანეთ retry აპლიკაციის თავიდან გასაშვებად");
            System.out.println();

            System.out.println("ნორმალიზებული ინფორმაციის ჯგუფის id:");
            String s = scanner.nextLine();
            if (isRetry(s)) {
                continue;
            }
            int groupedNormalizedDataId = 0;
            try {
                groupedNormalizedDataId = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
                continue;
            }

            GroupedNormalizedData groupedNormalizedData = groupedNormalizedDataService.getGroupedNormalizedData(groupedNormalizedDataId);
            List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
            groupedNormalizedDatum.add(groupedNormalizedData);
            List<NormalizedData> normalizedDatum = normalizedDataService.getNormalizedDatum(groupedNormalizedDatum);
            if (normalizedDatum.size() == 0) {
                System.out.println("ნორმალიზებული ინფორმაცია არ მოიძებნა");
                continue;
            }
            System.out.println("სულ მოიძებნა " + normalizedDatum.size() + " ნორმალიზებული ინფორმაცია!");
            while (true) {
                System.out.println("გსურთ შემთხვევითი ნორმალიზებული ინფორმაციის ნახვა? (true/false)");
                s = scanner.nextLine();
                boolean next = Boolean.parseBoolean(s);
                if (next) {
                    System.out.println("სახელი: " + groupedNormalizedData.getName());
                    System.out.println("მინიმალური მნიშვნელობა: " + groupedNormalizedData.getMinValue());
                    System.out.println("მაქსიმალური მნიშვნელობა: " + groupedNormalizedData.getMaxValue());
                    System.out.println("სიმაღლე: " + groupedNormalizedData.getHeight());
                    System.out.println("სიგრძე: " + groupedNormalizedData.getWidth());
                    System.out.println("ნორმალიზების ტიპი: " + groupedNormalizedData.getNormalizationType());
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    Random random = new Random();
                    NormalizedData normalizedData = normalizedDatum.get(random.nextInt(normalizedDatum.size()));
                    for (int i = 0; i < groupedNormalizedData.getHeight(); i++) {
                        for (int j = 0; j < groupedNormalizedData.getWidth(); j++) {
                            System.out.print(formatter.format(normalizedData.getData()[i * groupedNormalizedData.getWidth() + j]) + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("სიმბოლო: " + normalizedData.getLetter());
                    System.out.println();
                } else {
                    break;
                }
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
