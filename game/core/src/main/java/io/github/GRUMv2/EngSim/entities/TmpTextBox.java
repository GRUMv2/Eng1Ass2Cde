package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * TmpTextBox
 */
public class TmpTextBox extends Entity {

    private Vector2 pos;
    private Vector2 size;
    private Vector2 fontBounds;
    private float textOffsetX;
    private float textOffsetY;
    private float borderWidth = 0f;
    private String content = "";
    private Color backgroundColor;
    private Color borderColor = Color.BLACK;
    private Color textColor = Color.BLACK;
    private int textAlign = Align.left;

    public TmpTextBox(Vector2 pos, Vector2 size) {
        this.pos = pos;
        this.size = size;
        // Text is drawn at the left wall of the bounding box, offset by both the border width
        // and then "an amount" such that the text isn't right up against the edge, modelled
        // in this case by the log function of the box area as it's relatively suitable
        this.textOffsetX = (this.borderWidth / 2f) + (float) Math.log(this.size.x * this.size.x);
        this.textOffsetY = (this.borderWidth / 2f) + (float) Math.log(this.size.y * this.size.y);
        this.fontBounds = new Vector2(
            this.size.x - (2 * textOffsetX),
            this.size.y - (2 * textOffsetY)
        );
    }

    public TmpTextBox(Vector2 pos, Vector2 size, String content) {
        this(pos, size);
        this.content = content;
    }

    public TmpTextBox(Vector2 pos, Vector2 size, String[] contentLines) {
        this(pos, size, String.join("\n", contentLines));
    }

    public TmpTextBox(Vector2 pos, Vector2 size, String content, float borderWidth) {
        this(pos, size, content);
        this.borderWidth = borderWidth;
    }

    public TmpTextBox(Vector2 pos, Vector2 size, String[] contentLines, float borderWidth) {
        this(pos, size, String.join("\n", contentLines), borderWidth);
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getSize() {
        return size;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent(String[] content) {
        this.setContent(String.join("\n", content));
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void centreText() {
        this.textAlign(Align.center);
    }

    private void textAlign(int alignment) {
        this.textAlign = alignment;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        if (this.borderWidth > 0) {
            renderer.drawBorder(this.pos, this.size, this.borderColor, this.borderWidth);
        }

        if (this.backgroundColor != null) {
            renderer.drawRect(this.pos, this.size, this.backgroundColor);
        }

        Vector2 textPos;
        if (this.textAlign == Align.left) {
            textPos = new Vector2(
                // Draw text at the left wall of the bounding box, offset by the border width and
                // then "an amount" such that the text isn't right up against the edge, modelled
                // in this case by the log function of the box area as it's relatively suitable
                this.pos.x + this.textOffsetX,
                // Centre text in the box
                this.pos.y + (this.size.y / 2)
            );
        } else {
            textPos = new Vector2(
                this.pos.x + (this.size.x / 2),
                this.pos.y + (this.size.y / 2)
            );
        }

        renderer.drawText(
            this.content,
            textPos,
            this.textColor,
            renderer.calcFontScale(this.fontBounds, this.content, 2f),
            this.textAlign
        );
    }
}
