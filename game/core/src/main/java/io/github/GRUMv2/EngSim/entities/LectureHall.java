package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class LectureHall extends Building {

    public LectureHall(Vector2 mapPos) {
        // TODO: Map data
        super(
            mapPos,
            new Vector2[] {
                new Vector2(-2, 0),
                new Vector2(-1, 0),
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),

                new Vector2(-2, 1),
                new Vector2(-1, 1),
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(2, 1),

                new Vector2(-1, 2),
                new Vector2(0, 2),
                new Vector2(1, 2),
            },
            Color.CYAN
        );
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        super.update(renderer, inputHandler);
        Vector2 pos = this.getPos();
        renderer.drawText(
            "Lecture Hall",
            new Vector2(pos.x, pos.y + CELL_WIDTH),
            Color.WHITE,
            1.25f
        );
    }
}
