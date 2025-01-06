package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class TmpButton extends Button {
    private String text;

    public TmpButton(String text, Vector2 pos, Runnable handleClick) {
        super(pos, new Vector2(250, 60), handleClick);
        this.text = text;
    }

    // TODO: Button rendering abstraction
    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        // TODO: burn with fire
        renderer.drawRect(this.getPos(), this.getSize(), Color.GRAY);
        renderer.drawText(this.text, new Vector2(this.getPos().x + 10, this.getPos().y + this.getSize().y / 2), Color.BLACK, 1.5f);

        if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(this.getPos(), this.getSize())) {
            this.click();
        }
    }
}
