package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.client.GameScreen;
import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.client.InputHandler;

public class GameMap extends Entity {

    private Broker broker;
    private Cell[] cells;

    public GameMap(GameScreen game, Broker broker) {
        this.broker = broker;
        final int CELLS_PER_ROW = broker.getMapCells();
        this.cells = new Cell[CELLS_PER_ROW * CELLS_PER_ROW];

        for (Obstacle obstacle : MapLoader.gibMap()) {
            broker.placeObstacle(obstacle);
        }

        for (int i = 0; i < CELLS_PER_ROW; i++) {
            for (int j = 0; j < CELLS_PER_ROW; j++) {
                Vector2 cellPos = new Vector2(i, j);
                cells[i * CELLS_PER_ROW + j] = new Cell(
                    cellPos,
                // TODO: see note in Game()
                () -> game.handleCellClick(cellPos)
                );
            }
        }
    };

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        // draw the map
        Vector2 pos = new Vector2(1280 - 720, 0);
        Vector2 size = new Vector2(720, 720);
        Color color = Color.valueOf("c2c2c2");
        renderer.drawRect(pos, size, color);

        for (Cell cell : cells) {
            cell.update(renderer, inputHandler);
        }

        for (ForegroundEntity entity : this.broker.getEntities()) {
            entity.update(renderer, inputHandler);
        }
    }
}
