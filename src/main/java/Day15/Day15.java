package Day15;

import Day12.Coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class Day15 {

    Set<Sensor> sensors;
    Set<Coordinate> foundBeacon;
    String fileLocation;
    Boolean test;
    Integer yLine;
    Integer maxLarger;

    public static void main(String[] args) throws IOException {
        Day15 day = new Day15(false);
        System.out.println(day.sensors);
        System.out.println("");
        //day.printCave();

        System.out.println("Part 1 ------------------------");
        Set<Integer> xList = new HashSet<>();
        for (Sensor sensor : day.sensors) {
            if (sensor.isYLineCrossZone(day.yLine)) {
                xList.addAll(sensor.getXOfYLine(day.yLine));
            }
        }
        xList = xList.stream().filter(x -> !day.foundBeacon.stream().filter(c -> c.y.equals(day.yLine))
                .map(c -> c.x).anyMatch(n -> Objects.equals(n, x))).collect(Collectors.toSet());
        System.out.println("Part 1 : " + xList.size());
        System.out.println("\nPart 2 ------------------------");
        long t1 = System.currentTimeMillis();

        Coordinate distressBeacon = day.walkTheEdgeOfZones();
        System.out.println("distressBeacon : " + distressBeacon);
        
        BigDecimal resultat = new BigDecimal(distressBeacon.x).multiply(new BigDecimal(4_000_000)).add(new BigDecimal(distressBeacon.y));
        System.out.println("Part 2 Interger: " + (distressBeacon.x * 4_000_000 + distressBeacon.y));
        System.out.println("Part 2 BigDecimal: " + resultat);
        
        long t2 = System.currentTimeMillis();
		System.out.println ("Part 2 durée " + (t2-t1) + " ms");
        System.out.println("End");
    }

    private Coordinate walkTheEdgeOfZones() {
        for (Sensor sensor : sensors) {
            Set<Coordinate> edge = sensor.getNotIncludeEdgeOfZone();
            edge = edge.stream().filter(c -> c.x >= 0 && c.x <= this.maxLarger && c.y >= 0 && c.y <= this.maxLarger).collect(Collectors.toSet());
            //this.printEdge(edge);
            for (Coordinate coordinate : edge) {
                if (!isCoordinateInRangeOfASensor(coordinate)) return coordinate;
            }
        }
        return null;
    }

    private boolean isCoordinateInRangeOfASensor(Coordinate coordinate) {
        for (Sensor sensor : this.sensors) {
            if (sensor.isInRangeOf(coordinate)) {
                return true;
            }
        }
        return false;
    }

    private Coordinate getDistressBeaconCoordinate() {

        for (int y = 0; y < this.maxLarger; y++) {
            for (int x = 0; x < this.maxLarger; x++) {
                boolean coordinateInRangeOfAtLeastOneSensor = false;
                for (Sensor sensor : this.sensors) {
                    if (sensor.isInRangeOf(new Coordinate(x, y))) {
                        coordinateInRangeOfAtLeastOneSensor = true;
                        break;
                    }
                }
                if (!coordinateInRangeOfAtLeastOneSensor) {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    public Day15(Boolean test) throws IOException {
        this.fileLocation = test ? "src/main/java/Day15/inputday15sample.txt" : "src/main/java/Day15/inputday15.txt";
        this.yLine = test ? 10 : 2000000;
        this.maxLarger = test ? 20 : 4000000;
        this.sensors = initiate();
        this.foundBeacon = sensors.stream().map(s -> s.beacon).collect(Collectors.toSet());

    }

    private Set<Sensor> initiate() throws IOException {
        Set<Sensor> sensorSet = new HashSet<>();
        File f = new File(this.fileLocation);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

        String line = bufferedReader.readLine();
        while (line != null) {
            List<Integer> coordinateList = Arrays.stream(line.split("[^-\\d]")).filter(m -> m.length() > 0)
                    .map(Integer::parseInt).collect(Collectors.toList());
            Sensor sensor = new Sensor(new Coordinate(coordinateList.get(0), coordinateList.get(1)), new Coordinate(coordinateList.get(2), coordinateList.get(3)));
            sensorSet.add(sensor);
            line = bufferedReader.readLine();
        }
        return sensorSet;
    }

    private void printEdge(Set<Coordinate> edge) {
        int minX = edge.stream().mapToInt(s -> s.x).min().orElse(0);
        int maxX = edge.stream().mapToInt(s -> s.x).max().orElse(100);
        int minY = edge.stream().mapToInt(s -> s.y).min().orElse(0);
        int maxY = edge.stream().mapToInt(s -> s.y).max().orElse(100);


        String line = "";


        for (int y = minY; y <= maxY; y++) {
            line += y >= 0 && y < 10 ? " " + y + " " : y + " ";
            for (int x = minX; x <= maxX; x++) {
                int finalX = x;
                int finalY = y;
                if (sensors.stream().map(s -> s.location).anyMatch(l -> l.equals(new Coordinate(finalX, finalY)))) {
                    line += "S ";
                } else if (sensors.stream().map(s -> s.beacon).anyMatch(l -> l.equals(new Coordinate(finalX, finalY)))) {
                    line += "B ";
                } else if (edge.stream().anyMatch(l -> l.equals(new Coordinate(finalX, finalY)))) {
                    line += "# ";
                } else {
                    line += ". ";
                }
            }
            System.out.println(line);
            line = "";
        }
    }

    private void printCave() {
        int minX = Math.min(sensors.stream().mapToInt(s -> s.location.x).min().orElse(0),
                sensors.stream().mapToInt(s -> s.beacon.x).min().orElse(0));
        int maxX = Math.max(sensors.stream().mapToInt(s -> s.location.x).max().orElse(100),
                sensors.stream().mapToInt(s -> s.beacon.x).max().orElse(100));
        int minY = Math.min(sensors.stream().mapToInt(s -> s.location.y).min().orElse(0),
                sensors.stream().mapToInt(s -> s.beacon.y).min().orElse(0));
        int maxY = Math.max(sensors.stream().mapToInt(s -> s.location.y).max().orElse(100),
                sensors.stream().mapToInt(s -> s.beacon.y).max().orElse(100));

        String line = "";


        for (int y = minY; y <= maxY; y++) {
            line += y >= 0 && y < 10 ? " " + y + " " : y + " ";
            for (int x = minX; x <= maxX; x++) {
                int finalX = x;
                int finalY = y;
                if (sensors.stream().map(s -> s.location).anyMatch(l -> l.equals(new Coordinate(finalX, finalY)))) {
                    line += "S ";
                } else if (sensors.stream().map(s -> s.beacon).anyMatch(l -> l.equals(new Coordinate(finalX, finalY)))) {
                    line += "B ";
                } else {
                    line += ". ";
                }
            }
            System.out.println(line);
            line = "";
        }
    }
}
