package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.math.Vector2;

abstract public class Button extends Entity {

    private Vector2 pos;
    private Vector2 size;

    public Button(Vector2 pos, Vector2 size, Runnable command) {
        this.pos = pos;
        this.size = size;
        this.setHandleClick(command);
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getSize() {
        return size;
    }

}
