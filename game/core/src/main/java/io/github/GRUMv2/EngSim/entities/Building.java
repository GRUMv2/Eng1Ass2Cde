package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public abstract class Building extends ForegroundEntity {

    private String NAME = "";
    private String DESCRIPTION = "";

    public int studentHousingCapacity;
    public int studentStudyCapacity;
    public int leisureCapacity;
    public int staffOfficeCapacity;
    public int cost;
    public int monthlyUpkeepCosts;

    // Yep, but not much we can do about it stupid java
    public abstract int getStudentHousingCapacity();
    public abstract int getStudentStudyCapacity();
    public abstract int getLeisureCapacity();
    public abstract int getStaffOfficeCapacity();
    public abstract int getCost();
    public abstract int getMonthlyUpkeepCosts();

    /**
     * Constructor for a building
     * @param mapPos The position of the building on the map (map rel)
     * @param relCellsUsed The cells that the building occupies (mapPos rel)
     * @param color The color of the building, duh, i hate javadoc
     */
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
