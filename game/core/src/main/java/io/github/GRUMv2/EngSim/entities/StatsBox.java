package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * NoticeBox
 */
public class StatsBox extends Entity {
    private float borderWidth = 4f;

    private TmpTextBox header;
    private TmpTextBox content;
    private TmpTextBox[] itemBoxes;

    public StatsBox(Vector2 pos, Vector2 size) {
        this.header = new TmpTextBox(
            new Vector2(pos.x, pos.y + (size.y * 0.85f)),
            new Vector2(size.x, size.y * 0.15f),
            "",
            this.borderWidth
        );
        this.header.setContent("Statistics");

        this.content = new TmpTextBox(
            new Vector2(pos.x, pos.y),
            new Vector2(size.x, size.y * 0.85f),
            "",
            this.borderWidth
        );

        int items = 5;

        this.itemBoxes = new TmpTextBox[items];

        float spacing = (size.y * 0.85f) / items;
        for (int i = 0; i < items; i++) {
            itemBoxes[i] = new TmpTextBox(
                new Vector2(pos.x, pos.y + (spacing * i)),
                new Vector2(size.x, spacing),
                "",
                borderWidth/2
            );
        }
    }

    public void setStats(
            long balance,
            int income,
            float students,
            float studentSatisfaction,
            float staff,
            float staffSatisfaction) {

        this.itemBoxes[4].setContent("Balance: £" + (balance == 0 ? 0 : balance / 1000 + "K") + " (" + (income >= 0 ? "+£" + (income / 1000) + "K" : "-£" + (-income / 1000) + "K") + " )");
        this.itemBoxes[3].setContent("Students: " + students);
        this.itemBoxes[2].setContent("Student Satisfaction: " + Math.round(studentSatisfaction * 100) + "%");
        this.itemBoxes[1].setContent("Staff: " + staff);
        this.itemBoxes[0].setContent("Staff Satisfaction: " + Math.round(staffSatisfaction * 100) + "%");
    }

    public void setStats(long balance, int income) {
        this.itemBoxes[4].setContent("Balance: £" + (balance == 0 ? 0 : balance / 1000 + "K") + " (" + (income >= 0 ? "+£" + (income / 1000) + "K" : "-£" + (-income / 1000) + "K") + " )");
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        this.header.update(renderer, inputHandler);
        this.content.update(renderer, inputHandler);

        for (int i = 0; i < this.itemBoxes.length; i++) {
            itemBoxes[i].update(renderer, inputHandler);
        }
    }

}
