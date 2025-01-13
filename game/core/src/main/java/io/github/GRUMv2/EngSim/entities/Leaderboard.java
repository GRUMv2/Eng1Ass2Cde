package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;

/**
 * Leaderboard
 */
public class Leaderboard extends Entity {

    private final int LEADERBOARD_LENGTH = 10;
    private float borderWidth = 5f;

    private final float cellWidth = 0.5f;
    private final float contentHeight = 0.8f;
    private final float headerHeight = (1f - contentHeight) / 2f;

    private final Vector2 pos;
    private final Vector2 size;

    private final TmpTextBox titleHeader;
    private final TmpTextBox nameHeader;
    private final TmpTextBox scoreHeader;
    private final TmpTextBox nameCol;
    private final TmpTextBox scoreCol;

    private ArrayList<TmpTextBox> entries;

    public Leaderboard(Vector2 pos, Vector2 size) {

        this.pos = pos;
        this.size = size;

        // They're titled Tmp for a reason
        this.nameCol = new TmpTextBox(
            pos,
            new Vector2(size.x * cellWidth, size.y * contentHeight),
            "",
            this.borderWidth / 2f
        );

        this.scoreCol = new TmpTextBox(
            new Vector2(pos.x + (size.x / 2f), pos.y),
            new Vector2(size.x * cellWidth, size.y * contentHeight),
            "",
            this.borderWidth / 2f
        );

        this.scoreHeader = new TmpTextBox(
            new Vector2(pos.x + (size.x / 2f), pos.y + (size.y * contentHeight)),
            new Vector2(size.x * cellWidth, size.y * headerHeight),
            "",
            this.borderWidth / 2f
        );

        this.nameHeader = new TmpTextBox(
            new Vector2(pos.x, pos.y + (size.y * contentHeight)),
            new Vector2(size.x * cellWidth, size.y * headerHeight),
            "",
            this.borderWidth / 2f
        );

        this.titleHeader = new TmpTextBox(
            new Vector2(pos.x, pos.y + (size.y * (contentHeight + headerHeight))),
            new Vector2(size.x, size.y * headerHeight)
        );

        this.titleHeader.setContent("Leaderboard");
        this.titleHeader.centreText();
        this.nameHeader.setContent("NAME");
        this.scoreHeader.setContent("SCORE");

        this.entries = new ArrayList<>();
    }

    public Leaderboard(Vector2 pos, Vector2 size, float borderWidth) {
        this(pos, size);
        this.borderWidth = borderWidth;
    }

    public void setValues(ArrayList<SimpleImmutableEntry<String, Integer>> leaderboard) {
        Vector2 fullSize = this.nameCol.getSize();
        float boxHeight = fullSize.y / this.LEADERBOARD_LENGTH;
        Vector2 namePos = this.nameCol.getPos().cpy().add(0, fullSize.y - boxHeight);
        Vector2 scorePos = this.scoreCol.getPos().cpy().add(0, fullSize.y - boxHeight);
        Vector2 entrySize = new Vector2(fullSize.x, boxHeight);
        ArrayList<TmpTextBox> entries = new ArrayList<>();
        for (int i = 0; i < this.LEADERBOARD_LENGTH; i++) {
            if (leaderboard.size() < i + 1) {
                break;
            }
            entries.add(
                new TmpTextBox(namePos.cpy(), entrySize, leaderboard.get(i).getKey(), 1f)
            );
            entries.add(
                new TmpTextBox(scorePos.cpy(), entrySize, leaderboard.get(i).getValue().toString(), 1f)
            );
            namePos.sub(0, boxHeight);
            scorePos.sub(0, boxHeight);
        }
        this.entries = entries;
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        renderer.drawBorder(this.pos, this.size, Color.BLACK, this.borderWidth);

        this.titleHeader.update(renderer, inputHandler);
        this.nameHeader.update(renderer, inputHandler);
        this.scoreHeader.update(renderer, inputHandler);
        this.nameCol.update(renderer, inputHandler);
        this.scoreCol.update(renderer, inputHandler);
        for (TmpTextBox box : this.entries) {
            box.update(renderer, inputHandler);
        }
    }

}
