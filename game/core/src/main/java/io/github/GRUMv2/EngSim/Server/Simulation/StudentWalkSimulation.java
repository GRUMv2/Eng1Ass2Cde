package io.github.GRUMv2.EngSim.Server.Simulation;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.entities.ForegroundEntity;
import io.github.GRUMv2.EngSim.entities.Water;
import io.github.GRUMv2.EngSim.entities.HallsAccommadation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StudentWalkSimulation {
    Map<AbstractMap.SimpleEntry<Class<? extends ForegroundEntity>, Class<? extends ForegroundEntity>>, Integer> thingsToCheck = new HashMap<>();

    StudentWalkSimulation() {
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Water.class, HallsAccommadation.class), 1);
    }

    double distanceByDijkstra(ConcurrentHashMap<Vector2, ForegroundEntity> grid, Vector2 from, Vector2 to) {
        // Avoids any tiles without an entry in the grid
        // returns just the distance between the two points

        if (!grid.containsKey(from) || !grid.containsKey(to)) return -1;

        double ttl = 1000;

        Map<Vector2, Double> distances = new HashMap<>();
        distances.put(from, 0.0);


        while (!distances.isEmpty()) {
            ttl -= 1;

            if (ttl < 0) {
                return -1;
            }

            Vector2 current = distances.keySet().iterator().next();
            double distance = distances.get(current);

            if (current.equals(to)) {
                return distance;
            }

            if (!grid.containsKey(new Vector2(current.x + 1, current.y))) {
                distances.put(new Vector2(current.x + 1, current.y), distance + 1);
            }

            if (!grid.containsKey(new Vector2(current.x - 1, current.y))) {
                distances.put(new Vector2(current.x - 1, current.y), distance + 1);
            }

            if (!grid.containsKey(new Vector2(current.x, current.y + 1))) {
                distances.put(new Vector2(current.x, current.y + 1), distance + 1);
            }

            if (!grid.containsKey(new Vector2(current.x, current.y - 1))) {
                distances.put(new Vector2(current.x, current.y - 1), distance + 1);
            }

            distances.remove(current);
        }

        return -1;
    }

    void tick(ConcurrentHashMap<Vector2, ForegroundEntity> grid, HashMap<Class<? extends ForegroundEntity>, ArrayList<Vector2>> map) {
        for (AbstractMap.SimpleEntry<Class<? extends ForegroundEntity>, Class<? extends ForegroundEntity>> thing : thingsToCheck.keySet()) {
            Class<? extends ForegroundEntity> thingA = thing.getKey();
            Class<? extends ForegroundEntity> thingB = thing.getValue();

            // skip thing if either of the parts are not present
            if (!map.containsKey(thingA)) continue;
            if (!map.containsKey(thingB)) continue;

            ArrayList<Vector2> allOfA = map.get(thingA);
            ArrayList<Vector2> allOfB = map.get(thingB);

            ArrayList<Double> distances = new ArrayList<>();

            // TODO: randomly select a set of points to check
            for (Vector2 a : allOfA) {
                for (Vector2 b : allOfB) {
                    distances.add(distanceByDijkstra(grid, a, b));
                }
            }

            double sum = 0;
            for (double d : distances) {
                if (d == -1) {
                    continue;
                }

                sum += d;
            }
            double avg = sum / distances.size();

            System.out.println("Average distance between " + thingA.getSimpleName() + " and " + thingB.getSimpleName() + " is " + avg);
        }
    }
}
