package Day11;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Monkey {
    Integer numero;
    
    Queue<Item> items;
    String operand1, operation, operand2;
    Integer testDivisible;
    List throwTo;
    Integer inspectItem;

    public Monkey(Integer numero) {
        this.numero = numero;
        this.items = new ArrayDeque<Item>();
        this.throwTo = new ArrayList<>();
        this.inspectItem = 0;
    }

    @Override
    public String toString() {
        return "\nMonkey " +numero +
                "\n\t inspectItem=" + inspectItem +
                "\n\t items=" + items ;
    }
}
