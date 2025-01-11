package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class TmpButton extends TmpTextBox {

    public TmpButton(String text, Vector2 pos, Vector2 size, Runnable handleClick) {
        super(pos, size);
        this.setContent(text);
        this.setHandleClick(handleClick);
    }

    public TmpButton(String text, Vector2 pos, Vector2 size, Runnable handleClick, Color backgroundColor) {
        this(text, pos, size, handleClick);
        this.setBackgroundColor(backgroundColor);
    }

    // TODO: Button rendering abstraction
    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        super.update(renderer, inputHandler);
        if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(this.getPos(), this.getSize())) {
            this.click();
        }
    }
}
