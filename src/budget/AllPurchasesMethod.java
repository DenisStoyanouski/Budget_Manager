package budget;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static budget.BudgetManager.*;

public class AllPurchasesMethod implements SortMethod {

    @Override
    public void sort() {
        List<Record> recordList = new java.util.ArrayList<>(BudgetManager.getRecords().stream()
                .sorted(Comparator
                        .comparingDouble(Record::cost)
                        .thenComparing(Record::item))
                .toList());
        Collections.reverse(recordList);
        printListOfPurchases("ALL", recordList);
    }
}
