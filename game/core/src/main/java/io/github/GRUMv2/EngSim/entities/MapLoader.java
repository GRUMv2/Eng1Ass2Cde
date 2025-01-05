package io.github.GRUMv2.EngSim.entities;

//import java.util.ArrayList;
//import java.util.Random;

import com.badlogic.gdx.math.Vector2;

/**
 * MapLoader
 */
public class MapLoader {
    // Would be relatively straightforward to patch in random map gen here
    // but going to leave it as-is for now.

    //private final int ROAD_BRANCH = 10;
    //private final int MAX_ROAD = 25;
    //private final int LAKE_SIZE = 8;
    //private final Random random;
    //
    //public MapLoader() {
    //    this.random = new Random();
    //}

    public static Obstacle[] gibMap() {
        return new Obstacle[]{
            new Water(
                new Vector2(5, 5),
                new Vector2[] {
                    new Vector2(1, 0),
                    new Vector2(2, 0),
                    new Vector2(3, 0),

                    new Vector2(0, 1),
                    new Vector2(1, 1),
                    new Vector2(2, 1),
                    new Vector2(3, 1),
                    new Vector2(4, 1),
                    new Vector2(5, 1),

                    new Vector2(3, 2),
                    new Vector2(4, 2),
                    new Vector2(5, 2)
                }
            ),
            new Water(
                new Vector2(20, 25),
                new Vector2[] {
                    new Vector2(0, 0),
                    new Vector2(1, 0),
                    new Vector2(0, -1),
                    new Vector2(1, -1),
                    new Vector2(0, -2),
                    new Vector2(1, -2),
                    new Vector2(1, -3),
                    new Vector2(2, -3),
                    new Vector2(2, -4),
                    new Vector2(3, -4),
                    new Vector2(2, -5),
                    new Vector2(3, -5),
                    new Vector2(2, -6),
                    new Vector2(3, -6),
                    new Vector2(3, -7),
                }
            ),
            new Road(
                new Vector2(12, 0),
                new Vector2[] {
                    new Vector2(0, 0),
                    new Vector2(0, 1),
                    new Vector2(0, 2),
                    new Vector2(0, 3),
                    new Vector2(0, 4),
                    new Vector2(0, 5),
                    new Vector2(0, 6),
                    new Vector2(0, 7),
                    new Vector2(0, 8),
                    new Vector2(0, 9),
                    new Vector2(0, 10),
                    new Vector2(0, 11),
                    new Vector2(0, 12),
                    new Vector2(0, 13),
                    new Vector2(0, 14),
                    new Vector2(0, 15),
                    new Vector2(0, 16),
                    new Vector2(0, 17),
                    new Vector2(0, 18),
                    new Vector2(0, 19),
                    new Vector2(0, 20),
                    new Vector2(0, 21),
                    new Vector2(0, 22),
                    new Vector2(0, 23),
                    new Vector2(0, 24),
                    new Vector2(0, 25),
                    new Vector2(0, 26),
                    new Vector2(0, 27),
                    new Vector2(0, 28),
                    new Vector2(0, 29),
                    new Vector2(1, 14),
                    new Vector2(2, 14),
                    new Vector2(3, 14),
                    new Vector2(4, 14),
                    new Vector2(5, 14),
                    new Vector2(6, 14),
                    new Vector2(7, 14),
                    new Vector2(8, 14),
                    new Vector2(9, 14),
                    new Vector2(9, 13),
                    new Vector2(9, 12),
                    new Vector2(9, 11),
                    new Vector2(9, 10),
                    new Vector2(9, 9),
                    new Vector2(9, 8),
                    new Vector2(9, 7),
                    new Vector2(9, 6),
                }
            )
        };
    }

}
