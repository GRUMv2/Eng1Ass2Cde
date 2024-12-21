package io.github.GRUMv2.EngSim.broker;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.ForegroundEntity;
import io.github.GRUMv2.EngSim.entities.Obstacle;

/**
 * Broker (Singleton)
 */
public final class Broker {
    private static Broker instance;

    private final int MAP_CELLS = 30;
    private final float REALTIME_LENGTH = 300f;
    private final float GAMETIME_LENGTH = 10f;
    private final String GAMETIME_UNIT = "year";


    private volatile float timeElapsed = 0f; // Delta time elapsed since start
    private volatile boolean gameComplete = false;

    // thread-safe
    private ConcurrentHashMap<Building, Integer> buildingCount;
    private ConcurrentHashMap<Vector2, ForegroundEntity> grid;

    private CopyOnWriteArrayList<ForegroundEntity> entities;

    private Broker() {
        this.buildingCount = new ConcurrentHashMap<>();
        this.grid = new ConcurrentHashMap<>();
        this.entities = new CopyOnWriteArrayList<>();
    }

    public synchronized static Broker getInstance() {
        if (instance == null) {
            instance = new Broker();
        }
        return instance;
    }

    public int getMapCells() {
        return this.MAP_CELLS;
    }

    public String getTimeLeftString() {
        float progress = (timeElapsed / REALTIME_LENGTH);
        float gameTimeElapsed = progress * GAMETIME_LENGTH;
        float gameTimeLeft = GAMETIME_LENGTH - gameTimeElapsed;
        return String.format("%.2f %ss left", gameTimeLeft, GAMETIME_UNIT);
    }

    public boolean setTime(float time) {
        //if (time < this.timeElapsed) {
        //    return false;
        //}
        this.timeElapsed = time;
        if (time >= REALTIME_LENGTH) {
            this.gameComplete = true;
        }
        return true;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public CopyOnWriteArrayList<ForegroundEntity> getEntities() {
        return entities;
    }

    public ConcurrentHashMap<Vector2, ForegroundEntity> getGrid() {
        return grid;
    }

    public ConcurrentHashMap<Building, Integer> getBuildingCount() {
        return this.buildingCount;
    }

    public int getBuildingCount(Building building) {
        return this.buildingCount.getOrDefault(building, 0);
    }

    public int getTotalBuildings() {
        int count = 0;
        for (int i : this.buildingCount.values()) {
            count += i;
        }
        return count;
    }

    private boolean cellInBounds(Vector2 cell) {
        return cell.x <= this.MAP_CELLS &&
                cell.y <= this.MAP_CELLS &&
                cell.x >= 0 &&
                cell.y >= 0;
    }

    private boolean checkCoordsFree(Vector2[] interlinked, ForegroundEntity self) {
        for (Vector2 cell : interlinked) {
            if (!cellInBounds(cell)) {
                return false;
            }
            ForegroundEntity gridCell = this.grid.getOrDefault(cell, null);
            if (gridCell == self || gridCell == null) {
                continue;
            }
            return false;
        }
        return true;
    }

    private boolean checkCoordsFree(Vector2[] interlinked) {
        return this.checkCoordsFree(interlinked, null);
    }

    private boolean placeEntity(ForegroundEntity entity) {
        Vector2 entityPos = entity.getMapPos();
        Vector2[] entityRelCells = entity.getRelCellsUsed();
        Vector2[] cells = new Vector2[entityRelCells.length];
        int i;
        for (i = 0; i < entityRelCells.length; i++) {
            System.out.println("$ " + entityPos + " " + entityRelCells[i]);
            cells[i] = new Vector2(entityPos.x + entityRelCells[i].x,
                                    entityPos.y + entityRelCells[i].y);
        }
        if (!checkCoordsFree(cells)) {
            return false;
        }
        for (Vector2 cell : cells) {
            this.grid.put(cell, entity);
        }
        this.entities.add(entity);
        return true;
    }

    public boolean placeObstacle(Obstacle obstacle) {
        return placeEntity(obstacle);
    }

    public boolean placeBuilding(Building building) {
        if (!placeEntity(building)) {
            return false;
        }
        System.out.println("placed");
        this.buildingCount.put(building, this.getBuildingCount(building) + 1);
        return true;
    }

}
