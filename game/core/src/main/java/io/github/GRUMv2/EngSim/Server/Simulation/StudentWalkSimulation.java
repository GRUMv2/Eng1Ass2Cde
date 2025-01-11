package io.github.GRUMv2.EngSim.Server.Simulation;

import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.entities.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StudentWalkSimulation {
    Map<AbstractMap.SimpleEntry<Class<? extends ForegroundEntity>, Class<? extends ForegroundEntity>>, Integer> thingsToCheck = new HashMap<>();
    ArrayList<Double> scores = new ArrayList<>();

    StudentWalkSimulation() {
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Water.class, Halls.class), 2);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Water.class, Restaurant.class), 6);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Halls.class, Gym.class), 4);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Halls.class, Pub.class), 6);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Halls.class, Restaurant.class), 1);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Halls.class, LectureHall.class), 8);

        // Dont put pubs next to restaurants or gyms
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Restaurant.class, Pub.class), -2);
        thingsToCheck.put(new AbstractMap.SimpleEntry<>(Gym.class, Pub.class), -2);
    }

    private double hdist(Vector2 a, Vector2 b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }


    private double distanceByAStar(ConcurrentHashMap<Vector2, ForegroundEntity> grid, Vector2 from, Vector2 to, double maxDistance) {
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
        ArrayList<Double> totalDistances = new ArrayList<>();

        for (AbstractMap.SimpleEntry<Class<? extends ForegroundEntity>, Class<? extends ForegroundEntity>> thing : thingsToCheck.keySet()) {
            Class<? extends ForegroundEntity> thingA = thing.getKey();
            Class<? extends ForegroundEntity> thingB = thing.getValue();

            // skip thing if either of the parts are not present
            if ((!map.containsKey(thingA)) || (!map.containsKey(thingB))) {
                totalDistances.add(
                    (double) thingsToCheck.get(thing) * 32. // HARDCODE: 32 is the max distance
                );
                continue;
            }

//            System.out.println("Checking " + thingA.getSimpleName() + " and " + thingB.getSimpleName());

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

            double avg = this.getAvg(distances) * thingsToCheck.get(thing);

//            System.out.println("Average distance between " + thingA.getSimpleName() + " and " + thingB.getSimpleName() + " is " + avg);

            totalDistances.add(avg);
        }
        double avg = getAvg(totalDistances);
        double res = 100 - Math.max(Math.min((avg - 40) * 2, 100), 0);  // HARDCODE: 40 and *2 based on testing to get realistic min/max of score

//        System.out.println("Distance score: " + res);

        this.scores.add(res / 100);

        if (this.scores.size() > 20) {
            this.scores.remove(0);
        }
    }

    private double getAvg(ArrayList<Double> of) {
        double sum = 0;
        for (double d : of) {
            if (d == -1) {
                continue;
            }

            sum += d;
        }
        return sum / of.size();
    }

    public double getScore() {
        return this.getAvg(this.scores);
    }
}
