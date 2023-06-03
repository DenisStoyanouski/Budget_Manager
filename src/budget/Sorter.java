package budget;

public class Sorter {
    private SortMethod analyzeMethod;

    public void setMethod(SortMethod analyzeMethod) {
        this.analyzeMethod = analyzeMethod;
    }

    public void sort() {
        this.analyzeMethod.sort();
    }
}
