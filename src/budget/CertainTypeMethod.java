package budget;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static budget.BudgetManager.getInput;
import static budget.BudgetManager.printListOfPurchases;

public class CertainTypeMethod implements SortMethod{
    @Override
    public void sort() {
        String type = getType();
        List<Record> recordList= new java.util.ArrayList<>(BudgetManager.getRecords().stream()
                .sorted(Comparator
                        .comparingDouble(Record::cost)
                        .thenComparing(Record::item))
                .toList());
        Collections.reverse(recordList);
        printListOfPurchases(type, recordList);
    }

    private String getType() {
        String typeName;
        printMenu();
        try {
            int item = Integer.parseInt(getInput());
            typeName = BudgetManager.Types.values()[item - 1].name();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Enter a number from menu!");
            return getType();
        }
        return typeName;
    }

    private void printMenu() {
        System.out.println("\nChoose the type of purchase");
        Arrays.stream(BudgetManager.Types.values())
                .forEach(value -> System.out.println((value.ordinal() + 1) + ") " + value.name()));
    }
}
