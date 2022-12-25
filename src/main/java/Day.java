import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day {
    File f;

    public Day() {
        this.f = new File("src/main/java/inputday.txt");
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day x!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();
        while (line != null) {

            line = bufferedReader.readLine();
        }
        
        System.out.println("End ");
    }

}
