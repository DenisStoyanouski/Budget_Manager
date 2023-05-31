package budget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BudgetManager {
    private final static Scanner scanner = new Scanner(System.in);

    private final static List<Record> records = new ArrayList<>();

    public static void start() {
        readRecords();
    }

    private static void readRecords() {
        while (scanner.hasNextLine()) {
            records.add(createRecord(getInput()));
        }
        System.out.println("Total:" + getCurrency() + getBalance());
    }

    private static Record createRecord(String input) {
        // TODO: 31.05.2023 add process input
        return new Record("Item", "$", 56);
    }

    private static int getBalance() {
        return records.stream().mapToInt(record -> record.cost).sum();
    }

    private static String getCurrency() {
        return records.get(0).currency;
    }

    private static String getInput() {
        return scanner.nextLine();
    }

    public record Record (String item, String currency, int cost) {}
}
