package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.GameMap;

// To remove:
import io.github.GRUMv2.EngSim.entities.Gym;
import io.github.GRUMv2.EngSim.entities.HallsAccommadation;
import io.github.GRUMv2.EngSim.entities.LectureHall;
import io.github.GRUMv2.EngSim.entities.Pub;
import io.github.GRUMv2.EngSim.entities.Restaurant;
import io.github.GRUMv2.EngSim.entities.UI;

public class Game extends AbstractGameScreen {
    // TODO: -> Settings
    private static final float REALTIME_LENGTH = 300f;
    private static final float GAMETIME_LENGTH = 10f;
    private static final String GAMETIME_UNIT = "year";

    private float timeElapsed = 0f;  // TODO: -> Server
    private boolean paused = false; // TODO: -> Client
    private Class<? extends Building> buildingToPlace = null;
    private GameMap map;
    private UI ui;

    public Game(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        map = new GameMap(this);
        ui = new UI(this);
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        // TODO: PauseScreen
        if (!paused) {
            timeElapsed += Gdx.graphics.getDeltaTime();
        }

        // TODO: Time is a server job. Replace this with calls to server
        boolean gameComplete = timeElapsed >= REALTIME_LENGTH;
        if (gameComplete) {
            // TODO: Dedicated GameOverScreen
            //      - Scoreboard
            //      - Leaderboard
            //      - Achievements
            renderGameOverScreen(renderer);
            return;
        }

        map.update(renderer, inputHandler);
        ui.update(renderer, inputHandler);
    }

    // TODO: Time is a server job. Replace this with calls to server
    public String getTimeLeftString() {
        float progress = timeElapsed / REALTIME_LENGTH;
        float gameTimeElapsed = progress * GAMETIME_LENGTH;
        float gameTimeLeft = GAMETIME_LENGTH - gameTimeElapsed;
        return String.format("%.2f %ss left", gameTimeLeft, GAMETIME_UNIT);
    }

    // TODO: PauseScreen
    // This likely will result in the toggle of pausing of the game to be handled
    // by the parent Client() class instead
    public void togglePause() {
        paused = !paused;
    }

    public void setBuildingToPlace(Class<? extends Building> buildingType) {
        if (buildingType == buildingToPlace) {
            buildingToPlace = null;
        } else {
            buildingToPlace = buildingType;
        }
    }

    public Class<? extends Building> getBuildingToPlace() {
        return buildingToPlace;
    }

    // TODO: remove
    // What this is replaced by depends heavily on how we want to handle
    // building objects
    // Potentially a BuildingManager job but alternatively, if the tracking of objects
    // can be decoupled from Game() into a dedicated grid data type, then it may
    // make more sense to let the buttons themselves be able to create their objects
    public void handleCellClick(Vector2 cellPos) {
        Building building;
        if (buildingToPlace == Pub.class) {
            building = new Pub(cellPos);
        }
        else if (buildingToPlace == HallsAccommadation.class) {
            building = new HallsAccommadation(cellPos);
        }
        else if (buildingToPlace == Restaurant.class) {
            building = new Restaurant(cellPos);
        }
        else if (buildingToPlace == LectureHall.class) {
            building = new LectureHall(cellPos);
        }
        else if (buildingToPlace == Gym.class) {
            building = new Gym(cellPos);
        }
        else {
            return;
        }

        if (!map.getCanPlace(building)) {
            return;
        }

        map.placeBuilding(building);
    }

    public int getBuildingCount() {
        return map.getBuildingCount();
    }

    // TODO: remove
    private void renderGameOverScreen(Renderer renderer) {
        Vector2 pos = new Vector2(500, 700);
        renderer.drawText("Game Over!",pos, Color.RED, 4f);
    }
}
