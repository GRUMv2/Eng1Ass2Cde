package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.client.GameScreen;
import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.client.InputHandler;

public class GameMap extends Entity {
    // TODO -> Settings
    private Cell[] cells;
    // TODO: Map data
    // Similarly to attributes of Game, these might be better suited to a
    // dedicated class that keeps track of game state
    // This both prevents server having to reach all the way across into client
    // and means that the GameMap Entity class is more concise in purpose

    private Broker broker;

    public GameMap(GameScreen game, Broker broker) {
        this.broker = broker;
        final int CELLS_PER_ROW = broker.getMapCells();
        this.cells = new Cell[CELLS_PER_ROW * CELLS_PER_ROW];
        // TODO: something about this
        broker.placeObstacle(
            new Water(
                new Vector2(5, 5),
                new Vector2[] {
                    new Vector2(1, 0),
                    new Vector2(2, 0),
                    new Vector2(3, 0),

                    new Vector2(0, 1),
                    new Vector2(1, 1),
                    new Vector2(2, 1),
                    new Vector2(3, 1),
                    new Vector2(4, 1),
                    new Vector2(5, 1),

                    new Vector2(3, 2),
                    new Vector2(4, 2),
                    new Vector2(5, 2)
                }
            )
        );
        broker.placeObstacle(
            new Water(
                new Vector2(20, 25),
                new Vector2[] {
                    new Vector2(0, 0),
                    new Vector2(1, 0),
                    new Vector2(0, -1),
                    new Vector2(1, -1),
                    new Vector2(0, -2),
                    new Vector2(1, -2),
                    new Vector2(1, -3),
                    new Vector2(2, -3),
                    new Vector2(2, -4),
                    new Vector2(3, -4),
                    new Vector2(2, -5),
                    new Vector2(3, -5),
                    new Vector2(2, -6),
                    new Vector2(3, -6),
                    new Vector2(3, -7),
                }
            )
        );

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
