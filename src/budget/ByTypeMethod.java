package budget;

import java.util.*;

import static budget.BudgetManager.*;

public class ByTypeMethod implements SortMethod {
    @Override
    public void sort() {
        System.out.println("Types:");
        for (var type : BudgetManager.Types.values()) {
            double sum = getRecords().stream()
                    .filter(record -> Objects.equals(record.typeOfPurchase(), type.name()))
                    .mapToDouble(Record::cost)
                    .sum();
            System.out.println(type + " - $" + sum);
        }
        printTotal("ALL", getRecords());
    }


}
