package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Leaderboard;

import java.util.ArrayList;

// >"MenuScreen"
// >No menu

/**
 * MenuScreen - Does not, in fact, contain a menu
 * Initial screen of the game, shows a leaderboard
 */
public class MenuScreen extends AbstractGameScreen {

    private final Leaderboard leaderboard;

    public MenuScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        // TODO: un-hardcode
        this.leaderboard = new Leaderboard(
            new Vector2(440, 40),
            new Vector2(400, 280)
        );
        this.leaderboard.setValues(new ArrayList<>(Broker.getInstance().getLeaderboard()));
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
