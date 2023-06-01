package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class BudgetManager {
    private final static Scanner scanner = new Scanner(System.in);
    private final static String currency = "$";
    private final static List<Record> records = new ArrayList<>();

    private static Double balance;

    public static void start() {
        while (true) {
            String menu = """
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                0) Exit""";
            switch(getInput()) {
                case "1" -> addIncome();
            }
        }


        /*readRecords();
        printBudget();
        printTotal();*/
    }

    private static void addIncome() {
        System.out.println("Enter income:");
        try {
            balance += Double.parseDouble(getInput());
            System.out.println("Income was added!");
        } catch (NullPointerException|NumberFormatException e) {
            System.out.println("Wrong format. Use numbers!");
            addIncome();
        }


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
            String item = input.substring(0, currencyIndex).trim();
            double cost = Double.parseDouble(input.substring(currencyIndex + 1).trim());
            return new Record(item, currency, cost);
        } catch (NumberFormatException e) {
            System.out.println("Record: " + input + " has wrong currency format");
        }
        return new Record("", "", 0);
    }

    private static String getBalance() {
        return String.format(Locale.US, "%.2f", records.stream().mapToDouble(Record::cost).sum());
    }

    private static String getCurrency() {
        return currency;
    }

    private static String getInput() {
        return scanner.nextLine();
    }

    private static void printTotal() {
        System.out.printf(Locale.US, "\nTotal: " + getCurrency() + "" + getBalance() + "%n");
    }

}
