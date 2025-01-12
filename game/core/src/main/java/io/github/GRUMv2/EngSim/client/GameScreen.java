package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.broker.PopupTicket;
import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.BuildingFactory;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;
import io.github.GRUMv2.EngSim.entities.GameMap;
import io.github.GRUMv2.EngSim.entities.TmpPopup;
import io.github.GRUMv2.EngSim.entities.TmpPopupFactory;
import io.github.GRUMv2.EngSim.entities.UI;


import io.github.GRUMv2.EngSim.entities.Ghost;


/**
 * The GameScreen class represents the primary visual interface through which the
 * interactive digital entertainment experience is rendered and perceived by the end-user.
 * This interface serves as the principal conduit for the graphical and interactive
 * elements of the game, encapsulating the entirety of the visual and interactive components
 * that constitute the user's engagement with the game screen.
 *
 * Not that anyone really wants to look at the gamescreen anyway lets be honnest
 *
 * hope that's enough to satisfy the javadoc gods and their stupid thirst for obvious documentation
 *
 * to be fair they probably know that nobody likes java and would prefer to read the code in english
 *
 * only doubles the file size tho no stress
 */
public class GameScreen extends AbstractGameScreen {

    private Available buildingToPlace = null;
    private Modes mode;
    private GameMap map;
    private UI ui;
    private Broker broker;
    private BuildingFactory builder;
    private TmpPopupFactory popupFactory;

    private TmpPopup activePopup;

    private Ghost ghost;

    public GameScreen(Renderer renderer, InputHandler inputHandler) {
        super(renderer, inputHandler);
        broker = Broker.getInstance();
        builder = BuildingFactory.getInstance();
        popupFactory = TmpPopupFactory.getInstance(() -> this.dismissActivePopup());
        map = new GameMap(this, broker);
        ui = new UI(this, broker, builder);
        this.mode = Modes.NORMAL;
    }

    private void deathByDevZero() {
        int a = 1 / 0;
    }

    public void update(Renderer renderer, InputHandler inputHandler) {
        if (broker.isGameComplete()) {
            this.changeEvent(Screens.END);
            return;
        }

        map.update(renderer, inputHandler);
        ui.update(renderer, inputHandler);

        if (this.ghost != null) {
            // This kind of does the same calculation twice in a slightly different way.
            // If the draw position was pulled out of Ghost.update() and made obtainable, it could be used
            // to then calculate the grid tile too for minor efficiency gain
            if (inputHandler.getMouseInBounds(new Vector2(560, 0), new Vector2(720, 720))) {
                if (broker.isPlaceable(this.ghost.getBuilding(), this.map.getCellAtPos(inputHandler.getMousePos()))) {
                    this.ghost.update(renderer, inputHandler);

                } else if (broker.getTimeElapsed() % 1 > 0.6) {
                    this.ghost.update(renderer, inputHandler);
                }
            }
        }

        if (this.isInDialog()) {
            this.activePopup.update(renderer, inputHandler);
        } else {
            PopupTicket p = broker.getPendingPopups();
            if (p != null) {
                if (p.isTransient()) {
                    this.ui.pushNotice(p.getDescription());
                    broker.resolvePopup();
                } else {
                    this.activePopup = popupFactory.newInfobox(p);
                    this.changeEvent(Screens.GAME);
                }
            }
        }

        // Debug features
        // The player can trigger it if they like but it doesn't exactly offer much help
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
            if (Gdx.input.isKeyPressed(Keys.F7)) {
                this.changeEvent(Screens.END);
            } else if (Gdx.input.isKeyPressed(Keys.F5)) {
                broker.queuePopup(new PopupTicket("Debug", "Debug popup", new String[] { "OPTION" }));
                broker.queuePopup(new PopupTicket("This is a lot of information in a notice box"));
                this.changeEvent(Screens.GAME);
            } else if (Gdx.input.isKeyPressed(Keys.F6)) {
                deathByDevZero();
            }
        }
    }

    public void togglePause() {
        this.changeEvent(Screens.PAUSE);
    }

    public void dismissActivePopup() {
        this.activePopup = null;
        broker.resolvePopup();
        this.changeEvent(Screens.GAME);
    }

//    public void setBuildingToPlace(Available buildingType) {
//        if (buildingType == buildingToPlace) {
//            buildingToPlace = null;
//        } else {
//            buildingToPlace = buildingType;
//        }
//        this.toggleMode(Modes.NORMAL);
//    }

    public Modes getMode() {
        return mode;
    }

    public boolean isInDialog() {
        return this.activePopup != null;
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
            this.ghost = new Ghost(buildingType);
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
            this.ghost = null;
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
