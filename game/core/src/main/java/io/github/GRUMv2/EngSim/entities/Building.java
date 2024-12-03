package io.github.GRUMv2.EngSim.entities;

//  TODO: UI overhaul
// Think that Building, Obstacle (and NI Road) can be abstracted to a central
// ForegroundEntity class with a single update() method

import com.badlogic.gdx.math.Vector2;

public abstract class Building extends Entity {
    protected static final float CELL_WIDTH = 720 / 30;  // TODO: -> Settings

    protected Vector2[] relCellsUsed;
    protected Vector2 mapPos;

    public Building(Vector2 mapPos, Vector2[] relCellsUsed) {
        this.mapPos = mapPos;
        this.relCellsUsed = relCellsUsed;
    }

    public Vector2 getMapPos() {
        return mapPos;
    }

    public Vector2[] getRelCellsUsed() {
        return relCellsUsed;
    }
}
