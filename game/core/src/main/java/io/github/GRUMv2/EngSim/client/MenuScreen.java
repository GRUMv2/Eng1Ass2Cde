package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import io.github.GRUMv2.EngSim.entities.Leaderboard;

// >"MenuScreen"
// >No menu

/**
 * MenuScreen
 */
public class MenuScreen extends AbstractGameScreen {

    private Leaderboard leaderboard;

    public MenuScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        // TODO: unhardcode
        this.leaderboard = new Leaderboard(
            new Vector2(440, 40),
            new Vector2(400, 280)
        );
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        // placeholder
        if (Gdx.input.justTouched()) {
            this.changeEvent(Screens.GAME);
        }

        renderer.drawText("Click anywhere to start", new Vector2(640, 540), Color.BLACK, 2f, Align.center);
        this.leaderboard.update(renderer, inputHandler);
    }
}
