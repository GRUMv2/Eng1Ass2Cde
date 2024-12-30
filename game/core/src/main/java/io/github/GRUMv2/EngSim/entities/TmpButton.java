package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class TmpButton extends Button {
    private String text;
    private Vector2 pos;

    public TmpButton(String text, Vector2 pos, Runnable handleClick) {
        super(handleClick);
        this.pos = pos;
        this.text = text;
    }

    // TODO: Button rendering abstraction
    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        // TODO: burn with fire
        Vector2 size = new Vector2(250, 60);
        renderer.drawRect(pos, size, Color.GRAY);
        renderer.drawText("Action: " + this.text, new Vector2(pos.x + 10, pos.y + size.y / 2), Color.BLACK, 1.5f);

        if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(pos, size)) {
            this.click();
        }
    }
}
