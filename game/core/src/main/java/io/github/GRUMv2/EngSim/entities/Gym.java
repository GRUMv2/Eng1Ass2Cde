package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Gym extends Building {
    private final String NAME = "Gym";
    private final String DESCRIPTION = "A place to get bolo.";

    public int studentHousingCapacity = 0;
    public int studentStudyCapacity = 0;
    public int leisureCapacity = 6000;
    public int staffOfficeCapacity = 0;
    public int cost = 175_000;
    public int monthlyUpkeepCosts = 50_000;

    public Gym(Vector2 mapPos) {
        // TODO: Map data
        super(
            mapPos,
            new Vector2[] {
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),

                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(2, 1),

                new Vector2(0, 2),
                new Vector2(1, 2),
                new Vector2(2, 2),
            },
            Color.PURPLE
        );
        this.setText(this.NAME);
        this.setName(this.NAME);
        this.setDescription(this.DESCRIPTION);
    }
}
