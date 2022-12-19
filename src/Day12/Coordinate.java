package Day12;

import java.util.Objects;

public class Coordinate implements Cloneable, Comparable {
    public Integer x;
    public Integer y;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && Objects.equals(((Coordinate) obj).x, x) && Objects.equals(((Coordinate) obj).y, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return ((Coordinate) o).x - x;
    }
}
