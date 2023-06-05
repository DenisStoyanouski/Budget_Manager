package budget;

import java.util.*;

import static budget.BudgetManager.*;

public class ByTypeMethod implements SortMethod {
    @Override
    public void sort() {
        print(getSumsOfTypes().stream()
                .sorted(Comparator.comparingDouble(SumOfType::value).reversed())
                .toList());
    }

    private List<SumOfType> getSumsOfTypes() {
        List<SumOfType> sumsOfTypes = new ArrayList<>();
        for (var type : BudgetManager.Types.values()) {
            double sum = getRecords().stream()
                    .filter(record -> Objects.equals(record.typeOfPurchase(), type.name()))
                    .mapToDouble(Record::cost)
                    .sum();
            sumsOfTypes.add(new SumOfType(type.name().toLowerCase().replaceFirst("^.", type.name().substring(0, 1)), sum));
        }
        return sumsOfTypes;
    }

    private void print(List<SumOfType> sumsOfTypes) {
        sumsOfTypes.forEach(sum -> System.out.printf("%s - $%.2f\n", sum.typeName, sum.value));
    }

    private record SumOfType(String typeName, double value) {
    }
}
