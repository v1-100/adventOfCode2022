import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day6 {
    File f;

    public Day6() {
        this.f = new File("src/main/java/inputday6.txt");
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 6!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();
        int nbUnique = 14;
        while (line != null) {
            for(int index = 0; index+nbUnique < line.length();index++){
                String marker = line.substring(index,index+nbUnique);
                if(isAllCaracUnique(marker)){
                    System.out.println(index+nbUnique);
                    break;
                }
            }
            line = bufferedReader.readLine();
        }


        System.out.println("End top ");

    }

    private boolean isAllCaracUnique(String marker) {
        for(int i = 0; i < marker.length(); i++){
            char cara = marker.charAt(i);
            if(marker.chars().filter( c -> c == cara).count() > 1){
                return false;
            }
        }
        System.out.println(marker);
        return true;
    }


}
