package io.github.GRUMv2.EngSim.broker;

import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;
import io.github.GRUMv2.EngSim.entities.ForegroundEntity;
import io.github.GRUMv2.EngSim.entities.Obstacle;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Broker (Singleton)
 */
public final class Broker {
    private static Broker instance;

    private final int MAP_CELLS = 30;
    private final int MAP_SIZE = 720;
    private final float REALTIME_LENGTH = 300f;

    private volatile String timeElapsedString = "f";
    private volatile float timeElapsed = 0;
    private volatile float studentSatisfaction = 0f;
    private volatile float studentNumbers = 0f;
    private volatile float staffSatisfaction = 0f;
    private volatile float staffNumbers = 0f;
    private volatile long money = 0;
    private volatile int income = 0;
    private volatile int gamePausedNumber = 0;
    private volatile int pendingSpendMoney = 0;

    // thread-safe
    private volatile boolean gameComplete = false;

    private ConcurrentHashMap<Available, Integer> buildingCount;
    private ConcurrentHashMap<Vector2, ForegroundEntity> grid;
    private CopyOnWriteArrayList<SimpleImmutableEntry<String, Integer>> leaderboard;
    private CopyOnWriteArrayList<String> achievementAwarded = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<ForegroundEntity> entities;

    private Broker() {
        this.buildingCount = new ConcurrentHashMap<>();
        this.grid = new ConcurrentHashMap<>();
        this.leaderboard = new CopyOnWriteArrayList<>();
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

    public int getMapSize() {
        return this.MAP_SIZE;
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public int getTimeLeft() {
        return (int) (REALTIME_LENGTH - timeElapsed);
    }

    public String getTimeLeftString() {
        int left = this.getTimeLeft();
        float minLeft = (float) Math.floor(left / 60f);
        return timeElapsedString + " (" + (int) minLeft + ":" + String.format("%02d", (int) Math.floor(left - (minLeft * 60f))) + ")";
    }

    public void serverPush(
        float timeElapsed,
        String timeElapsedString,
        float studentSatisfaction,
        float studentNumbers,
        float staffSatisfaction,
        float staffNumbers,
        long money,
        int income
    ) {
        if (timeElapsed >= REALTIME_LENGTH) {
            this.gameComplete = true;
        }

        this.timeElapsed = timeElapsed;
        this.timeElapsedString = timeElapsedString;
        this.studentSatisfaction = studentSatisfaction;
        this.studentNumbers = studentNumbers;
        this.staffSatisfaction = staffSatisfaction;
        this.staffNumbers = staffNumbers;
        this.money = money;
        this.income = income;
    }

    public float getStudentSatisfaction() {
        return studentSatisfaction;
    }

    public float getStudentNumbers() {
        return studentNumbers;
    }

    public float getStaffSatisfaction() {
        return staffSatisfaction;
    }

    public float getStaffNumbers() {
        return staffNumbers;
    }

    public long getMoney() {
        return money;
    }

    public int getIncome() {
        return income;
    }

    public synchronized void spendMoney(int spent) {
        this.pendingSpendMoney += spent;
    }

    public int getPendingSpendMoney() {
        return pendingSpendMoney;
    }

    public synchronized int reconcileFunds() {
        int spent = this.pendingSpendMoney;
        this.pendingSpendMoney = 0;
        return spent;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public synchronized CopyOnWriteArrayList<ForegroundEntity> getEntities() {
        return entities;
    }

    public synchronized ConcurrentHashMap<Vector2, ForegroundEntity> getGrid() {
        return grid;
    }

    public ConcurrentHashMap<Available, Integer> getBuildingCount() {
        return this.buildingCount;
    }

    public int getBuildingCount(Available buildingType) {
        return this.buildingCount.getOrDefault(buildingType, 0);
    }

    public int getTotalBuildings() {
        int count = 0;
        for (int i : this.buildingCount.values()) {
            count += i;
        }
        return count;
    }

    private Vector2[] relativeCellsToAbsolute(Vector2 mapPos, Vector2[] relCells) {
        Vector2[] cells = new Vector2[relCells.length];
        int i;
        for (i = 0; i < relCells.length; i++) {
            cells[i] = new Vector2(mapPos.x + relCells[i].x,
                mapPos.y + relCells[i].y);
        }
        return cells;
    }

    private Vector2[] relativeCellsToAbsolute(ForegroundEntity entity) {
        return this.relativeCellsToAbsolute(
            entity.getMapPos(),
            entity.getRelCellsUsed()
        );
    }

    private boolean cellInBounds(Vector2 cell) {
        return cell.x < this.MAP_CELLS &&
            cell.y < this.MAP_CELLS &&
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

    public boolean isPlaceable(ForegroundEntity entity, Vector2 position) {
        return this.checkCoordsFree(this.relativeCellsToAbsolute(position, entity.getRelCellsUsed()), entity);
    }

    private boolean placeEntity(ForegroundEntity entity) {
        Vector2[] cells = this.relativeCellsToAbsolute(entity);
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
        this.spendMoney(building.getCost());
        this.buildingCount.put(Available.get(building.getClass()), this.getBuildingCount(Available.get(building.getClass())) + 1);

        return true;
    }

    private Building removeBuilding(Vector2 click) {
        ForegroundEntity entity = this.grid.get(click);
        if (!(entity instanceof Building)) {
            return null;
        }
        Building building = (Building) entity;
        Vector2[] cells = this.relativeCellsToAbsolute(building);
        for (Vector2 cell : cells) {
            this.grid.remove(cell);
        }
        this.entities.remove(building);
        return building;
    }

    public Building destroyBuilding(Vector2 click) {
        Building building = this.removeBuilding(click);
        if (building == null) {
            return null;
        }
        this.buildingCount.put(Available.get(building.getClass()), this.getBuildingCount(Available.get(building.getClass())) - 1);
        return building;
    }

    private boolean validateScore(int score) {
        if (score < 0) {
            return false;
        }
        /**
         * else if (score > this.MAX_SCORE) {
         * return false;
         * } ...
         */
        return true;
    }

    public boolean updateLeaderboard(String name, int score) {
        if (!this.validateScore(score)) {
            return false;
        }
        SimpleImmutableEntry<String, Integer> newEntry = new SimpleImmutableEntry<String, Integer>(name, score);
        for (int i = 0; i < this.leaderboard.size(); i++) {
            if (score > this.leaderboard.get(i).getValue()) {
                this.leaderboard.add(i, newEntry);
                return true;
            }
        }
        this.leaderboard.add(newEntry);
        return true;
    }

    public CopyOnWriteArrayList<SimpleImmutableEntry<String, Integer>> getLeaderboard() {
        return leaderboard;
    }

    public int getHighScore() {
        // leaderboard presumed to be sorted at rest
        return this.leaderboard.get(0).getValue();
    }

    public void achievementAwarded(String achievement) {
        achievementAwarded.add(achievement);
    }

    public int getGamePausedNumber() {
        return gamePausedNumber;
    }

    public void incrementGamePausedCount() {
        this.gamePausedNumber += 1;
    }
}
