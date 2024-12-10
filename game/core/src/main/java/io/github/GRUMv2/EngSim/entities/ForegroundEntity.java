package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

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

    private String text;
    private Color textColor;
    private Vector2 textMidpoint;

    public ForegroundEntity(Vector2 mapPos, Vector2[] relCellsUsed, Color color) {
        this.mapPos = mapPos;
        this.relCellsUsed = relCellsUsed;
        this.color = color;
        // TODO: unhardcode
        this.pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
    }

    public void setText(String text, Color textColor) {
        this.text = text;
        this.textColor = textColor;
        this.textMidpoint = getTextMidpoint();
    }

    public void setText(String text) {
        this.setText(text, Color.WHITE);    // TODO: default color -> Settings?
    }

    public String getText() {
        return text;
    }

    public Color getTextColor() {
        return textColor;
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

    private Vector2 getTextMidpoint() {
        if (relCellsUsed.length < 1) {
            return new Vector2(CELL_WIDTH, CELL_WIDTH);
        }
        float minX = 0;
        float minY = 0;
        float maxY = 0;
        float maxX = 0;
        for (Vector2 v : relCellsUsed) {
            if (v.x > maxX) {
                maxX = v.x;
            } else if (v.x < minX) {
                minX = v.x;
            }
            if (v.y > maxY) {
                maxY = v.y;
            } else if (v.y < minY) {
                minY = v.y;
            }
        }
        float x = (maxX + minX + 1) / 2;
        float y = (maxY + minY + 1) / 2;
        return new Vector2(this.pos.x + (x * CELL_WIDTH), this.pos.y + (y * CELL_WIDTH));
    }

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

        if (this.text != null) {
            renderer.drawText(
                this.text,
                new Vector2(this.textMidpoint.x, this.textMidpoint.y),
                this.textColor, // Color gets set when text does
                1.25f,
                Align.center
            );
        }
    }
}
