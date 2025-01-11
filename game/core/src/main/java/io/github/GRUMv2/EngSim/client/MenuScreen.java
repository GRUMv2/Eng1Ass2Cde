package io.github.GRUMv2.EngSim.client;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Leaderboard;

// >"MenuScreen"
// >No menu

/**
 * MenuScreen
 */
public class MenuScreen extends AbstractGameScreen {

    private Leaderboard leaderboard;

    private Vector2[] anywhere = new Vector2[] { // TODO: remove
        new Vector2(30, 20),
        new Vector2(60, 20)
    };

    public MenuScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        // TODO: unhardcode
        this.leaderboard = new Leaderboard(
            new Vector2(440, 40),
            new Vector2(400, 280)
        );
        this.leaderboard.setValues(new ArrayList<SimpleImmutableEntry<String, Integer>>(Broker.getInstance().getLeaderboard()));
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        // placeholder
        if (Gdx.input.justTouched() /* TODO: remove */ && inputHandler.getMouseInBounds(anywhere[0], anywhere[1])) {
            this.changeEvent(Screens.GAME);
        }

        renderer.drawText("Click anywhere to start", new Vector2(640, 540), Color.BLACK, 2f, Align.center);
        renderer.drawText("anywhere", new Vector2(60, 30), Color.ORANGE, 1f, Align.center); // TODO: remove
        this.leaderboard.update(renderer, inputHandler);
    }
}
