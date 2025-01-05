package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Restaurant extends Building {

    private final String NAME = "Restaurant";
    private final String DESCRIPTION = "A place to eat.";

    public int studentHousingCapacity = 0;
    public int studentStudyCapacity = 0;
    public int leisureCapacity = 4000;
    public int staffOfficeCapacity = 0;
    public int cost = 75_000;
    public int monthlyUpkeepCosts = 30_000;

        @Override public int getStudentHousingCapacity() {return studentHousingCapacity;}
    @Override public int getStudentStudyCapacity() {return studentStudyCapacity;}
    @Override public int getLeisureCapacity() {return leisureCapacity;}
    @Override public int getStaffOfficeCapacity() {return staffOfficeCapacity;}
    @Override public int getCost() {return cost;}
    @Override public int getMonthlyUpkeepCosts() {return monthlyUpkeepCosts;}

    public Restaurant(Vector2 mapPos) {
        // TODO: Map data
        super(
            mapPos,
            new Vector2[] {
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(3, 0),
                new Vector2(0, 1),
                new Vector2(3, 1)
            },
            Color.RED
        );
        this.setText(this.NAME);
        this.setName(this.NAME);
        this.setDescription(this.DESCRIPTION);
    }
}
