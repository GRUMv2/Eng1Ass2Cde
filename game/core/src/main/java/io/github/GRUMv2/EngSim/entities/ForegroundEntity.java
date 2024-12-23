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
    private Vector2[] bounds;
    private Vector2 textMidpoint;
    private Vector2 fontBounds;

    public ForegroundEntity(Vector2 mapPos, Vector2[] relCellsUsed, Color color) {
        this.mapPos = mapPos;
        this.relCellsUsed = relCellsUsed;
        this.color = color;
        // TODO: unhardcode
        this.pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
        this.bounds = this.getBounds();
        this.fontBounds = this.fontHackBounds();
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


    // TODO, low priority: Rewrite font rendering
    // There's no context of bounds to the existing system outside of the
    // hack added in drawScalingText
    // The entire system needs to be replaced with, at very minimum,
    // the concept of "objects with text" that control their own font scaling
    // rather than the current "draw a box of hardcoded size then draw text of hardcoded size on top"

    private Vector2 fontHackBounds() {
        return new Vector2(
            (bounds[1].x - bounds[0].x + 0.5f) * CELL_WIDTH,
            (bounds[1].y - bounds[0].y + 0.5f) * CELL_WIDTH
        );
    }

    private Vector2 getTextMidpoint() {
        float x = (bounds[1].x + bounds[0].x + 1) / 2;
        float y = (bounds[1].y + bounds[0].y + 1) / 2;
        return new Vector2(this.pos.x + (x * CELL_WIDTH), this.pos.y + (y * CELL_WIDTH));
    }

    private Vector2[] getBounds() {
        if (relCellsUsed.length < 1) {
            return new Vector2[2];
        }
        float minX = Integer.MAX_VALUE;
        float minY = Integer.MAX_VALUE;
        float maxY = Integer.MIN_VALUE;
        float maxX = Integer.MIN_VALUE;
        for (Vector2 v : relCellsUsed) {
            if (v.x > maxX) {
                maxX = v.x;
            }
            if (v.x < minX) {
                minX = v.x;
            }
            if (v.y > maxY) {
                maxY = v.y;
            }
            if (v.y < minY) {
                minY = v.y;
            }
        }
        Vector2 min = new Vector2(minX, minY);
        Vector2 max = new Vector2(maxX, maxY);
        return new Vector2[]{ min, max };
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
                renderer.calcFontScale(this.fontBounds, this.text, /* max-size, TODO unhardcode */ 2.0f),
                Align.center
            );
        }
    }
}
