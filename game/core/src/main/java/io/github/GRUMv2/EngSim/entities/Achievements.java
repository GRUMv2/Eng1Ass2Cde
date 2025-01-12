package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * Achievements
 */
public class Achievements extends Entity {

    private final int SCALE_THRESHOLD = 8;
    private final float borderWidth = 5f;

    private Vector2 pos;
    private Vector2 size;
    private Vector2 contentSize;


    private TmpTextBox titleBox;
    private TmpTextBox borderBox;
    private TmpTextBox[] achievementBoxes;

    public Achievements(Vector2 pos, Vector2 size) {

        this.pos = pos;
        this.size = size;

        float titleRatio = 0.1f;

        this.titleBox = new TmpTextBox(
            new Vector2(pos.x, pos.y + (size.y * (1 - titleRatio))),
            new Vector2(size.x, size.y * titleRatio),
            "Achievements",
            borderWidth
        );
        this.titleBox.centreText();

        this.borderBox = new TmpTextBox(
            pos,
            size,
            "",
            borderWidth
        );

        this.contentSize = new Vector2(size.x, size.y * (1 - titleRatio));
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
        Vector2 startPos = new Vector2(this.pos.x, this.pos.y + (Math.max(this.SCALE_THRESHOLD, achievements.length) * height));
        this.achievementBoxes = new TmpTextBox[achievements.length];
        for (int i = 0; i < achievements.length; i++) {
            startPos.sub(0, height);
            this.achievementBoxes[i] = new TmpTextBox(
                startPos.cpy(),
                boxSize,
                achievements[i],
                borderWidth / 2
            );
        }

        this.titleBox.setContent("Achievements (" + achievements.length + ")");
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        for (TmpTextBox box : achievementBoxes) {
            box.update(renderer, inputHandler);
        }
        this.titleBox.update(renderer, inputHandler);
        this.borderBox.update(renderer, inputHandler);
    }

}
