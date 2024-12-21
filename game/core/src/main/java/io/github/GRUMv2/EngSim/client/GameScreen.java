package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.BuildingFactory;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;
import io.github.GRUMv2.EngSim.entities.GameMap;
import io.github.GRUMv2.EngSim.entities.UI;

public class GameScreen extends AbstractGameScreen {

    private Available buildingToPlace = null;
    private Modes mode;
    private GameMap map;
    private UI ui;
    private Broker broker;
    private BuildingFactory builder;

    private float tmpTimer = 0f;

    public enum Modes {
        NORMAL("Normal"),
        DESTROY("Destroy"),
        MOVE("Move");

        private String text;

        private Modes(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

    public GameScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        broker = Broker.getInstance();
        builder = BuildingFactory.getInstance();
        map = new GameMap(this, broker);
        ui = new UI(this, broker);
        this.mode = Modes.NORMAL;
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

    public void setBuildingToPlace(Available buildingType) {
        if (buildingType == buildingToPlace) {
            buildingToPlace = null;
        } else {
            buildingToPlace = buildingType;
        }
        this.toggleMode(Modes.NORMAL);
    }

    public Modes getMode() {
        return mode;
    }

    public void toggleMode(Modes mode) {
        if (this.mode == mode) {
            this.mode = Modes.NORMAL;
        } else {
            this.mode = mode;
        }
        switch (mode) {
            case NORMAL:
                break;
            case DESTROY:
            case MOVE:

                this.buildingToPlace = null;
                break;
            default:
                break;
        }
    }

    public Available getBuildingToPlace() {
        return buildingToPlace;
    }

    public void handleCellClick(Vector2 cellPos) {
        switch (this.mode) {
            case NORMAL:
                this.clickBuild(cellPos);
                break;

            case DESTROY:
                this.clickDestroy(cellPos);
                break;
            case MOVE:
                this.clickMove(cellPos);
                break;
            default:
                break;
        }
    }

    // TODO: remove
    // What this is replaced by depends heavily on how we want to handle
    // building objects
    // Potentially a BuildingManager job but alternatively, if the tracking of objects
    // can be decoupled from GameScreen() into a dedicated grid data type, then it may
    // make more sense to let the buttons themselves be able to create their objects
    private void clickBuild(Vector2 cellPos) {
        Building building = builder.newBuilding(buildingToPlace, cellPos);
        broker.placeBuilding(building);
    }

    private void clickDestroy(Vector2 cellPos) {
        broker.destroyBuilding(cellPos);
    }

    private void clickMove(Vector2 cellPos) {
        // TODO
        this.clickDestroy(cellPos);
    }
}
