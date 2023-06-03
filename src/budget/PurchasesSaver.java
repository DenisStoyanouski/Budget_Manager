package budget;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PurchasesSaver {
    private final static String filePath = "./" + File.separator + "purchases.txt";

    public static void saveRecords(List<Record> records) {
        try (FileWriter fw = new FileWriter(filePath)) {
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
