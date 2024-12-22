package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Water
 */
public class Water extends Obstacle {
    public Water(Vector2 mapPos, Vector2[] relCellsUsed) {
        super(mapPos, relCellsUsed, Color.BLUE); // TODO: -> Settings
    }

}
