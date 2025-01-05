package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Road
 */
public class Road extends Obstacle {
    public Road(Vector2 mapPos, Vector2[] relCellsUsed) {
        super(mapPos, relCellsUsed, Color.GRAY); // TODO: -> Settings
    }

}
