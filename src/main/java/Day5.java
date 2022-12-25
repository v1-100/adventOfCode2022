import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
    File f;
    ArrayList<ArrayList<String>> cargo = new ArrayList<>();
    String top;

    public Day5() {
        this.f = new File("src/main/java/inputday5.txt");
        for (int i = 0; i < 10; i++) {
            cargo.add(new ArrayList());
        }
        this.top = "";
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 5!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();

        while (line != null) {

            if (line.indexOf("[") > -1) {
                initiateCrates(line);
                System.out.println(cargo);
            }
            if (line.contains("move")) {
                Integer quantity = Integer.parseInt(line.substring(0, line.indexOf("from")).replaceAll("[^0-9]", ""));
                Integer origin = Integer.parseInt(line.substring(line.indexOf("from"), line.indexOf("to")).replaceAll("[^0-9]", ""));
                Integer destination = Integer.parseInt(line.substring(line.indexOf("to")).replaceAll("[^0-9]", ""));
                move9001(quantity, origin, destination);
            }

            line = bufferedReader.readLine();
        }
        System.out.println(cargo);
        for (List crate : cargo) {
            if (crate.size() > 0) {
                top = top + crate.get(0);
            } else top = top + " ";
        }
        System.out.println("End top " + top);

    }

    private void move(Integer quantity, Integer origin, Integer destination) {

        for (int i = 0; i < quantity; i++) {
            System.out.println(quantity + "/" + origin + "/" + destination);
            if (cargo.get(origin - 1).size() > 0) {
                System.out.println(cargo.get(origin - 1).get(0));
                cargo.get(destination - 1).add(0, cargo.get(origin - 1).get(0));
                cargo.get(origin - 1).remove(0);
                System.out.println(cargo);
            }
        }
    }

    private void move9001(Integer quantity, Integer origin, Integer destination) {
        System.out.println(quantity + "/" + origin + "/" + destination);
        for (int i = quantity; 0 < i; i--) {
            cargo.get(destination - 1).add(0, cargo.get(origin - 1).get(i-1));
        }
        for (int j = 0; j < quantity; j++) {
            cargo.get(origin - 1).remove(0);
        }
        System.out.println(cargo);

    }

    private void initiateCrates(String line) {
        System.out.println("taille " + line.length());
        int index = 0;
        for (int i = 0; i < line.length(); ) {
            String crate = i + 4 < line.length() ? line.substring(i, i + 4).replace("[", "").replace("]", "").replace(" ", "")
                    : line.substring(i).replace("[", "").replace("]", "").replace(" ", "");
            if (crate.length() > 0) cargo.get(index).add(crate);
            index++;
            i = i + 4;
        }
//        String crate1 = line.substring(0, 4).replace("[", "").replace("]", "").replace(" ", "");
//        if (crate1.length() > 0) cargo.get(0).add(crate1);
//        String crate2 = line.substring(4, 8).replace("[", "").replace("]", "").replace(" ", "");
//        if (crate2.length() > 0) cargo.get(1).add(crate2);
//        String crate3 = line.substring(8, 11).replace("[", "").replace("]", "").replace(" ", "");
//        if (crate3.length() > 0) cargo.get(2).add(crate3);
    }
}
