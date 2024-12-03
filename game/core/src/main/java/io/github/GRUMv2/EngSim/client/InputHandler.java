package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// Although would prefer more robust and extensible input handling
// with a processor/handler/multiplexer implementatation,
// this is fine to leave as-is for as long as scope permits it
// as it achieves what it was intended to do well enough.
// Not particularly practical if keyboard shortcuts become a must
// in which case it gets binned and replaced with a proper input system
public class InputHandler {

    final private OrthographicCamera camera;

    public InputHandler(OrthographicCamera camera) {
        this.camera = camera;
    }

    public boolean getMouseClicked() {
        boolean clicked = Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
        return clicked;
    }

    public Vector2 getMousePos() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();
        Vector3 screenCoords = new Vector3(x, y, 0);
        Vector3 worldCoords = camera.unproject(screenCoords);
        return new Vector2(worldCoords.x, worldCoords.y);
    }

    public boolean getMouseInBounds(Vector2 pos, Vector2 size) {
        Vector2 mousePos = getMousePos();
        return (
            mousePos.x > pos.x
            && mousePos.x < pos.x + size.x
            && mousePos.y > pos.y
            && mousePos.y < pos.y + size.y
        );
    }
}
