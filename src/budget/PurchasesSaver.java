package budget;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PurchasesSaver {
    private final static String filePath = "./" + File.separator + "purchases.txt";

    public static void saveBudget(double balance, List<Record> records) {
        saveRecords(balance, records);

    }

    private static void saveRecords(double balance, List<Record> records) {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(String.valueOf(balance));
            records.forEach(record -> {
                try {
                    fw.write(record.toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}
