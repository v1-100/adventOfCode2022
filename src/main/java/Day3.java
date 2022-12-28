import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {

    String priority = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    File f;
    Integer prioSum;
    List<String> group = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Day3 day = new Day3();
        day.calculate();
    }
    public Day3() {
        this.f = new File("src/main/java/inputday3.txt");
        this.prioSum = 0;
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 3!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();

        while (line != null) {
            //partOne(line);
            group.clear();
            for (int i = 0; i < 3; i++) {
                group.add(line);
                line = bufferedReader.readLine();
            }
            partTwo(group);
        }
        System.out.println("End totalScore " + prioSum);

    }

    private void partTwo(List<String> group) {
        for (int i = 0; i < group.get(0).length(); i++) {
            char item = group.get(0).charAt(i);
            long oc = countOccurences(group.get(1), item);
            if (oc > 0) {
                long oc2 = countOccurences(group.get(2), item);
                if (oc2 > 0) {
                    int prio = priority.indexOf(item);
                    System.out.println(prio);
                    prioSum = prioSum + prio;
                    break;
                }
            }
        }
    }

    private void partOne(String line) {
        if (line.length() != 0) {
            String sack1 = line.substring(0, line.length() / 2);
            String sack2 = line.substring(line.length() / 2);
            for (int i = 0; i < sack1.length(); i++) {
                char item = sack1.charAt(i);
                long oc = countOccurences(sack2, item);
                if (oc > 0) {
                    int prio = priority.indexOf(item);
                    System.out.println(prio);
                    prioSum = prioSum + prio;
                    break;
                }
            }
            System.out.println(sack1 + " " + sack2);
        }
    }

    private static long countOccurences(String str, char ch) {
        return str.chars().filter(c -> c == ch).count();
    }

}
