package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import io.github.GRUMv2.EngSim.entities.TmpButton;

public class PauseScreen extends AbstractGameScreen {

    private final TmpButton quitButton;

    public PauseScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);

        this.quitButton = new TmpButton(
            "QUIT",
            new Vector2(540, 160),
            new Vector2(200, 40),
            () -> this.changeEvent(Screens.QUIT),
            Color.GRAY
        );
        this.quitButton.centreText();
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        // placeholder
        if (Gdx.input.justTouched()) {
            this.changeEvent(Screens.GAME);
        }

        renderer.drawText("Click anywhere to resume", new Vector2(640, 360), Color.BLACK, 2f, Align.center);
        this.quitButton.update(renderer, inputHandler);

    }
}
