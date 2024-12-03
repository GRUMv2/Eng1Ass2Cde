package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class Obstacle extends Entity {
    // TODO: -> Settings
    protected static final float CELL_WIDTH = 720 / 30;

    private Vector2 mapPos;
    private Vector2[] relCellsUsed;
    private Color color;

    public Obstacle(Vector2 mapPos, Vector2[] relCellsUsed, Color color) {
        this.mapPos = mapPos;
        this.relCellsUsed = relCellsUsed;
        this.color = color;
    }

    // TODO: UI overhaul -> abstract
    // Think that Building, Obstacle (and NI Road) can be abstracted to a central
    // ForegroundEntity class with a single update() method
    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        // TODO: unhardcode
        Vector2 pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
        Vector2 cellSize = new Vector2(CELL_WIDTH, CELL_WIDTH);

        for (Vector2 relCellOffset : relCellsUsed) {
            Vector2 cellPos = new Vector2(
                pos.x + (relCellOffset.x * CELL_WIDTH),
                pos.y + (relCellOffset.y * CELL_WIDTH)
            );
            renderer.drawRect(cellPos, cellSize, color);
        }
    }

    public Vector2[] getRelCellsUsed() {
        return relCellsUsed;
    }

    public Vector2 getMapPos() {
        return mapPos;
    }
}
