package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.*;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;

public class GameScreen extends AbstractGameScreen {

    private Available buildingToPlace = null;
    private Modes mode;
    private GameMap map;
    private UI ui;
    private Broker broker;
    private BuildingFactory builder;

    private Ghost ghost;

    public GameScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        broker = Broker.getInstance();
        builder = BuildingFactory.getInstance();
        map = new GameMap(this, broker);
        ui = new UI(this, broker, builder);
        this.mode = Modes.NORMAL;
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        if (broker.isGameComplete()) {
            this.changeEvent(Screens.END);
            return;
        }

        map.update(renderer, inputHandler);
        ui.update(renderer, inputHandler);

        if (this.ghost != null && inputHandler.getMouseInBounds(new Vector2(560, 0), new Vector2(720, 720))) {
            this.ghost.update(renderer, inputHandler);
        }

        if (Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT) && Gdx.input.isKeyJustPressed(Keys.F7)) {
            this.changeEvent(Screens.END);
        }

    }

    public void togglePause() {
        this.changeEvent(Screens.PAUSE);
    }

    public Modes getMode() {
        return mode;
    }

    public void toggleMode(Modes mode) {
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
        if (this.mode == mode) {
            this.mode = Modes.NORMAL;
        } else {
            this.mode = mode;
        }
    }

    public Available getBuildingToPlace() {
        return buildingToPlace;
    }

    public void setBuildingToPlace(Available buildingType) {
        if (buildingType == buildingToPlace) {
            buildingToPlace = null;
            this.ghost = null;
        } else {
            buildingToPlace = buildingType;

            try {
                Building temp = builder.newBuilding(buildingType, new Vector2(0, 0));
                this.ghost = new Ghost(temp);

            } catch (Exception e) {
                Building temp = new Gym(new Vector2(0, 0));
                this.ghost = new Ghost(temp);
            }
        }
        this.toggleMode(Modes.NORMAL);
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
    private boolean clickBuild(Vector2 cellPos) {
        if (buildingToPlace == null) {
            return false;
        }
        Building building = builder.newBuilding(buildingToPlace, cellPos);
        if (broker.getMoney() < building.getCost()) {
            return false;
        }
        // if not holding down shift select none
        if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.buildingToPlace = null;
        }
        return broker.placeBuilding(building);
    }

    private void clickDestroy(Vector2 cellPos) {
        broker.destroyBuilding(cellPos);
    }

    private void clickMove(Vector2 cellPos) {
        if (this.buildingToPlace == null) {
            Building building = broker.destroyBuilding(cellPos);
            if (building == null) {
                return;
            }
            // TODO: Exception handle
            // Theoreticaly throws IllegalArgumentException if building is not within the enum
            // In practice I don't see how this could ever be triggered, because destroyBuilding
            // implies a previous placeBuilding, triggered by user UI interaction,
            // which is generated from the values of Available
            this.buildingToPlace = Available.get(building.getClass());
        } else {
            if (this.clickBuild(cellPos)) {
                this.toggleMode(Modes.MOVE);
            }
        }
    }

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
}
