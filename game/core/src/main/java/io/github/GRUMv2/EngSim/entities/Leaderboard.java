package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * Leaderboard
 */
public class Leaderboard extends Entity {

    private float borderWidth = 3f;

    private TmpTextBox nameHeader;
    private TmpTextBox scoreHeader;
    private TmpTextBox nameCol;
    private TmpTextBox scoreCol;

    public Leaderboard(Vector2 pos, Vector2 size) {


        // They're titled Tmp for a reason
        this.nameCol = new TmpTextBox(
            pos,
            new Vector2(size.x * 0.5f, size.y * 0.9f),
            "",
            this.borderWidth
        );

        this.scoreCol = new TmpTextBox(
            new Vector2(pos.x + (size.x / 2), pos.y),
            new Vector2(size.x * 0.5f, size.y * 0.9f),
            "",
            this.borderWidth
        );

        this.scoreHeader = new TmpTextBox(
            new Vector2(pos.x + (size.x / 2), pos.y + (size.y * 0.9f)),
            new Vector2(size.x * 0.5f, size.y * 0.1f),
            "",
            this.borderWidth
        );

        this.nameHeader = new TmpTextBox(
            new Vector2(pos.x, pos.y + (size.y * 0.9f)),
            new Vector2(size.x * 0.5f, size.y * 0.1f),
            "",
            this.borderWidth
        );

        this.nameHeader.setContent("NAME");
        this.scoreHeader.setContent("SCORE");

    }

    public Leaderboard(Vector2 pos, Vector2 size, float borderWidth) {
        this(pos, size);
        this.borderWidth = borderWidth;
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        this.nameHeader.update(renderer, inputHandler);
        this.scoreHeader.update(renderer, inputHandler);
        this.nameCol.update(renderer, inputHandler);
        this.scoreCol.update(renderer, inputHandler);
    }

}
