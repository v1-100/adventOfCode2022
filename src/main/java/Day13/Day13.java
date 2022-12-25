package Day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13 {

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        System.out.println("Hello calculate day 13!");

        List<Pair> pairList = initiate();
        Integer sumOfIndiceInTheRightOrder = 0;
        String summary = "\nSummary :";

        for (int i = 0; i < pairList.size(); i++) {
            Pair currentPair = pairList.get(i);
            Boolean order = false;
            try {
                order = currentPair.isPairsInTheRightOrder(currentPair.leftList, currentPair.rightList);
            }catch (Exception e){
                System.out.println("DAY 13 "+ e);
            }
            System.out.println("###########################################");
            System.out.println(currentPair + " right order : " + order);
            System.out.println("###########################################\n");
            if (order) {
                sumOfIndiceInTheRightOrder += (i + 1);
                summary += "\n"+(i + 1)+ " : "+currentPair;
                System.out.println("sumOfIndiceInTheRightOrder : "+sumOfIndiceInTheRightOrder);
            }
        }


        System.out.println("pairList " +pairList);
        System.out.println(summary);
        System.out.println("sumOfIndiceInTheRightOrder : " + sumOfIndiceInTheRightOrder);
        System.out.println("End ");

    }

    private static List<Pair> initiate() throws IOException {
        List<Pair> pairList = new ArrayList<>();

        File f = new File("src/main/java/Day13/inputday13.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

        String line = bufferedReader.readLine();
        while (line != null) {
            if (line.length() != 0) {
                String left = line;
                line = bufferedReader.readLine();
                String right = line;
                Pair pair = new Pair(left, right);
                pairList.add(pair);
            }

            line = bufferedReader.readLine();

        }
        return pairList;
    }


}
