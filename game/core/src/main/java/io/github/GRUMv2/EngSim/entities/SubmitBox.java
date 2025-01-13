package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * SubmitBox
 */
public class SubmitBox extends Entity {
    // Enough overlap with TmpTextBox that it could extend it
    // Or, really, should both extend a common abstract class
    //
    // It's almost like we're missing a proper UI overhaul or something

    private final Vector2 pos;
    private final Vector2 size;

    private final float ratio = 0.75f;

    private final TmpButton button;
    private final InputField field;

    private Color backgroundColor;

    public SubmitBox(Vector2 pos, Vector2 size, Runnable submitAction) {
        this.pos = pos;
        this.size = size;

        this.button = new TmpButton(
            "Submit",
            new Vector2(pos.x + (ratio * size.x), pos.y),
            new Vector2((1 - ratio) * size.x, size.y),
            submitAction
        );
        this.button.setBorderWidth(3f);

        this.field = new InputField(
            pos,
            new Vector2(size.x * ratio, size.y)
        );
        this.field.setBorderWidth(3f);
    }

    public SubmitBox(Vector2 pos, Vector2 size, Runnable submitAction, Color backgroundColor) {
        this(pos, size, submitAction);
        this.setBackgroundColor(backgroundColor);
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        if (this.backgroundColor == null) {
            renderer.drawBorder(this.pos, this.size, Color.BLACK, 3f);
        } else {
            renderer.drawRect(this.pos, this.size, this.backgroundColor);
            renderer.drawBorder(this.pos, this.size, Color.BLACK, 3f);
        }
        this.button.update(renderer, inputHandler);
        this.field.update(renderer, inputHandler);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        // kinda arbitrary but easily reworked
        this.button.setBackgroundColor(Color.WHITE);
        this.field.setBackgroundColor(Color.WHITE);
    }

    public String getValue() {
        return this.field.getContent();
    }

}
