package Day14;

import Day12.Coordinate;
import Day13.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;

public class Day14 {
    static Coordinate start = new Coordinate(500, 0);


    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        System.out.println("Hello calculate day 13!");
        Set<Coordinate> paths = initiate();
        Set<Coordinate> sands = new HashSet<>();

        printCave(paths, sands);
        
        Integer part = 2;

        while (true) {
            Coordinate newSandUnit = OneUnitOfSandFalls(paths, sands, part);
            if (newSandUnit != null) {
                sands.add(newSandUnit);
            } else {
                break;
            }
            System.out.println("\n");
            //printCave(paths, sands);
        }
        printCave(paths, sands);
        System.out.println("Units of sands : " + (part == 2 ? sands.size()+1 : sands.size()));
        System.out.println("End ");

    }

    private static Coordinate OneUnitOfSandFalls(Set<Coordinate> paths, Set<Coordinate> sands, Integer part) {
        Set<Coordinate> possibleDestination = getPossiblesDestination(start, paths, sands);
        Coordinate nextDest = null;
        while (!possibleDestination.isEmpty()){
            nextDest = possibleDestination.iterator().next();
            if(part == 1 && isSandFallsIntoAbyss(nextDest, paths)) return null;
            possibleDestination = getPossiblesDestination(nextDest, paths, sands);
        }
        return nextDest;
    }

    private static boolean isSandFallsIntoAbyss(Coordinate nextDest, Set<Coordinate> paths) {
        OptionalInt maxY = paths.stream().mapToInt(c -> c.y).max();
        if(maxY.getAsInt() <= nextDest.y){
            return true;
        }else {
            return false;
        }
    }


    private static Set<Coordinate> getPossiblesDestination(Coordinate origin, Set<Coordinate> paths, Set<Coordinate> sands) {
        Set<Coordinate> possibleDestination = new LinkedHashSet<>();
        possibleDestination.add(new Coordinate(origin.x, origin.y+1));
        possibleDestination.add(new Coordinate(origin.x-1, origin.y+1));
        possibleDestination.add(new Coordinate(origin.x+1, origin.y+1));
        Set<Coordinate> toRemove = new HashSet<>();
        
        int floorY = paths.stream().mapToInt(c -> c.y).max().getAsInt() + 2;
        
        for (Coordinate dest : possibleDestination) {
            if(paths.contains(dest) || sands.contains(dest) || dest.y == floorY){
                toRemove.add(dest);
            }
        }
        possibleDestination.removeAll(toRemove);
        return possibleDestination;
    }

    private static void printCave(Set<Coordinate> paths, Set<Coordinate> sands) {
        int minX = Math.min(paths.stream().mapToInt(c -> c.x).min().orElse(500),
                sands.stream().mapToInt(c -> c.x).min().orElse(500));
        int maxX = Math.max(paths.stream().mapToInt(c -> c.x).max().orElse(500),
                sands.stream().mapToInt(c -> c.x).max().orElse(500));
        int minY = Math.min(paths.stream().mapToInt(c -> c.y).min().orElse(0),
                                    sands.stream().mapToInt(c -> c.y).min().orElse(0));
        int maxY = Math.max(paths.stream().mapToInt(c -> c.y).max().orElse(0),
                sands.stream().mapToInt(c -> c.y).max().orElse(0));
        String line = "";

        for (int y = 0; y <= maxY +2; y++) {
            line += y + " ";
            for (int x = minX; x <= maxX; x++) {

                if (start.equals(new Coordinate(x, y))) {
                    line += "+ ";
                } else if (sands.contains(new Coordinate(x, y))) {
                    line += "o ";
                } else if (paths.contains(new Coordinate(x, y))) {
                    line += "# ";
                } else {
                    line += ". ";
                }
            }
            System.out.println(line);
            line = "";
        }
    }

    private static Set<Coordinate> initiate() throws IOException {
        Set<Coordinate> coordinateHashSet = new HashSet<>();

        File f = new File("src/main/java/Day14/inputday14.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

        String line = bufferedReader.readLine();
        while (line != null) {
            String[] currentPath = line.split(" -> ");
            for (int i = 1; i < currentPath.length; i++) {
                String[] startString = currentPath[i - 1].split(",");
                String[] endString = currentPath[i].split(",");
                Coordinate start = new Coordinate(Integer.parseInt(startString[0]), Integer.parseInt(startString[1]));
                Coordinate end = new Coordinate(Integer.parseInt(endString[0]), Integer.parseInt(endString[1]));
                coordinateHashSet.addAll(createPathBetween(start, end));
            }

            line = bufferedReader.readLine();
        }
        return coordinateHashSet;
    }

    private static List<Coordinate> createPathBetween(Coordinate start, Coordinate end) {
        List<Coordinate> list = new ArrayList<>();
        if (!Objects.equals(start.x, end.x)) {
            int xMin = start.x < end.x ? start.x : end.x;
            int xMax = start.x < end.x ? end.x : start.x;
            for (int i = xMin; i <= xMax; i++) {
                list.add(new Coordinate(i, start.y));
            }
        } else {
            int yMin = start.y < end.y ? start.y : end.y;
            int yMax = start.y < end.y ? end.y : start.y;
            for (int i = yMin; i < yMax; i++) {
                list.add(new Coordinate(start.x, i));
            }
        }
        return list;
    }


}

