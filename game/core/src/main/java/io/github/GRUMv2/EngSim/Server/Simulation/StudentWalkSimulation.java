package io.github.GRUMv2.EngSim.Server.Simulation;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.entities.ForegroundEntity;
import io.github.GRUMv2.EngSim.entities.Water;
import io.github.GRUMv2.EngSim.entities.HallsAccommadation;
import io.github.GRUMv2.EngSim.entities.LectureHall;
import io.github.GRUMv2.EngSim.entities.Pub;
import io.github.GRUMv2.EngSim.entities.Restaurant;
import io.github.GRUMv2.EngSim.entities.Gym;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StudentWalkSimulation {
    Map<AbstractMap.SimpleEntry<Class<? extends ForegroundEntity>, Class<? extends ForegroundEntity>>, Integer> thingsToCheck = new HashMap<>();

    StudentWalkSimulation() {
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Water.class, HallsAccommadation.class), 2);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Water.class, Restaurant.class), 6);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(HallsAccommadation.class, Gym.class), 4);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(HallsAccommadation.class, Pub.class), 6);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(HallsAccommadation.class, Restaurant.class), 1);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(HallsAccommadation.class, LectureHall.class), 8);

        // Dont put pubs next to restaurants or gyms
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Restaurant.class, Pub.class), -2);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Gym.class, Pub.class), -2);
    }

    double hdist(Vector2 a, Vector2 b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }


    double distanceByAStar(ConcurrentHashMap<Vector2, ForegroundEntity> grid, Vector2 from, Vector2 to, double maxDistance) {
        Map<Vector2, Double> current_score = new HashMap<>();
        Map<Vector2, Double> straight_line_score = new HashMap<>();

        // saves almost 4ms per 32 iterations!!!!!
        PriorityQueue<Vector2> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> straight_line_score.getOrDefault(node, Double.MAX_VALUE)));

        Set<Vector2> visited = new HashSet<>();

        current_score.put(from, 0.0);
        straight_line_score.put(from, hdist(from, to));
        queue.add(from);

        while (!queue.isEmpty()) {
            Vector2 current = queue.poll();

            // TODO: Is returning the maxDistance the best way to handle this?
            if (current_score.getOrDefault(current, Double.MAX_VALUE) > maxDistance) {
                // System.out.println("Maximum allowed distance exceeded.");
                return maxDistance;
            }

            if (current.equals(to)) {
                return current_score.get(current);
            }

            if (visited.contains(current)) continue;
            visited.add(current);

            // Why oh why does java have to be so... java
            // for direction in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
            for (int[] direction : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                Vector2 neighbor = new Vector2(current.x + direction[0], current.y + direction[1]);

                if (grid.containsKey(neighbor) && !neighbor.equals(to)) continue;

                double tentativeGScore = current_score.get(current) + 1;
                if (tentativeGScore < current_score.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    current_score.put(neighbor, tentativeGScore);
                    straight_line_score.put(neighbor, tentativeGScore + hdist(neighbor, to));
                    queue.add(neighbor);
                }
            }
        }

        // womp womp
        // TODO: Should this be -1 or maxDistance?
        return -1;
    }

    void tick(ConcurrentHashMap<Vector2, ForegroundEntity> grid, HashMap<Class<? extends ForegroundEntity>, ArrayList<Vector2>> map) {
        for (AbstractMap.SimpleEntry<Class<? extends ForegroundEntity>, Class<? extends ForegroundEntity>> thing : thingsToCheck.keySet()) {
            Class<? extends ForegroundEntity> thingA = thing.getKey();
            Class<? extends ForegroundEntity> thingB = thing.getValue();

            // skip thing if either of the parts are not present
            if (!map.containsKey(thingA)) continue;
            if (!map.containsKey(thingB)) continue;

            System.out.println("Checking " + thingA.getSimpleName() + " and " + thingB.getSimpleName());

            ArrayList<Vector2> allOfA = map.get(thingA);
            ArrayList<Vector2> allOfB = map.get(thingB);

            ArrayList<Double> distances = new ArrayList<>();

            // TODO: discus weather random should be centralized/seeded
            Random random = new Random();

            // System.out.println("Checking 8 instead of " + allOfA.size() * allOfB.size() + " points");

            int samples = 8;
            for (int i = 0; i < samples; i++) {
                // Randomly select a point from allOfA and allOfB
                Vector2 a = allOfA.get(random.nextInt(allOfA.size()));
                Vector2 b = allOfB.get(random.nextInt(allOfB.size()));

                // Calculate the distance and store it
                double distance = distanceByAStar(grid, a, b, 32);
                distances.add(distance);
            }

            double sum = 0;
            for (double d : distances) {
                if (d == -1) {
                    continue;
                }

                sum += d;
            }
            double avg = (sum / distances.size()) * thingsToCheck.get(thing);

            System.out.println("Average distance between " + thingA.getSimpleName() + " and " + thingB.getSimpleName() + " is " + avg);
        }
        // TODO average of averages you know
    }
}
