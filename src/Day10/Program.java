package Day10;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Program {
    Instruction<String, Integer> currentInstruction;
    Queue<Instruction> instructionList;
    Integer cycle;
    List<Integer> signalToSave;
    List<Integer> history;
    List<Integer> historyOfX;
    List<Integer> historyOfXByCycle;

    public Program(ArrayList<String> currentInstruction) {
        this.instructionList = new ArrayDeque<Instruction>();
        for (String line : currentInstruction) {
            String[] splitLine = line.split(" ");
            if(splitLine.length > 1){
                instructionList.add(new Instruction("noop", 0));
                instructionList.add(new Instruction("addx", Integer.parseInt(splitLine[1])));                
            }else{
                instructionList.add(new Instruction("noop", 0));
            }
        }
        this.cycle = 0;
        this.history = new ArrayList<>();
        this.historyOfX = new ArrayList<>();
        this.historyOfXByCycle = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Program{" +
                "cylinder=" + history +
                '}';
    }

    public void start() {
        historyOfX.add(1);
        historyOfXByCycle.add(1);
        Integer x = 1;
        Integer signal = 0;
        currentInstruction = instructionList.poll();
        while (currentInstruction != null) {
            cycle++;
            signal += currentInstruction.valeur;
            
            if(currentInstruction.instruction.equals("addx")) {
                x += signal;
                history.add(signal);
                historyOfX.add(x);
                signal = 0;
            }
            historyOfXByCycle.add(x);
            currentInstruction = instructionList.poll();
        }
    }
}
