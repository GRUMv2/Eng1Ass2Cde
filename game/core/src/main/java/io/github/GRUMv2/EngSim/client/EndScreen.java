package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

// TODO: Dedicated GameOverScreen
//      - Scoreboard
//      - Leaderboard
//      - Achievements

public class EndScreen extends AbstractGameScreen {

    public EndScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        // placeholder
        if (Gdx.input.justTouched()) {
            System.out.println("aAAAaaaAAaaaAAaAA");
            this.changeEvent(Screens.QUIT);
        }

        renderer.drawText("Game Over", new Vector2(640, 360), Color.BLACK, 2f, Align.center);
    }
}
