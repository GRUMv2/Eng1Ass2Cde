package io.github.GRUMv2.EngSim.entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * NoticeBox
 */
public class NoticeBox extends Entity {
    private final int MAX_ITEMS = 5;
    private float borderWidth = 4f;

    private TmpTextBox header;
    private TmpTextBox content;
    private TmpTextBox[] itemBoxes;
    private ArrayList<String> items;

    public NoticeBox(Vector2 pos, Vector2 size) {
        this.header = new TmpTextBox(
            new Vector2(pos.x, pos.y + (size.y * 0.85f)),
            new Vector2(size.x, size.y * 0.15f),
            "",
            this.borderWidth
        );
        this.header.setContent("Notices");

        this.content = new TmpTextBox(
            new Vector2(pos.x, pos.y),
            new Vector2(size.x, size.y * 0.85f),
            "",
            this.borderWidth
        );

        this.itemBoxes = new TmpTextBox[MAX_ITEMS];
        this.items = new ArrayList<>();

        float spacing = (size.y * 0.85f) / MAX_ITEMS;
        for (int i = 0; i < MAX_ITEMS; i++) {
            itemBoxes[i] = new TmpTextBox(
                new Vector2(pos.x, pos.y + (spacing * i)),
                new Vector2(size.x, spacing),
                "",
                borderWidth/2
            );
        }
    }

    public void put(String text) {
        items.add(text);
        for (int i = 0; i < Math.min(items.size(), this.MAX_ITEMS); i++) {
            itemBoxes[this.MAX_ITEMS - 1 - i].setContent(items.get(Math.max(items.size() - 1 - i, 0)));
        }
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        this.header.update(renderer, inputHandler);
        this.content.update(renderer, inputHandler);

        for (int i = 0; i < Math.min(items.size(), this.MAX_ITEMS); i++) {
            itemBoxes[this.MAX_ITEMS - 1 - i].update(renderer, inputHandler);
        }
    }

}
