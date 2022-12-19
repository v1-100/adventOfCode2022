package Day12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Heightmap implements Cloneable {
    List<List<Integer>> map;
    Coordinate startingPosition;
    Coordinate locationBestSignal;
    HashMap<Coordinate, Integer> shortestDistanceFromStartOfVisitedPosition;


    public Heightmap() {
        this.map = new ArrayList<>();
        this.shortestDistanceFromStartOfVisitedPosition = new HashMap<>();
    }

    public void setRowOfMap(String line) {
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == 'S') {
                row.add(getLetterIndex('a'));
                startingPosition = new Coordinate(i, map.size());
            } else if (line.charAt(i) == 'E') {
                row.add(getLetterIndex('z'));
                locationBestSignal = new Coordinate(i, map.size());
            } else {
                row.add(getLetterIndex(line.charAt(i)));
            }
        }
        map.add(row);
    }

    synchronized void setIfShortestPath() {

    }

    public static int getLetterIndex(char letter) {
        letter = Character.toLowerCase(letter);
        int index = letter - 'a' + 1;
        return index;
    }

    public synchronized ExplorationResult getPathToLocationBestSignal(ArrayList<Coordinate> path, String prefix) {
        prefix += "\t";
//        System.out.println(prefix + path.get(path.size() - 1));
        if (path.contains(locationBestSignal)) throw new RuntimeException("Ne doit pas arriver");

        Coordinate currentPosition = path.get(path.size() - 1);

        ExplorationResult explorationResult = new ExplorationResult();
        explorationResult.path = path;

        List<Coordinate> possibleMoves = getPossibleMoveFromPosition(currentPosition);
        // Mouvement possible depuis la position
        possibleMoves = possibleMoves.stream().filter(m -> {
            Integer destinationHeight = getValueOfPosition(m);
            Integer currentHeight = getValueOfPosition(currentPosition);
            return destinationHeight <= currentHeight + 1 && !path.contains(m);
        }).collect(Collectors.toList());

        //Collections.sort(possibleMoves);

        List<ExplorationResult> resultsOfExploration = new ArrayList<>();
        if (!possibleMoves.isEmpty()) {
            for (Coordinate move : possibleMoves) {
                ArrayList<Coordinate> pathClone = (ArrayList<Coordinate>) path.clone();
                pathClone.add(move);

                if (move.equals(locationBestSignal)) {
                    isThereAShortestPathToThisPosition(move, pathClone.size() - 1);
                    ExplorationResult foundExpl = new ExplorationResult();
                    foundExpl.foundE = true;
                    foundExpl.path = pathClone;
//                    System.out.println("-----------------------------------------");
//                    System.out.println(prefix + foundExpl);
//                    printMap(foundExpl);
//                    System.out.println(prefix + pathClone.size());
                    resultsOfExploration.add(foundExpl);
                } else if (isThereAShortestPathToThisPosition(move, pathClone.size() - 1)) {
                    ExplorationResult tooLongExpl = new ExplorationResult();
                    explorationResult.path = pathClone;
//                    System.out.println(prefix + "too long " + pathClone.get(pathClone.size() - 1) + " : " + (pathClone.size() - 1));
                    resultsOfExploration.add(tooLongExpl);
                } else {
//                    System.out.println(prefix + "getPathToLocationBestSignal" + move);
                    resultsOfExploration.add(getPathToLocationBestSignal(pathClone, prefix));
                }
            }

            if (resultsOfExploration.size() != possibleMoves.size()) throw new RuntimeException("POURQUOI ?");

            ExplorationResult temp = null;
            for (ExplorationResult res : resultsOfExploration) {
                if (res.foundE) {
//                    System.out.println(prefix + "Choose? " + (res.path.size() - 1));
                    if (temp == null || temp.path.size() > res.path.size()) {
//                        System.out.println(prefix + "Choosen " + (res.path.size() - 1));
                        temp = res;
                    }
                }
            }
            explorationResult = temp != null ? temp : explorationResult;
        }

        return explorationResult;
    }

    private synchronized Boolean isThereAShortestPathToThisPosition(Coordinate currentPosition, Integer distanceFromStart) {
        Integer alreadyKnownDistanceFromStart = null;
        if (shortestDistanceFromStartOfVisitedPosition.containsKey(currentPosition)) {
            alreadyKnownDistanceFromStart = shortestDistanceFromStartOfVisitedPosition.get(currentPosition);
        } else {
            shortestDistanceFromStartOfVisitedPosition.put(currentPosition, distanceFromStart);
            return false;
        }
        if (alreadyKnownDistanceFromStart != null && alreadyKnownDistanceFromStart > distanceFromStart) {
            shortestDistanceFromStartOfVisitedPosition.replace(currentPosition, distanceFromStart);
            return false;
        }
        return true;
    }

    private Integer getValueOfPosition(Coordinate coordinate) {
        return map.get(coordinate.y).get(coordinate.x);
    }

    private List<Coordinate> getPossibleMoveFromPosition(Coordinate currentPosition) {
        Integer x = currentPosition.x;
        Integer y = currentPosition.y;
        int maxX = map.get(0).size() - 1;
        int maxY = map.size() - 1;
        List<Coordinate> possibleMoves = new ArrayList<>();

        possibleMoves.add(new Coordinate(x, y + 1));
        possibleMoves.add(new Coordinate(x, y - 1));
        possibleMoves.add(new Coordinate(x + 1, y));
        possibleMoves.add(new Coordinate(x - 1, y));
        return possibleMoves.stream().filter(coordinate -> coordinate.y >= 0 && coordinate.x >= 0
                && coordinate.y <= maxY && coordinate.x <= maxX).collect(Collectors.toList());
    }

    public void printMap(ExplorationResult explorationResult) {
        int maxX = map.get(0).size();
        int maxY = map.size();
        String line = "";
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (explorationResult.path.contains(new Coordinate(x, y))) {
                    int position = explorationResult.path.indexOf(new Coordinate(x, y));
                    line = line + "| " + (position > 9 ? position : position + " ");
                } else {
                    line = line + "| . ";
                }
            }
            System.out.println(line);
            line = "";
        }
    }

    public List<Coordinate> getPositionsOfValue(Integer i) {
        int maxX = map.get(0).size();
        int maxY = map.size();
        List<Coordinate> list = new ArrayList<>();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (map.get(y).get(x) == i){
                    list.add(new Coordinate(x, y));
                }
            }
        }
        return list;
    }
}
