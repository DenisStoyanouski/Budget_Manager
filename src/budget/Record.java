package budget;

import java.util.Locale;

record Record(String typeOfPurchase, String item, String currency, double cost) {
    @Override
    public String item() {
        return item;
    }

    @Override
    public String currency() {
        return currency;
    }

    @Override
    public double cost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s %s%.2f", item, currency, cost);
    }

    public String toFile() {
        return String.format(Locale.US, "%s %s %s%.2f", typeOfPurchase, item, currency, cost);
    }
}
