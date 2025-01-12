package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class Ghost extends DynamicEntity {
    private final float CELL_WIDTH;
    private final float ALPHA = 0.7f;
    private final Building building;

    public Ghost(Building building) {
        super(building.getRelCellsUsed(), building.getColor());
        this.building = building;
        Broker broker = Broker.getInstance();
        this.CELL_WIDTH = (float) broker.getMapSize() / broker.getMapCells();
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        Vector2 mousePos = inputHandler.getMousePos().sub(
            (1280 - 720) % CELL_WIDTH,
            0
        );
        ;


        Vector2 pos = mousePos.sub(
            mousePos.x % CELL_WIDTH,
            mousePos.y % CELL_WIDTH
        );

        Vector2 cellSize = new Vector2(CELL_WIDTH, CELL_WIDTH);

        for (Vector2 relCellOffset : this.getRelCellsUsed()) {
            Vector2 cellPos = new Vector2(
                pos.x + (relCellOffset.x * CELL_WIDTH) + 8,  // why 8? no idea.
                pos.y + (relCellOffset.y * CELL_WIDTH)
            );
            renderer.drawRect(cellPos, cellSize, this.getColor(), this.ALPHA);
        }
    }

    public Building getBuilding() {
        return building;
    }
}
