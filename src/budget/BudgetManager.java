package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class BudgetManager {
    private final static Scanner scanner = new Scanner(System.in);
    private final static String currency = "$";
    private final static List<Record> records = new ArrayList<>();

    private static Double balance = 0d;

    public static void start() {
        while (true) {
            String menu = """
                    Choose your action:
                    1) Add income
                    2) Add purchase
                    3) Show list of purchases
                    4) Balance
                    0) Exit""";
            System.out.println(menu);
            switch (getInput()) {
                case "1" -> addIncome();
                case "2" -> addPurchase();
                case "3" -> printListOfPurchases();
                case "4" -> printBalance();
                case "0" -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
            }
        }
    }

    private static void addIncome() {
        System.out.println("\nEnter income:");
        String income = getInput();
        try {
            balance += Integer.parseInt(income);
            System.out.println("Income was added!\n");
        } catch (NullPointerException | NumberFormatException e) {
            System.out.println(e.getMessage());
            System.out.println("Wrong format. Use numbers!");
            addIncome();
        }
    }

    private static void addPurchase() {
        System.out.println("\nEnter purchase name:");
        String purchaseName = getInput();
        System.out.println("Enter its price:");
        double price = Double.parseDouble(getInput());
        Record record = new Record(purchaseName, currency, price);
        records.add(record);
    }

    private static void printListOfPurchases() {
        printBudget();
        printTotal();
    }

    private static void printBalance() {
        System.out.printf(Locale.US, "\nBalance: $%.2f\n\n", balance);

    }

    private static void printBudget() {
        if (records.isEmpty()) {
            System.out.println("\nThe purchase list is empty\n");
        } else {
            records.forEach(record -> System.out.println(record.toString()));
        }
    }

    /*private static Record createRecord(String input) {
        int currencyIndex = input.indexOf('$');
        try {
            String item = input.substring(0, currencyIndex).trim();
            double cost = Double.parseDouble(input.substring(currencyIndex + 1).trim());
            return new Record(item, currency, cost);
        } catch (NumberFormatException e) {
            System.out.println("Record: " + input + " has wrong currency format");
        }
        return new Record("", "", 0);
    }*/

    private static String getTotal() {
        return String.format(Locale.US, "%.2f", records.stream().mapToDouble(Record::cost).sum());
    }

    private static String getCurrency() {
        return currency;
    }

    private static String getInput() {
        return scanner.nextLine();
    }

    private static void printTotal() {
        System.out.printf(Locale.US, "\nTotal sum: " + getCurrency() + "" + getTotal() + "%n");
    }

}
