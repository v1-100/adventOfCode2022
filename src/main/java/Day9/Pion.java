package Day9;

import java.util.ArrayList;
import java.util.Optional;
import java.util.OptionalInt;

public class Pion {
    String name;

    Coordinate<Integer, Integer> coordinate;
    Zone zone;
    ArrayList<Object> history;
    Coordinate<Integer, Integer> start;
    Integer rope;

    public Pion(String name, Coordinate<Integer, Integer> coordinate, Integer rope) {
        this.name = name;
        this.coordinate = coordinate;
        this.zone = new Zone(0, 0, rope);
        this.setZone();
        this.history = new ArrayList<Object>();
        this.history.add(coordinate);
        this.start = new Coordinate<>(0, 0);
    }

    public void setCoordinate(Coordinate<Integer, Integer> coordinate) {
        this.coordinate = coordinate;
    }

    public void printCoordinate() {
        System.out.println(this.name + " : " + this.coordinate.toString());
    }

    public void setZone() {
        this.zone.setZone(this.coordinate.x, this.coordinate.y);
    }
    public void setZone(Integer rope) {
        this.zone.setZone(this.coordinate.x, this.coordinate.y, rope);
    }
    public void saveCoordinate() throws CloneNotSupportedException {
        this.history.add(coordinate.clone());
    }

    public void printMap() {
        OptionalInt minX = history.stream().mapToInt(c -> (int) ((Coordinate) c).x).min();
        OptionalInt maxX = history.stream().mapToInt(c -> (int) ((Coordinate) c).x).max();
        OptionalInt minY = history.stream().mapToInt(c -> (int) ((Coordinate) c).y).min();
        OptionalInt maxY = history.stream().mapToInt(c -> (int) ((Coordinate) c).y).max();
        String line = "";
        minX = OptionalInt.of(minX.getAsInt() > 0 ? 0 : minX.getAsInt());
        minY = OptionalInt.of(minY.getAsInt() > 0 ? 0 : minY.getAsInt());
        for (int y = maxY.getAsInt(); y >= minY.getAsInt(); y--) {
            for (int x = minX.getAsInt(); x <= maxX.getAsInt(); x++) {
            
                if (start.equals(new Coordinate<>(x, y))) {
                    line = line + "s";
                } else if (history.indexOf(new Coordinate<>(x, y)) > -1) {
                    line = line + "#";
                } else {
                    line = line + ".";
                }
            }
            System.out.println(line);
            line = "";
        }
    }
}
