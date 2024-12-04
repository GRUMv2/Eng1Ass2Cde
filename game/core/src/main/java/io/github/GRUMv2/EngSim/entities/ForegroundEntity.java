package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * ForegroundEntity
 */
public abstract class ForegroundEntity extends Entity {
    protected static final float CELL_WIDTH = 720 / 30;  // TODO: -> Settings

    private Vector2[] relCellsUsed;
    private Vector2 mapPos;
    private Color color;
    private Vector2 pos;

    public ForegroundEntity(Vector2 mapPos, Vector2[] relCellsUsed, Color color) {
        this.mapPos = mapPos;
        this.relCellsUsed = relCellsUsed;
        this.color = color;
        // TODO: unhardcode
        this.pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
    }

    public Vector2 getMapPos() {
        return mapPos;
    }

    public Vector2[] getRelCellsUsed() {
        return relCellsUsed;
    }

    public Color getColor() {
        return color;
    }

    public Vector2 getPos() {
        return pos;
    }

    // TODO: getMidpoint() for text

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        Vector2 cellSize = new Vector2(CELL_WIDTH, CELL_WIDTH);

        for (Vector2 relCellOffset : this.relCellsUsed) {
            Vector2 cellPos = new Vector2(
                this.pos.x + (relCellOffset.x * CELL_WIDTH),
                this.pos.y + (relCellOffset.y * CELL_WIDTH)
            );
            renderer.drawRect(cellPos, cellSize, this.color);
        }
    }
}
