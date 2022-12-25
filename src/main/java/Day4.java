import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4 {
    File f;
    Integer pairs;

    public Day4() {
        this.f = new File("src/main/java/inputday4.txt");
        this.pairs = 0;
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 3!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();

        while (line != null) {
            String[] peer = line.split(",");
            String[] a = peer[0].split("-");
            String[] b = peer[1].split("-");
            Integer a1 = Integer.parseInt(a[0]);
            Integer a2 = Integer.parseInt(a[1]);
            Integer b1 = Integer.parseInt(b[0]);
            Integer b2 = Integer.parseInt(b[1]);       
            
            if((a1 <= b1 && a2 >= b2) || (b1 <= a1 && b2 >= a2) ||
                    (a2 >= b1 && a1 <= b2)
            ){
                System.out.println(line);
                pairs = pairs +1;
            }

            line = bufferedReader.readLine();
        }
        System.out.println("End totalScore " + pairs);

    }
}
