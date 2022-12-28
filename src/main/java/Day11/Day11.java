package Day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day11 {
    File f;
    List<Monkey> monkeyList;
    Monkey currentMonkey;
    Integer modulo;
    public static void main(String[] args) throws IOException {
        Day11 day = new Day11();
        day.calculate();
    }
    public Day11() {
        this.f = new File("src/main/java/Day11/inputday11.txt");
        this.monkeyList = new ArrayList<>();
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 11!");
        initiate();
        modulo = monkeyList.stream().map(m -> m.testDivisible).reduce(1, (a, b) -> a * b); // d�nominateur commun
        System.out.println(monkeyList);
        for (int i = 0; i < 10000; i++) {
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
        System.out.println(Long.valueOf(times.get(times.size()-1))*Long.valueOf(times.get(times.size()-2)));
    }

    private void doARound(Integer roundNumber) {
        
        for (int i = 0; i < monkeyList.size(); i++) {
            currentMonkey = getOrCreateMonkey(i);
            //System.out.println(currentMonkey);
            if(currentMonkey.items.isEmpty()) continue;
            do {
                Item item = currentMonkey.items.poll();
                currentMonkey.inspectItem++;
                //On change en Integer en Long le temp de r�duire le worryLevel
                Long newWorrylevel = 0L;
                switch (currentMonkey.operation) {
                    case "*":
                        newWorrylevel = Long.valueOf(getOperandValue(currentMonkey.operand1, item)) * Long.valueOf(getOperandValue(currentMonkey.operand2, item));
                        break;
                    case "+":
                        newWorrylevel = Long.valueOf(getOperandValue(currentMonkey.operand1, item)) + Long.valueOf(getOperandValue(currentMonkey.operand2, item));
                        break;
                }
                newWorrylevel = newWorrylevel % modulo ;// 3;
                item.worryLevel = newWorrylevel.intValue();
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
