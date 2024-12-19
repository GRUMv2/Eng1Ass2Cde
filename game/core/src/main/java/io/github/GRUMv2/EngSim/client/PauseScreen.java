package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class PauseScreen extends AbstractGameScreen {

    public PauseScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        // placeholder
        if (Gdx.input.justTouched()) {
            this.changeEvent(Screens.GAME);
        }

        renderer.drawText("Click anywhere to resume", new Vector2(640, 360), Color.BLACK, 2f, Align.center);

    }
}
