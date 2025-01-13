package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Halls extends Building {

    public final int studentHousingCapacity = 1_000;
    public final int studentStudyCapacity = 0;
    public final int leisureCapacity = 0;
    public final int staffOfficeCapacity = 0;
    public final int cost = 250_000;
    public final int monthlyUpkeepCosts = 50_000;

    public Halls(Vector2 mapPos) {
        // TODO: Map data
        super(
            mapPos,
            new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(3, 0),
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(2, 1),
                new Vector2(3, 1),
            },
            Color.CYAN
        );
        String NAME = "Halls";
        this.setText(NAME);
        this.setName(NAME);
        String DESCRIPTION = "A place to sleep.";
        this.setDescription(DESCRIPTION);
    }

    @Override
    public int getStudentHousingCapacity() {
        return studentHousingCapacity;
    }

    @Override
    public int getStudentStudyCapacity() {
        return studentStudyCapacity;
    }

    @Override
    public int getLeisureCapacity() {
        return leisureCapacity;
    }

    @Override
    public int getStaffOfficeCapacity() {
        return staffOfficeCapacity;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getMonthlyUpkeepCosts() {
        return monthlyUpkeepCosts;
    }
}
