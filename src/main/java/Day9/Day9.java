package Day9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 {
    File f;
    Map map;
    Integer ropeSize;
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        Day9 day = new Day9();
        day.calculate();
    }
    public Day9() {
        Integer ropeSize = 9;
        this.f = new File("src/main/java/Day9/inputday9.txt");
        this.ropeSize = ropeSize;
        this.map = new Map(1, ropeSize);
    }


    public void calculate() throws IOException, CloneNotSupportedException {
        System.out.println("Hello calculate day 9!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();


        while (line != null) {
            char destination = line.split(" ")[0].charAt(0);
            Integer nbStep = Integer.parseInt(line.split(" ")[1]);
            for (int i = 0; i < nbStep; i++) {
                System.out.println("head " + map.head.coordinate.toString() + " | tail " + map.rope.get(ropeSize-1).coordinate.toString());
                map.move(map.head, destination);
                if (!map.isCoordinateInZone(map.head.coordinate, map.rope.get(0).zone)) {
                    Coordinate<Integer, Integer> nextCoord = map.getDestinationForSuiveur(map.head, map.rope.get(0));
                    map.move(map.rope.get(0), nextCoord);
                }
                for (int j = 1; j < ropeSize; j++) {
                    if (!map.isCoordinateInZone(map.rope.get(j-1).coordinate, map.rope.get(j).zone)) {
                        Coordinate<Integer, Integer> nextCoord = map.getDestinationForSuiveur(map.rope.get(j-1), map.rope.get(j));
                        map.move(map.rope.get(j), nextCoord);
                    }                    
                }

//        map.head.printMap();
//        System.out.println("-----------------------");
//        map.rope.get(ropeSize-1).printMap();
//        System.out.println("-----------------------");                
            }
            //System.out.println("map.tail.history : " +map.tail.history);
            
            line = bufferedReader.readLine();
        }
        
        map.head.printCoordinate();
        map.rope.get(ropeSize-1).printCoordinate();
        map.head.printMap();
        System.out.println("-----------------------");
        map.rope.get(ropeSize-1).printMap();
        System.out.println("-----------------------");
        Set point = map.rope.get(ropeSize-1).history.stream().map(c -> c).collect(Collectors.toSet());
        System.out.println("map.tail.history size : " +point.size());
        System.out.println("-----------------------");
        System.out.println("End ");
    }

}

