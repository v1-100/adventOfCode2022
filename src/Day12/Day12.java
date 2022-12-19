package Day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day12 {
    File f;
    Heightmap heightmap;

    public Day12() {
        this.f = new File("src/Day12/inputday12sample.txt");
        this.heightmap = new Heightmap();
    }


    public void calculate() throws IOException {
        System.out.println("Hello calculate day 12!");
        initiate();
        ArrayList<Coordinate> path = new ArrayList<>();
        path.add(heightmap.startingPosition);
        String prefix = "";
        ExplorationResult explorationResult = heightmap.getPathToLocationBestSignal(path, prefix);
        System.out.println("---------- RESULT --------------");
        System.out.println(explorationResult.path.size());
        heightmap.printMap(explorationResult);
        System.out.println("--------------------------------");
        System.out.println("Part1 result : "+heightmap.shortestDistanceFromStartOfVisitedPosition.get(heightmap.locationBestSignal));
        System.out.println("--------------------------------");
        
//        List<Coordinate> lowPositions = heightmap.getPositionsOfValue(1);
//        lowPositions.retainAll(heightmap.shortestDistanceFromStartOfVisitedPosition.entrySet());
//        List<ExplorationResult> exploOfLowlevel = new ArrayList<>();
//        for (Coordinate position : lowPositions) {
//            heightmap.shortestDistanceFromStartOfVisitedPosition = new HashMap<>();
//            ArrayList<Coordinate> testPath = new ArrayList<>();
//            testPath.add(position);
//            exploOfLowlevel.add(heightmap.getPathToLocationBestSignal(testPath, ""));
//        }
//        System.out.println(exploOfLowlevel.stream().mapToInt(ex -> ex.path.size()).min().getAsInt() - 1);
        
        
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
