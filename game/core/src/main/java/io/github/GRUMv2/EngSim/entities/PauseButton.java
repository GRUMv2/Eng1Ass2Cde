package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;


public class PauseButton extends Button {

    public PauseButton(Vector2 pos, Runnable handleClick) {
        super(
            pos,
            new Vector2(30, 30),
            handleClick
        );
    }

    // TODO: Button rendering abstraction
    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        renderer.drawRect(this.getPos(), this.getSize(), Color.LIGHT_GRAY);
        renderer.drawRect(
            new Vector2(this.getPos().x + 5, this.getPos().y + 5),
            new Vector2(7, 20),
            Color.WHITE
        );
        renderer.drawRect(
            new Vector2(this.getPos().x + 17, this.getPos().y + 5),
            new Vector2(7, 20),
            Color.WHITE
        );

        if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(this.getPos(), this.getSize())) {
            this.click();
        }
    }
}
