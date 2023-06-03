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

    private static List<Record> records = new ArrayList<>();
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
                    7) Analyze (Sort)
                    0) Exit""";
            System.out.println("\n" + menu);

            switch (getInput()) {
                case "1" -> addIncome();
                case "2" -> addPurchase();
                case "3" -> showListOfPurchases();
                case "4" -> printBalance();
                case "5" -> PurchasesSaver.saveBudget(balance, records);
                case "6" -> {
                    records = PurchasesLoader.loadRecords();
                    balance = PurchasesLoader.getBalance();
                }
                case "7" -> analyze();
                case "0" -> {
                    System.out.println("\nBye!");
                    System.exit(0);
                }
            }
        }
    }

    private static void analyze() {
        boolean back = false;
        while (!back) {
            Sorter sorter = new Sorter();
            String sorterMenu = """
                How do you want to sort?
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back
                """;
            System.out.println(sorterMenu);
            switch (getInput().trim()) {
                case "1" -> {
                    sorter.setMethod(new AllPurchasesMethod());
                    sorter.sort();
                }
                case "2" -> sorter.setMethod(new ByTypeMethod());
                case "3" -> sorter.setMethod(new CertainTypeMethod());
                case "4" -> back = true;
            }
        }
    }

    public static List<Record> getRecords() {
        return records;
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
                records.add(record);
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
        if (records.isEmpty()) {
            System.out.println("\nThe purchase list is empty\n");
            return;
        }
        while (!back) {
            printShowMenu();
            int item = Integer.parseInt(getInput().trim());
            switch (item) {
                case 1, 2, 3, 4, 5 -> printListOfPurchases(ShowMenuItems.values()[item - 1].name(), records);
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

    static void printListOfPurchases(String typeOfPurchase, List<Record> recordList) {
        List<Record> recordsOfType = getListOfPurchases(typeOfPurchase, recordList);
        if (!recordsOfType.isEmpty()) {
            System.out.println("\n" + typeOfPurchase + ":");
            recordsOfType.forEach(System.out::println);
            printTotal(typeOfPurchase, recordList);
        } else {
            System.out.println("The purchase list is empty!\n");
        }
    }

    private static List<Record> getListOfPurchases(String typeOfPurchase, List<Record> recordList) {
        if ("ALL".equals(typeOfPurchase)) {
            return recordList;
        }
        return recordList.stream()
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
        return records.stream().mapToDouble(Record::cost).sum();
    }

    static void printTotal(String typeOfPurchase, List<Record> currentRecords) {
        String total;
        if ("ALL".equals(typeOfPurchase)) {
            total = String.format(Locale.US, "%.2f", currentRecords.stream().mapToDouble(Record::cost).sum());
        } else {
            total = String.format(Locale.US, "%.2f",
                    currentRecords.stream()
                            .filter(record -> Objects.equals(record.typeOfPurchase(), typeOfPurchase))
                            .mapToDouble(Record::cost)
                            .sum());
        }
        System.out.printf(Locale.US, "Total sum: " + getCurrency() + "" + total + "%n");
    }
}
