package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// Although we would prefer more robust and extensible input handling
// with a processor/handler/multiplexer implementation,
// this is fine to leave as-is for as long as scope permits it
// as it achieves what it was intended to do well enough.
// Not particularly practical if keyboard shortcuts become a must
// in which case it gets binned and replaced with a proper input system

/**
 * InputHandler - master class passed to all update() methods for handling (mouse) input
 */
public class InputHandler {

    final private OrthographicCamera camera;

    public InputHandler(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Check whether mouse clicked
     *
     * @return whether mouse clicked
     */
    public boolean getMouseClicked() {
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

    /**
     * Position of mouse converted to libGDX terms
     *
     * @return Vector of mouse position
     */
    public Vector2 getMousePos() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();
        Vector3 screenCoords = new Vector3(x, y, 0);
        Vector3 worldCoords = camera.unproject(screenCoords);
        return new Vector2(worldCoords.x, worldCoords.y);
    }

    /**
     * Is mouse in bounds
     *
     * @param pos  origin coordinate
     * @param size size of bounds
     */
    public boolean getMouseInBounds(Vector2 pos, Vector2 size) {
        Vector2 mousePos = getMousePos();
        return (
            mousePos.x >= pos.x
                && mousePos.x < pos.x + size.x
                && mousePos.y >= pos.y
                && mousePos.y < pos.y + size.y
        );
    }

    /**
     * Is mouse in bounds
     *
     * @param shape 2D list of [pos, size] arrays that can be passed
     */
    public boolean getMouseInBounds(Vector2[][] shape) {
        // Where shape is a 2D list of [pos, size] arrays that can be passed
        // to getMouseInBounds(pos, size)
        for (Vector2[] box : shape) {
            if (getMouseInBounds(box[0], box[1])) {
                return true;
            }
        }
        return false;
    }
}
