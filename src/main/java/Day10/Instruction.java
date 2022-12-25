package Day10;

public class Instruction <I, V> {
    I instruction;
    V valeur;
    public Instruction(I instruction, V valeur) {
        this.instruction = instruction;
        this.valeur = valeur;
    }
}
