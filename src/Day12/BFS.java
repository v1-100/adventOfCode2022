package Day12;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BFS {
    public static List<Coordinate> bfs(Heightmap map, Coordinate start, Coordinate goal) {
        Queue<Coordinate> queue = new ArrayDeque<>();
        Set<Coordinate> visited = new HashSet<>();
        Map<Coordinate, Coordinate> parentMap = new HashMap<>();

        // Ajout du n?ud de d�part � la file
        queue.add(start);
        parentMap.put(start, null);
        visited.add(start);

        while (!queue.isEmpty()) {
            // Retrait du premier n?ud de la file
            Coordinate currentNode = queue.poll();

            // Si le n?ud actuel est l'objectif, arr�tez l'algorithme et renvoyez le chemin
            if (currentNode.equals(goal)) {
                return constructPath(parentMap, currentNode);
            }
            
            // Sinon, parcourez tous les voisins de currentNode qui n'ont pas �t� visit�s et ajoutez-les � la file
            for (Coordinate neighbor : map.getPossibleMoveRisingFilteredFromPosition(currentNode)) {
                if (!visited.contains(neighbor)) {
                    parentMap.put(neighbor, currentNode);
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        // Si l'objectif n'a pas �t� atteint, renvoyez null
        return null;
    }

    private static List<Coordinate> constructPath(Map<Coordinate, Coordinate> parentMap, Coordinate goal) {
        // Cr�ation de la liste qui va contenir le chemin
        List<Coordinate> path = new ArrayList<>();

        // Ajout du n?ud objectif � la liste
        path.add(goal);

        // R�cup�ration du n?ud parent du n?ud objectif � partir de la map des n?uds parents
        Coordinate parent = parentMap.get(goal);

        // Tant que le n?ud parent n'est pas null (c'est-�-dire que nous ne sommes pas encore arriv�s au d�part)
        while (parent != null) {
            // Ajout du n?ud parent � la liste
            path.add(parent);
            // R�cup�ration du n?ud parent du n?ud parent
            parent = parentMap.get(parent);
        }
        return path;
    }
    
    
}
