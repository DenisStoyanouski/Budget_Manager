package budget;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PurchasesLoader {

    private final static String filePath = "./" + File.separator + "purchases.txt";

    static List<Record> records = new ArrayList<>();

    static double balance;

    public static List<Record> loadRecords() {
        String[] source;
        try {
            source = readFileAsString().split("(?<=\\$\\d{1,}\\.\\d\\d)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String rec : source) {
            records.add(createRecord(rec.trim()));
        }
        System.out.println("\nPurchases were loaded!");
        return records;
    }

    private static String readFileAsString() throws IOException {
        String result = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] withBalance = result.split("(?<=\\d{1,}\\.\\d\\d)", 2);
        if (withBalance[0].matches("\\d+([.,])\\d+")) {
            balance = Double.parseDouble(withBalance[0].trim().replace(",", "."));
            return withBalance[1];
        }
        return result;
    }

    public static double getBalance() {
        return balance;
    }

    private static Record createRecord(String line) {
        String typeOfPurchases = line.substring(0, line.indexOf(" "));
        String item = line.substring(line.indexOf(" ") + 1, line.lastIndexOf("$") - 1);
        double cost = Double.parseDouble(line.substring(line.lastIndexOf("$") + 1).replace(",", "."));
        return new Record(typeOfPurchases, item, "$", cost);
    }
}
