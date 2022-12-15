package Day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {

    File f;
    ArrayList<String> instructionsList;

    public Day10() throws IOException {
        this.f = new File("src/Day10/inputday10.txt");
        this.instructionsList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();
        while (line != null) {
            instructionsList.add(line);
            line = bufferedReader.readLine();
        }
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 10!");
        Program program = new Program(instructionsList);
        program.start();

        List<Integer> signalToSave = Arrays.asList(20, 60, 100, 140, 180, 220);
        Integer sum = 0;
        for (Integer l : signalToSave) {
            sum = sum + l * program.historyOfXByCycle.get(l-1);
        }

        System.out.println(program.history);      
        System.out.println(program.historyOfXByCycle);   
        System.out.println("End "+ sum);
    }
    
}
