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
    private StatsBox statsBox;

    private int updateTime;

    public UI(GameScreen game, Broker broker, BuildingFactory builder) {
        this.broker = broker;
        this.game = game;
        pauseButton = new PauseButton(
            new Vector2(520, 680),
            game::togglePause
        );

        this.actionButtons = new TmpButton[] {
            new TmpButton(
                Modes.DESTROY.toString(),
                new Vector2(20, 410),
                () -> game.toggleMode(Modes.DESTROY)
            ),
            new TmpButton(
                Modes.MOVE.toString(),
                new Vector2(290, 410),
                () -> game.toggleMode(Modes.MOVE)
            )
        };

        Available[] availableBuildings = Available.values();
        this.buildingPlaceButtons = new BuildingPlaceButton[availableBuildings.length];
        for (int i = 0; i < availableBuildings.length; i++) {
            Available buildingType = availableBuildings[i]; // define here or lambda complains
            buildingPlaceButtons[i] = new BuildingPlaceButton(
                new Vector2(20, 330 - (i * 80)),
                new Vector2(520, 60),
                buildingType,
                () -> game.setBuildingToPlace(buildingType)
            );
        }

        this.statsBox = new StatsBox(
            new Vector2(20, 480),
            new Vector2(250, 180)
        );
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        // draw the title
        Vector2 titlePos = new Vector2(20, 700);
        renderer.drawText(this.TITLE, titlePos, Color.BLACK, 1.5f);

        // draw the time display
        String timeLeftString = broker.getTimeLeftString();
        Vector2 timePos = new Vector2(20, 680);
        renderer.drawText(timeLeftString, timePos, Color.BLACK, 1f);

        // update pause button
        pauseButton.update(renderer, inputHandler);

        renderer.drawText(
            "Mode: " + game.getMode(),
            new Vector2(350, 700),
            Color.BLACK,
            1f
        );

        // draw the selected building
        renderer.drawText(
            "Selected: " + (game.getBuildingToPlace() == null ? "None" : game.getBuildingToPlace()),
            new Vector2(350, 680),
            Color.BLACK,
            1f
        );

        // draw the building count
        renderer.drawText(
            broker.getTotalBuildings() + " Buildings",
            new Vector2(350, 660),
            Color.BLACK,
            1f
        );

        if (this.updateTime == broker.getTimeLeft()) {
            this.statsBox.setStats(broker.getMoney(), broker.getIncome());
        } else {
            this.statsBox.setStats(
                broker.getMoney(),
                broker.getIncome(),
                broker.getStudentNumbers(),
                broker.getStudentSatisfaction(),
                broker.getStaffNumbers(),
                broker.getStaffSatisfaction()
            );
            this.updateTime = broker.getTimeLeft();
        }
        this.statsBox.update(renderer, inputHandler);

        for (TmpButton button : this.actionButtons) {
            button.update(renderer, inputHandler);
        }

        // update the building place buttons
        for (BuildingPlaceButton buildingPlaceButton : buildingPlaceButtons) {
            buildingPlaceButton.setCount(broker.getBuildingCount(buildingPlaceButton.getBuildingType()));
            buildingPlaceButton.update(renderer, inputHandler);
        }
    }
}
