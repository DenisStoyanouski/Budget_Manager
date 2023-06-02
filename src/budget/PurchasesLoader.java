package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PurchasesLoader {

    private final static String filePath = "./" + File.separator + "purchases.txt";

    private static List<Record> records;

    public static List<Record> loadRecords() {
        File file = new File(filePath);
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                Record record = createRecord(scanner.nextLine());
                records.add(record);
            }

        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return records;
    }

    private static Record createRecord(String line) {
        Record record;
        String[] separatedLine = line.split("\\s");
        String typeOfPurchases = separatedLine[0].trim();
        String item = String.join("", Arrays.copyOfRange(separatedLine, 1, separatedLine.length - 1));
        double cost = Double.parseDouble(separatedLine[separatedLine.length - 1].replace("$", "").trim());
        record = new Record(typeOfPurchases, item, "$", cost);
        return record;
    }
}
