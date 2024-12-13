package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class Cell extends Entity {
    private static final int BORDER_WIDTH = 1;
    // TODO: -> Settings
    private static final float CELL_WIDTH = 720 / 30;

    private Vector2 pos;
    private Vector2 size = new Vector2(CELL_WIDTH, CELL_WIDTH);

    public Cell(Vector2 mapPos, Runnable handleClick) {
        // TODO: Button rendering abstraction
        this.setHandleClick(handleClick);
        // TODO: unhardcode
        pos = new Vector2((1280 - 720) + (mapPos.x * CELL_WIDTH), mapPos.y * CELL_WIDTH);
        size = new Vector2(CELL_WIDTH, CELL_WIDTH);
    }

    // TODO: Button rendering abstraction (?)
    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        // TODO: style
        // Just for UI style I think there'll be a need to replace this border trick
        // with something more formal
        // Or at very least abstract the trick to Renderer so it can be used in
        // arbitrary classes
        // draw black border
        Color borderColor = inputHandler.getMouseInBounds(pos, size) ? Color.WHITE : Color.BLACK;
        renderer.drawRect(pos, size, borderColor);

        // draw green cell
        renderer.drawRect(
            new Vector2(pos.x + BORDER_WIDTH, pos.y + BORDER_WIDTH),
            new Vector2(size.x - (2*BORDER_WIDTH), size.y - (2*BORDER_WIDTH)),
            Color.GREEN
        );

        if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(pos, size)) {
            this.click();
        }
    }
}
