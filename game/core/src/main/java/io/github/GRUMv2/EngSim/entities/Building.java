package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public abstract class Building extends ForegroundEntity {
    public Building(Vector2 mapPos, Vector2[] relCellsUsed, Color color) {
        super(mapPos, relCellsUsed, color);
    }
}
