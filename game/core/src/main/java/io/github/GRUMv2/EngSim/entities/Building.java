package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public abstract class Building extends ForegroundEntity {

    private String NAME = "";
    private String DESCRIPTION = "";

    public Building(Vector2 mapPos, Vector2[] relCellsUsed, Color color) {
        super(mapPos, relCellsUsed, color);
    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public void setName(String name) {
        this.NAME = name;
    }

    public void setDescription(String description) {
        DESCRIPTION = description;
    }

}
