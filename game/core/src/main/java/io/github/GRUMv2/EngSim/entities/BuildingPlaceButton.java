package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;

public class BuildingPlaceButton extends Button {

    private final Available buildingType;

    private final TmpTextBox nameBox;
    private final TmpTextBox descBox;
    private final TmpTextBox costBox;
    private final TmpTextBox keepBox;
    private final TmpTextBox manyBox;

    public BuildingPlaceButton(Vector2 pos, Vector2 size, Available buildingType, Runnable handleClick) {
        super(pos, size, handleClick);

        this.buildingType = buildingType;

        Building building = BuildingFactory.getInstance().newBuilding(buildingType, new Vector2());

        this.nameBox = new TmpTextBox(new Vector2(pos.x, pos.y - (size.y / 8) + (size.y / 2f)), new Vector2((size.x / 2f) /* + (size.y / 8) */, (size.y / 2f) + (size.y / 8)));
        this.descBox = new TmpTextBox(new Vector2(pos.x, pos.y), new Vector2((size.x / 2f) - (size.x / 12f), size.y / 2f));
        this.costBox = new TmpTextBox(new Vector2(pos.x + size.x / 2f - (size.x / 12f), pos.y + size.y / 2f), new Vector2(size.x / 2f - (size.x / 12f), size.y / 2f));
        this.keepBox = new TmpTextBox(new Vector2(pos.x + size.x / 2f - (size.x / 12f), pos.y), new Vector2(size.x / 2f - (size.x / 12f), size.y / 2f));
        this.manyBox = new TmpTextBox(new Vector2(pos.x + (size.x / 3f) + (size.x / 2f), pos.y), new Vector2(size.x - (size.x / 3f) - (size.x / 2f), size.y));

        assert building != null;  // again CI wants me to do this?
        this.nameBox.setContent(building.getName());
        this.descBox.setContent(building.getDescription());
        this.costBox.setContent("Cost: £" + (building.getCost() / 1000) + "K");
        this.keepBox.setContent("Monthly upkeep: £" + (building.getMonthlyUpkeepCosts() / 1000) + "K");

        this.manyBox.centreText();
    }

    public void setCount(int count) {
        this.manyBox.setContent(String.valueOf(count));
    }

    public Available getBuildingType() {
        return buildingType;
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        this.update(renderer, inputHandler, false);
    }

    public void update(Renderer renderer, InputHandler inputHandler, boolean selected) {
        renderer.drawRect(this.getPos(), this.getSize(), selected ? Color.GRAY : Color.LIGHT_GRAY, 1f);

        this.nameBox.update(renderer, inputHandler);
        this.descBox.update(renderer, inputHandler);
        this.costBox.update(renderer, inputHandler);
        this.keepBox.update(renderer, inputHandler);
        this.manyBox.update(renderer, inputHandler);

        if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(this.getPos(), this.getSize())) {
            this.click();
        }
    }
}
