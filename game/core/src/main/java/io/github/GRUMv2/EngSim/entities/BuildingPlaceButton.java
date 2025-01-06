package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

public class BuildingPlaceButton extends Button {
    private String buildingName;
    private String buildingDescription;

    public BuildingPlaceButton(Vector2 pos, String buildingName, String buildingDescription, Runnable handleClick) {
        super(pos, new Vector2(520, 60), handleClick);
        this.buildingName = buildingName;
        this.buildingDescription = buildingDescription;
    }

    // TODO: Button rendering abstraction
    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        // TODO: burn with fire
        //Vector2 pos = new Vector2(20, 400 - (index * 80));
        //Vector2 size = new Vector2(520, 60);
        renderer.drawRect(this.getPos(), this.getSize(), Color.LIGHT_GRAY);
        renderer.drawText("Place: " + buildingName, new Vector2(this.getPos().x + 10, this.getPos().y + this.getSize().y - 10), Color.BLACK, 1.5f);
        renderer.drawText(buildingDescription, new Vector2(this.getPos().x + 10, this.getPos().y + this.getSize().y- 35), Color.BLACK, 1f);

        if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(this.getPos(), this.getSize())) {
            this.click();
        }
    }
}
