package Day11;

public class Item <I, W> {
    I initialValue;
    W worryLevel;

    public Item(I initialValue, W worryLevel) {
        this.initialValue = initialValue;
        this.worryLevel = worryLevel;
    }

    public void setWorryLevel(W worryLevel) {
        this.worryLevel = worryLevel;
    }

    @Override
    public String toString() {
        return "Item{" +
                "w=" + worryLevel +
                '}';
    }
}
