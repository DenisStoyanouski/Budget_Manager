package budget;

import org.w3c.dom.ls.LSOutput;

import java.util.*;


public class BudgetManager {
    private final static Scanner scanner = new Scanner(System.in);
    private final static String currency = "$";

    private final static String[] typeOfPurchase = {"", "Food", "Clothes", "Entertainment", "Other", "All"};
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
            System.out.println("\n" + menu);

            switch (getInput()) {
                case "1" -> addIncome();
                case "2" -> addPurchase();
                case "3" -> showListOfPurchases();
                case "4" -> printBalance();
                case "0" -> {
                    System.out.println("\nBye!");
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
        boolean back = false;
        while (!back) {
            String menu = """
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back""";
            System.out.println(menu);
            Record record = null;
            int item = Integer.parseInt(getInput().trim());
            switch (item) {
                case 1, 2, 3, 4 -> record = createRecord(typeOfPurchase[item]);
                case 5 -> back = true;
                default -> System.out.println("Enter number from menu!\n");
            }
            if (record != null) {
                records.add(record);
                System.out.println("Purchase was added!\n");
                changeBalance(record.cost());
            }
        }
    }

    private static void changeBalance(double price) {
        balance -= price;
        if (balance < 0) {
            balance = 0d;
        }
    }

    private static void printBalance() {
        System.out.printf(Locale.US, "\nBalance: $%.2f\n\n", balance);

    }

    private static void showListOfPurchases() {
        boolean back = false;
        if (records.isEmpty()) {
            System.out.println("\nThe purchase list is empty\n");
            return;
        }
        while(!back) {
            String menu = """
                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back""";
            System.out.println("\n" + menu);
            int item = Integer.parseInt(getInput().trim());
            switch (item) {
                case 1, 2, 3, 4, 5 -> printListOfPurchases(typeOfPurchase[item]);
                case 6 -> back = true;
                default -> System.out.println("Enter number from menu!\n");
            }
        }
    }

    private static void printListOfPurchases(String typeOfPurchase) {
        System.out.println("\n" + typeOfPurchase + ":");
        List<Record> records = getListOfPurchases(typeOfPurchase);
        if (!records.isEmpty()) {
            records.forEach(System.out::println);
            printTotal(typeOfPurchase);
        } else {
            System.out.println("The purchase list is empty!\n");
        }

    }

    private static List<Record> getListOfPurchases(String typeOfPurchase) {
        if ("All".equals(typeOfPurchase)) {
            return records;
        }
        return records.stream()
                .filter(record -> Objects.equals(record.typeOfPurchase(), typeOfPurchase))
                .toList();
    }

    private static Record createRecord(String typeOfPurchase) {
        System.out.println("\nEnter purchase name:");
        String purchaseName = getInput();
        System.out.println("Enter its price:");
        double price = Double.parseDouble(getInput());
        return new Record(typeOfPurchase, purchaseName, currency, price);
    }

    private static String getCurrency() {
        return currency;
    }

    private static String getInput() {
        return scanner.nextLine();
    }

    private static void printTotal(String typeOfPurchase) {
        String total = "";
        if ("All".equals(typeOfPurchase)) {
            total = String.format(Locale.US, "%.2f", records.stream().mapToDouble(Record::cost).sum());
        } else {
            total = String.format(Locale.US, "%.2f",
                    records.stream()
                            .filter(record -> Objects.equals(record.typeOfPurchase(), typeOfPurchase))
                            .mapToDouble(Record::cost)
                            .sum());
        }
        System.out.printf(Locale.US, "Total sum: " + getCurrency() + "" + total + "%n%n");
    }
}
