package io.github.GRUMv2.EngSim.entities;

// TODO, low priority: proper UI toolkit and rewrite

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.client.GameScreen;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.client.GameScreen.Modes;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;

public class UI extends Entity {

    // TODO: -> Settings
    private final String TITLE = "University Simulator";

    private GameScreen game;
    private Broker broker;
    private PauseButton pauseButton;
    private BuildingPlaceButton[] buildingPlaceButtons;
    private TmpButton[] actionButtons;

    public UI(GameScreen game, Broker broker, BuildingFactory builder) {
        this.broker = broker;
        this.game = game;
        pauseButton = new PauseButton(game::togglePause);

        Available[] availableBuildings = Available.values();
        this.buildingPlaceButtons = new BuildingPlaceButton[availableBuildings.length];
        Vector2 _v = new Vector2();
        Building building;
        for (int i = 0; i < availableBuildings.length; i++) {
            Available buildingType = availableBuildings[i]; // define here or lambda complains
            building = builder.newBuilding(buildingType, _v);
            buildingPlaceButtons[i] = new BuildingPlaceButton(
                building.getName(),
                building.getDescription(),
                i,
                () -> game.setBuildingToPlace(buildingType)
            );
        }

        this.actionButtons = new TmpButton[] {
            new TmpButton(
                Modes.DESTROY.toString(),
                new Vector2(20, 500),
                () -> game.toggleMode(Modes.DESTROY)
            ),
            new TmpButton(
                Modes.MOVE.toString(),
                new Vector2(290, 500),
                () -> game.toggleMode(Modes.MOVE)
            )
        };
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        // draw the title
        Vector2 titlePos = new Vector2(20, 700);
        renderer.drawText(this.TITLE, titlePos, Color.BLACK, 2f);

        // draw the time display
        String timeLeftString = broker.getTimeLeftString();
        Vector2 timePos = new Vector2(20, 650);
        renderer.drawText(timeLeftString, timePos, Color.BLACK, 1.5f);

        // update pause button
        pauseButton.update(renderer, inputHandler);

        // draw the building count
        renderer.drawText(
            broker.getTotalBuildings() + " Buildings",
            new Vector2(20, 600),
            Color.BLACK,
            1.5f
        );

        // draw the selected building
        renderer.drawText(
            "Selected: " + (game.getBuildingToPlace() == null ? "None" : game.getBuildingToPlace()),
            new Vector2(200, 600),
            Color.BLACK,
            1.5f
        );

        renderer.drawText(
            "Mode: " + game.getMode(),
            new Vector2(300, 650),
            Color.BLACK,
            1.5f
        );

        for (TmpButton button : this.actionButtons) {
            button.update(renderer, inputHandler);
        }

        // update the building place buttons
        for (BuildingPlaceButton buildingPlaceButton : buildingPlaceButtons) {
            buildingPlaceButton.update(renderer, inputHandler);
        }
    }
}
