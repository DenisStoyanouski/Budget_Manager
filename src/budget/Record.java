package budget;

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
        return String.format("%s %s%.2f", item, currency, cost);
    }
}
