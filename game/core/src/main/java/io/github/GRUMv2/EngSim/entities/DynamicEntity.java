package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * DynamicEntity
 */
public abstract class DynamicEntity extends Entity {
    private final Vector2[] relCellsUsed;
    private Color color;

    public DynamicEntity(Vector2[] relCellsUsed, Color color) {
        this.relCellsUsed = relCellsUsed;
        this.color = color;
    }

    public Vector2[] getRelCellsUsed() {
        return relCellsUsed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void update(Renderer renderer, InputHandler inputHandler);
}
