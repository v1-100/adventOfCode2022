package Day12;


import java.util.ArrayList;
import java.util.List;

public class ExplorationResult {
    Boolean foundE;
    Boolean tooLong;
    List<Coordinate> path;

    public ExplorationResult() {
        this.foundE = false;
        this.tooLong = false;
        this.path = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ExplorationResult{" +
                "foundE=" + foundE +
                ", path=" + path +
                '}';
    }
}
