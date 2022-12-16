package Day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Day11 {
    File f;
    List<Monkey> monkeyList;
    Monkey currentMonkey;

    public Day11() {
        this.f = new File("src/Day11/inputday11.txt");
        this.monkeyList = new ArrayList<>();
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 11!");
        initiate();
        System.out.println(monkeyList);
        for (int i = 0; i < 20; i++) {
            doARound(i);
        }
        
        System.out.println("----------------------------------");
        System.out.println("monkeyList " + monkeyList);
        getmultiplicationOfTwoMax();
        System.out.println("End");
    }

    private void getmultiplicationOfTwoMax() {
        List<Integer> times = new ArrayList<>();
        for (Monkey monkey :monkeyList) {
            times.add(monkey.inspectItem);
        }
        Collections.sort(times);
        System.out.println(times.get(times.size()-1)*times.get(times.size()-2));
    }

    private void doARound(Integer roundNumber) {
        
        for (int i = 0; i < monkeyList.size(); i++) {
            currentMonkey = getOrCreateMonkey(i);
            //System.out.println(currentMonkey);
            if(currentMonkey.items.isEmpty()) continue;
            do {
                Item item = currentMonkey.items.poll();
                currentMonkey.inspectItem++;
                Integer newWorrylevel = 0;
                switch (currentMonkey.operation) {
                    case "*":
                        newWorrylevel = getOperandValue(currentMonkey.operand1, item) * getOperandValue(currentMonkey.operand2, item);
                        break;
                    case "+":
                        newWorrylevel = getOperandValue(currentMonkey.operand1, item) + getOperandValue(currentMonkey.operand2, item);
                        break;
                }
                newWorrylevel = newWorrylevel / 3;
                item.worryLevel = newWorrylevel;
                if (newWorrylevel % currentMonkey.testDivisible == 0) {
                    getOrCreateMonkey((Integer) currentMonkey.throwTo.get(0)).items.add(item);
                } else {
                    getOrCreateMonkey((Integer) currentMonkey.throwTo.get(1)).items.add(item);
                }
            } while (!currentMonkey.items.isEmpty());

        }
//        System.out.println("----------------------------------");
//        System.out.println("End of Round "+roundNumber+" : " + monkeyList);
    }

    private Integer getOperandValue(String operand, Item<Integer, Integer> item) {
        switch (operand) {
            case "old":
                return item.worryLevel;
            default:
                return Integer.parseInt(operand);
        }
    }

    private void initiate() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();
        while (true) {
            //System.out.println(line);
            for (int i = 0; i < 7; i++) {
                if (line.indexOf("Monkey") == 0) {
                    currentMonkey = getOrCreateMonkey(Integer.parseInt(line.substring(7, 8)));
                } else if (line.contains("Starting")) {
                    String[] items = line.replaceAll("[^0-9,]+", "").split(",");
                    for (int j = 0; j < items.length; j++) {
                        Item newItem = new Item<Integer, Integer>(Integer.parseInt(items[j]), Integer.parseInt(items[j]));
                        currentMonkey.items.add(newItem);
                    }

                } else if (line.contains("Operation")) {
                    String[] operation = line.substring(19).split(" ");
                    currentMonkey.operand1 = operation[0];
                    currentMonkey.operation = operation[1];
                    currentMonkey.operand2 = operation[2];
                } else if (line.contains("Test")) {
                    currentMonkey.testDivisible = Integer.parseInt(line.replaceAll("[^0-9,]+", ""));
                } else if (line.contains("throw")) {
                    currentMonkey.throwTo.add(Integer.parseInt(line.replaceAll("[^0-9,]+", "")));
                }
                line = bufferedReader.readLine();
                if (line == null) break;
            }
            //System.out.println(currentMonkey.toString());
            if (line == null) break;
        }
    }

    private Monkey getOrCreateMonkey(int parseInt) {
        for (Monkey monkey : monkeyList) {
            if (monkey.numero == parseInt) {
                return monkey;
            }
        }
        Monkey newMonkey = new Monkey(parseInt);
        monkeyList.add(newMonkey);
        return newMonkey;
    }

}
