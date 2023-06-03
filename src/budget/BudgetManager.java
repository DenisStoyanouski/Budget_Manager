package budget;

import java.util.*;


public class BudgetManager {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static String CURRENCY = "$";

    private enum AddMenuItems {
        FOOD,
        CLOTHES,
        ENTERTAINMENT,
        OTHER,
        BACK
    }

    private enum ShowMenuItems {
        FOOD,
        CLOTHES,
        ENTERTAINMENT,
        OTHER,
        ALL,
        BACK
    }

    private static List<Record> RECORDS = new ArrayList<>();
    private static double balance = 0d;

    public static void start() {
        while (true) {
            String menu = """
                    Choose your action:
                    1) Add income
                    2) Add purchase
                    3) Show list of purchases
                    4) Balance
                    5) Save
                    6) Load
                    0) Exit""";
            System.out.println("\n" + menu);

            switch (getInput()) {
                case "1" -> addIncome();
                case "2" -> addPurchase();
                case "3" -> showListOfPurchases();
                case "4" -> printBalance();
                case "5" -> PurchasesSaver.saveBudget(balance, RECORDS);
                case "6" -> {
                    RECORDS = PurchasesLoader.loadRecords();
                    balance = PurchasesLoader.getBalance();
                }
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
            System.out.println("Wrong format. Use numbers!");
            addIncome();
        }
    }

    private static void addPurchase() {
        boolean back = false;
        while (!back) {
            printAddMenu();
            Record record = null;
            int item = Integer.parseInt(getInput().trim());
            switch (item) {
                case 1, 2, 3, 4 -> record = createRecord(AddMenuItems.values()[item - 1].name());
                case 5 -> back = true;
                default -> System.out.println("Enter number from menu!\n");
            }
            if (record != null) {
                RECORDS.add(record);
                System.out.println("Purchase was added!\n");
                changeBalance(record.cost());
            }
        }
    }

    private static void printAddMenu() {
        System.out.println("\nChoose the type of purchase");
        Arrays.stream(AddMenuItems.values())
                .forEach(value -> System.out.println((value.ordinal() + 1) + ") " + value.name()));
    }

    private static void changeBalance(double price) {
        balance -= price;
        if (balance < 0) {
            balance = 0d;
        }
    }

    private static void printBalance() {
        System.out.printf(Locale.US, "\nBalance: $%.2f\n", balance);
    }

    private static void showListOfPurchases() {
        boolean back = false;
        if (RECORDS.isEmpty()) {
            System.out.println("\nThe purchase list is empty\n");
            return;
        }
        while (!back) {
            printShowMenu();
            int item = Integer.parseInt(getInput().trim());
            switch (item) {
                case 1, 2, 3, 4, 5 -> printListOfPurchases(ShowMenuItems.values()[item - 1].name());
                case 6 -> back = true;
                default -> System.out.println("Enter a number from menu!\n");
            }
        }
    }

    private static void printShowMenu() {
        System.out.println("\nChoose the type of purchase");
        Arrays.stream(ShowMenuItems.values())
                .forEach(value -> System.out.println((value.ordinal() + 1) + ") " + value.name()));
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
        if ("ALL".equals(typeOfPurchase)) {
            return RECORDS;
        }
        return RECORDS.stream()
                .filter(record -> Objects.equals(record.typeOfPurchase(), typeOfPurchase))
                .toList();
    }

    private static Record createRecord(String typeOfPurchase) {
        System.out.println("\nEnter purchase name:");
        String purchaseName = getInput();
        System.out.println("Enter its price:");
        double price = Double.parseDouble(getInput());
        return new Record(typeOfPurchase, purchaseName, CURRENCY, price);
    }

    private static String getCurrency() {
        return CURRENCY;
    }

    private static String getInput() {
        return SCANNER.nextLine();
    }

    private static double getAllTotal() {
        return RECORDS.stream().mapToDouble(Record::cost).sum();
    }

    private static void printTotal(String typeOfPurchase) {
        String total;
        if ("ALL".equals(typeOfPurchase)) {
            total = String.format(Locale.US, "%.2f", RECORDS.stream().mapToDouble(Record::cost).sum());
        } else {
            total = String.format(Locale.US, "%.2f",
                    RECORDS.stream()
                            .filter(record -> Objects.equals(record.typeOfPurchase(), typeOfPurchase))
                            .mapToDouble(Record::cost)
                            .sum());
        }
        System.out.printf(Locale.US, "Total sum: " + getCurrency() + "" + total + "%n");
    }
}
