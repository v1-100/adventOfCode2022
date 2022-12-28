package Day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12 {
    File f;
    Heightmap heightmap;

    public static void main(String[] args) throws IOException {
        Day12 day = new Day12();
        day.calculate();
    }
    public Day12() {
        this.f = new File("src/main/java/Day12/inputday12.txt");
        this.heightmap = new Heightmap();
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 12!");
        initiate();
        ArrayList<Coordinate> path = new ArrayList<>();
        path.add(heightmap.startingPosition);

        System.out.println("---------- RESULT --------------");
        long t1 = System.currentTimeMillis();
        ExplorationResult explorationResult = heightmap.getPathToLocationBestSignal(path, "");
        System.out.println(explorationResult.path.size());
        //heightmap.printMap(explorationResult);
        System.out.println("--------------------------------");
        System.out.println("Part1 result : "+heightmap.shortestDistanceFromStartOfVisitedPosition.get(heightmap.locationBestSignal));
        long t2 = System.currentTimeMillis();
		System.out.println ("TEMPS=" + (t2-t1));
        System.out.println("--------------------------------");
        
        System.out.println("--------------------------------");
        t1 = System.currentTimeMillis();
        System.out.println("Part1 BFS algo result : "+ BFS.bfs(heightmap, heightmap.startingPosition, heightmap.locationBestSignal).size());
        t2 = System.currentTimeMillis();
		System.out.println ("TEMPS=" + (t2-t1));
        System.out.println("--------------------------------");        

        System.out.println("Part 2");    
        List<Coordinate> coordinates = heightmap.getPositionsOfValue(1);
        Integer min = Integer.MAX_VALUE;
        for(Coordinate coor : coordinates){
            List<Coordinate> newPath = BFS.bfs(heightmap, coor, heightmap.locationBestSignal);
            Integer size = newPath != null ? newPath.size() : Integer.MAX_VALUE;
            min = min > size ? size -1 : min;
        }
        System.out.println("Part 2 result : "+min);
        System.out.println("End ");
    }

    private void initiate() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

        String line = bufferedReader.readLine();
        while (line != null) {
            heightmap.setRowOfMap(line);

            System.out.println(line);
            line = bufferedReader.readLine();
        }
    }


}
