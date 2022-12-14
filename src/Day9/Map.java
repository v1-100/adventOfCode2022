package Day9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Map {
    Pion head;
    //Pion tail;
    ArrayList<Pion> rope;
    Integer stepSize;

    public Map(Integer stepSize, Integer ropeSize) {
        this.head = new Pion("H", new Coordinate<>(0, 0), 1);
        //this.tail = new Pion("T", new Coordinate<>(0, 0), stepSize);
        this.rope = new ArrayList<>();
        for (int i = 0; i < ropeSize; i++) {
            rope.add(new Pion("R"+i,new Coordinate<>(0, 0), 1));
        }
        this.stepSize = stepSize;
    }

    public void move(Pion pion, char destination) throws CloneNotSupportedException {
        switch (destination) {
            case 'R':
                pion.coordinate.x = pion.coordinate.x + 1;
                break;
            case 'L':
                pion.coordinate.x = pion.coordinate.x - 1;
                break;
            case 'U':
                pion.coordinate.y = pion.coordinate.y + 1;
                break;
            case 'D':
                pion.coordinate.y = pion.coordinate.y - 1;
        }
        pion.setZone();
        pion.saveCoordinate();
    }
    public void move(Pion pion, Coordinate destination) throws CloneNotSupportedException {
        pion.setCoordinate(destination);
        pion.setZone();
        pion.saveCoordinate();
    }

    public Coordinate<Integer, Integer> getDestinationForSuiveur(Pion pionDevant, Pion pionSuiveur) {
        Set<Coordinate> possibleDestination = new HashSet<>();
        ArrayList<Coordinate> possibleMovements = getPossibleMovements(pionSuiveur);
        for(Coordinate destination : possibleMovements){
            Pion tempPion = new Pion("temp", destination, this.stepSize);
            //on verifie que la futur position contient head
            if(isCoordinateInZone(pionDevant.coordinate, tempPion.zone)){
                possibleDestination.add(destination);
            }
        }
        System.out.println("possibleDestination : "+possibleDestination);
        
        return getBestCoordinate(possibleDestination, pionDevant);
    }

    private Coordinate<Integer, Integer> getBestCoordinate(Set<Coordinate> possibleDestination, Pion pion) {
        Coordinate bestCoordinate = null;
        Integer bestDistance = 10000000;
        for (Coordinate coordinate : possibleDestination) {
            Integer distance = getDistanceToGo(coordinate, pion.coordinate);
            if(distance < bestDistance){
                bestCoordinate = coordinate;
                bestDistance = distance;
            }
        }
        return bestCoordinate;
    }

    private int getDistanceToGo(Coordinate<Integer, Integer> coordinate1, Coordinate<Integer, Integer> coordinate2) {
        //return (int) Math.sqrt(Math.pow(coordinate2.x - coordinate1.x, 2) + Math.pow(coordinate2.y - coordinate1.y, 2));
        return Math.abs(coordinate2.x - coordinate1.x) + Math.abs(coordinate2.y - coordinate1.y);
    }

    private ArrayList<Coordinate> getPossibleMovements(Pion pion) {
        ArrayList destinations = generateCoordinatesOfTheZone(pion.zone);
        destinations.remove(pion.coordinate);
        return destinations;
    }

    public boolean isCoordinateInZone(Coordinate coordinate, Zone zone) {
        ArrayList<Coordinate> coordinatesOfTheZone = generateCoordinatesOfTheZone(zone);
        return coordinatesOfTheZone.indexOf(coordinate) > -1;
    }

    public ArrayList<Coordinate> getCommonCoordinate(Pion pionDevant, Pion pionDerriere) {
        ArrayList<Coordinate> common = new ArrayList<>();
        //head.zone.bottomLeft.x
        ArrayList<Coordinate> coordinatesHead = generateCoordinatesOfTheZone(pionDevant.zone);
        ArrayList<Coordinate> coordinatesTail = generateCoordinatesOfTheZone(pionDerriere.zone);
        for (Coordinate coorTail : coordinatesTail) {
            for (Coordinate coorHead : coordinatesHead) {
                if(coorTail.equals(coorHead)){
                    common.add(coorHead);
                }
            }
        }
        //System.out.println("getCommonCoordinate : "+common);
        return common;
    }
    public ArrayList<Coordinate> getCommonCoordinate(Zone zone1, Zone zone2) {
        ArrayList<Coordinate> common = new ArrayList<>();
        //head.zone.bottomLeft.x
        ArrayList<Coordinate> coordinatesTail = generateCoordinatesOfTheZone(zone1);
        ArrayList<Coordinate> coordinatesHead = generateCoordinatesOfTheZone(zone2);
        for (Coordinate coorTail : coordinatesTail) {
            for (Coordinate coorHead : coordinatesHead) {
                if(coorTail.equals(coorHead)){
                    common.add(coorHead);
                }
            }
        }
        //System.out.println("getCommonCoordinate : "+common);
        return common;
    }
    private ArrayList<Coordinate> generateCoordinatesOfTheZone(Zone zone) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        Integer x1 = zone.bottomLeft.x < zone.topRight.x ? zone.bottomLeft.x : zone.topRight.x;
        Integer x2 = zone.bottomLeft.x < zone.topRight.x ? zone.topRight.x : zone.bottomLeft.x;
        Integer y1 = zone.bottomLeft.y < zone.topRight.y ? zone.bottomLeft.y : zone.topRight.y;
        Integer y2 = zone.bottomLeft.y < zone.topRight.y ? zone.topRight.y : zone.bottomLeft.y;
        
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                coordinates.add(new Coordinate<>(i, j));
            }
        }
        
        return coordinates;
    }
    
}
