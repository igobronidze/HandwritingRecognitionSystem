package ge.edu.tsu.hrs.control_panel.server;

import ge.edu.tsu.hrs.control_panel.server.processor.stringmatching.StringMatchingProcessor;

import java.util.Scanner;

public class StringMatchingTest {

    private static final StringMatchingProcessor stringMatchingProcessor = new StringMatchingProcessor();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("სატესტო სიტყვა:");
            String text = scanner.next();
            String result = stringMatchingProcessor.getNearestString(text);
            System.out.println(result);
            System.out.println("გსურთ გაგრძელება(true/false)");
            String next = scanner.next();
            if (!Boolean.valueOf(next)) {
                break;
            }
        }
    }
}
