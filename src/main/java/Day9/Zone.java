package Day9;

public class Zone {
    Coordinate<Integer, Integer> bottomLeft;
    Coordinate<Integer, Integer> topRight;
    Integer rope;

    public Zone(Integer x, Integer y, Integer rope) {
        this.rope = rope;
        setZone(x, y);
    }
    
    public void setZone(Integer x, Integer y){
        this.setZone(x, y, rope);
    }
    
    public void setZone(Integer x, Integer y, Integer rope){
        this.bottomLeft = new Coordinate<>(x -rope, y -rope);
        this.topRight = new Coordinate<>(x+rope, y +rope);
    }    
}
