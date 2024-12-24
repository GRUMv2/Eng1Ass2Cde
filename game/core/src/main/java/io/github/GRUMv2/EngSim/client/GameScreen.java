package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.GameMap;

// To remove:
import io.github.GRUMv2.EngSim.entities.Gym;
import io.github.GRUMv2.EngSim.entities.HallsAccommadation;
import io.github.GRUMv2.EngSim.entities.LectureHall;
import io.github.GRUMv2.EngSim.entities.Pub;
import io.github.GRUMv2.EngSim.entities.Restaurant;
import io.github.GRUMv2.EngSim.entities.UI;

public class GameScreen extends AbstractGameScreen {

    private Class<? extends Building> buildingToPlace = null;
    private GameMap map;
    private UI ui;
    private Broker broker;

    private float tmpTimer = 0f;

    public GameScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        broker = Broker.getInstance();
        map = new GameMap(this, broker);
        ui = new UI(this, broker);
    }

    public void update(Renderer renderer, InputHandler inputHandler) {

        // XXX: PLACEHOLDER
        tmpTimer += Gdx.graphics.getDeltaTime();
        broker.setTime(tmpTimer);

        if (broker.isGameComplete()) {
            this.changeEvent(Screens.END);
            return;
        }

        map.update(renderer, inputHandler);
        ui.update(renderer, inputHandler);
    }

    public void togglePause() {
        this.changeEvent(Screens.PAUSE);
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
    // can be decoupled from GameScreen() into a dedicated grid data type, then it may
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

        broker.placeBuilding(building);
    }
}
