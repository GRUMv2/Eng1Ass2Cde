package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.client.GameScreen;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class GameMap extends Entity {
    private final int CELLS_PER_ROW;

    private Broker broker;
    private GameScreen game;
    private Cell[] cells;

    public GameMap(GameScreen game, Broker broker) {
        this.broker = broker;
        this.CELLS_PER_ROW = broker.getMapCells();
      
        this.game = game;

        this.cells = new Cell[CELLS_PER_ROW * CELLS_PER_ROW];
        // TODO: something about this
        broker.placeObstacle(
            new Water(
                new Vector2(5, 5),
                new Vector2[]{
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
                new Vector2[]{
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

        for (int i = 0; i < this.CELLS_PER_ROW; i++) {
            for (int j = 0; j < this.CELLS_PER_ROW; j++) {
                Vector2 cellPos = new Vector2(i, j);
                cells[i * this.CELLS_PER_ROW + j] = new Cell(
                    cellPos,
                    // TODO: see note in Game()
                    () -> game.handleCellClick(cellPos)
                );
            }
        }
    }

    ;

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        // draw the map
        Vector2 pos = new Vector2(1280 - 720, 0);
        Vector2 size = new Vector2(720, 720);
        Color color = Color.valueOf("c2c2c2");
        renderer.drawRect(pos, size, color);

        if (this.game.isInDialog()) {
            renderer.drawRect(pos, size, Color.GREEN);
        } else {
            for (Cell cell : cells) {
                cell.update(renderer, inputHandler);
            }
        }

        for (ForegroundEntity entity : this.broker.getEntities()) {
            entity.update(renderer, inputHandler);
        }
    }

    public Vector2 getCellAtPos(Vector2 pos) {
        return new Vector2(
            (float) Math.floor((pos.x - 560f) / (720f / this.CELLS_PER_ROW)),
            (float) Math.floor(pos.y / (720f / this.CELLS_PER_ROW))
        );
    }
}
