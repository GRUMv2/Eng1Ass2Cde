package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * Achievements
 */
public class Achievements extends Entity {

    private final int SCALE_THRESHOLD = 8;

    private Vector2 pos;
    private Vector2 size;
    private Vector2 contentPos;
    private Vector2 contentSize;


    private TmpTextBox titleBox;
    private TmpTextBox borderBox;
    private TmpTextBox[] achievementBoxes;

    public Achievements(Vector2 posTopL, Vector2 size) {

        this.pos = posTopL;
        this.size = size;

        float titleRatio = 0.1f;
        float boxWidthRatio = 0.95f;

        this.titleBox = new TmpTextBox(
            new Vector2(pos.x, pos.y - (size.y * titleRatio)),
            new Vector2(size.x, size.y * titleRatio),
            "Achievements",
            3f
        );

        this.contentPos = pos.cpy().add((size.x * (1 - boxWidthRatio)) / 2, -(size.y * titleRatio));
        this.contentSize = new Vector2(size.x * boxWidthRatio, size.y * (1 - titleRatio));
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setValues(String[] achievements) {

        float height = Math.min(this.contentSize.y / this.SCALE_THRESHOLD, this.contentSize.y / achievements.length);
        Vector2 boxSize = new Vector2(this.contentSize.x, height);
        Vector2 startPos = new Vector2(this.contentPos.x, this.contentPos.y);
        this.achievementBoxes = new TmpTextBox[achievements.length];
        for (int i = 0; i < achievements.length; i++) {
            startPos.sub(0, height);
            this.achievementBoxes[i] = new TmpTextBox(
                startPos.cpy(),
                boxSize,
                achievements[i],
                0f
            );
        }
        this.borderBox = new TmpTextBox(
            new Vector2(this.pos.x, startPos.y),
            new Vector2(this.size.x, this.pos.y - startPos.y),
            "",
            3f
        );
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        this.titleBox.update(renderer, inputHandler);
        for (TmpTextBox box : achievementBoxes) {
            box.update(renderer, inputHandler);
        }
        this.borderBox.update(renderer, inputHandler);
    }

}
