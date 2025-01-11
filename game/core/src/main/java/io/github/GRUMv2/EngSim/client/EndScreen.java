package io.github.GRUMv2.EngSim.client;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Leaderboard;
import io.github.GRUMv2.EngSim.entities.SubmitBox;
import io.github.GRUMv2.EngSim.entities.TmpButton;

// TODO: Dedicated GameOverScreen
//      - Scoreboard
//      - Leaderboard
//      - Achievements

public class EndScreen extends AbstractGameScreen {

    private Leaderboard leaderboard;
    private SubmitBox submitBox;
    private TmpButton quitButton;

    public EndScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        // TODO: unhardcode

        this.submitBox = new SubmitBox(
            new Vector2(70, 440),
            new Vector2(400, 40),
            () -> this.submitScore()
        );

        this.leaderboard = new Leaderboard(
            new Vector2(70, 40),
            new Vector2(400, 280)
        );
        this.leaderboard.setValues(new ArrayList<SimpleImmutableEntry<String, Integer>>(Broker.getInstance().getLeaderboard()));

        this.quitButton = new TmpButton(
            "QUIT",
            new Vector2(540, 160),
            new Vector2(200, 40),
            () -> this.changeEvent(Screens.QUIT),
            Color.GRAY
        );

        this.quitButton.centreText();

    }

    public boolean submitScore() {
        String input = this.submitBox.getValue();
        if (input.length() < 1) {
            return false;
        }
        Broker broker = Broker.getInstance();
        broker.updateLeaderboard(input, Math.round(broker.getStudentSatisfaction() * 1000));
        this.leaderboard.setValues(new ArrayList<SimpleImmutableEntry<String, Integer>>(Broker.getInstance().getLeaderboard()));
        return true;
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        renderer.drawText("Game Over", new Vector2(640, 580), Color.BLACK, 2f, Align.center);
        this.leaderboard.update(renderer, inputHandler);
        this.submitBox.update(renderer, inputHandler);
        this.quitButton.update(renderer, inputHandler);
    }
}
