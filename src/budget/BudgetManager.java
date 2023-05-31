package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BudgetManager {
    private final static Scanner scanner = new Scanner(System.in);
    private final static String currency = "$";
    private final static List<Record> records = new ArrayList<>();

    public static void start() {
        readRecords();
        printBudget();
        printTotal();
    }

    private static void readRecords() {
        while (scanner.hasNextLine()) {
            Record record = createRecord(getInput());
            if (!record.item().isEmpty()) {
                records.add(record);
            }
        }
    }

    private static void printBudget() {
        records.forEach(record -> System.out.println(record.toString()));
    }

    private static Record createRecord(String input) {
        int currencyIndex = input.indexOf('$');
        try {
            String item = input.substring(0, currencyIndex);
            double cost = Double.parseDouble(input.substring(currencyIndex + 1));
            return new Record(item, currency, cost);
        } catch (NumberFormatException e) {
            System.out.println("Record: " + input + " has wrong currency format");
        }
        return new Record("", "", 0);
    }

    private static String getBalance() {
        return String.format("%.2f", records.stream().mapToDouble(Record::cost).sum());
    }

    private static String getCurrency() {
        return currency;
    }

    private static String getInput() {
        return scanner.nextLine();
    }

    private static void printTotal() {
        System.out.println("\nTotal: " + getCurrency() + "" + getBalance());
    }

}
