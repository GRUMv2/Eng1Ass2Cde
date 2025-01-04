package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Pub extends Building {

    private final String NAME = "Pub";
    private final String DESCRIPTION = "A place to drink.";

    public Pub(Vector2 mapPos) {


        // TODO: Map data
        super(
            mapPos,
            new Vector2[] {
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(0, 1),
                new Vector2(1, 1)
            },
            // TODO: -> Settings
            Color.PINK
        );
        this.setText(this.NAME);
        this.setName(this.NAME);
        this.setDescription(this.DESCRIPTION);
    }
}
