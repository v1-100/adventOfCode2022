package Day15;

import Day12.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Sensor {
    Coordinate location;
    Coordinate beacon;
    Set<Coordinate> zone;

    public Sensor(Coordinate location, Coordinate beacon) {
        this.location = location;
        this.beacon = beacon;
        // Work with example but not with puzzle input :
        //this.zone = generateZone();
    }

    private int getRadiusOfSensor() {
        return Math.abs(location.x - beacon.x) + Math.abs(location.y - beacon.y);
    }
    
    public Boolean isInRangeOf(Coordinate coordinate){
        int distanceFromLocation = Math.abs(location.x - coordinate.x) + Math.abs(location.y - coordinate.y);
        return distanceFromLocation <= getRadiusOfSensor();
    }

    public boolean isYLineCrossZone(Integer y) {
        if (location.y - getRadiusOfSensor() <= y && location.y + getRadiusOfSensor() >= y) {
            return true;
        }
        return false;
    }

    public Set getXOfYLine(Integer y) {
        Set<Integer> xList = new HashSet<>();
        Integer rangeSize = getRadiusOfSensor() - Math.abs(location.y - y);

        for (int i = location.x - rangeSize; i <= location.x + rangeSize; i++) {
            xList.add(i);
        }

        return xList;
    }
    
    public Set<Coordinate> getNotIncludeEdgeOfZone(){
        int radius = getRadiusOfSensor();

        Set<Coordinate> coordinatesOfNotincludeEdge = new HashSet<>();

        for (int i = radius+1, j = 0; i >= 0; i--, j++) {
            coordinatesOfNotincludeEdge.addAll(getCoordinateOfEndsOfLine(location.x, location.y + j, i));
            coordinatesOfNotincludeEdge.addAll(getCoordinateOfEndsOfLine(location.x, location.y - j, i));
        }
        return coordinatesOfNotincludeEdge;      
    }
    
    private Set<Coordinate> getCoordinateOfEndsOfLine(Integer x, Integer y, int width) {
        Set<Coordinate> endsOfLine = new HashSet<>();
        endsOfLine.add(new Coordinate(x - width, y));
        endsOfLine.add(new Coordinate(x + width, y));
        return endsOfLine;
    }    

    private Set<Coordinate> generateZone() {
        int radius = getRadiusOfSensor();

        Set<Coordinate> coordinatesOfZone = new HashSet<>(generateLineOfZone(location.x, location.y, radius));

        for (int i = radius - 1, j = 1; i >= 0; i--, j++) {
            coordinatesOfZone.addAll(generateLineOfZone(location.x, location.y + j, i));
            coordinatesOfZone.addAll(generateLineOfZone(location.x, location.y - j, i));
        }
        return coordinatesOfZone;
    }

    private Set<Coordinate> generateLineOfZone(Integer x, Integer y, int width) {
        Set<Coordinate> coordinatesOfLineZone = new HashSet<>();
        coordinatesOfLineZone.add(new Coordinate(x, y));
        for (int i = 1; i <= width; i++) {
            coordinatesOfLineZone.add(new Coordinate(x - i, y));
            coordinatesOfLineZone.add(new Coordinate(x + i, y));
        }
        return coordinatesOfLineZone;
    }

    @Override
    public String toString() {
        return "\nSensor{" +
                "location=" + location +
                ", beacon=" + beacon +
                ", r=" + getRadiusOfSensor() +
                '}';
    }
}
