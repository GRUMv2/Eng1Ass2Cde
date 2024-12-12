package io.github.GRUMv2.EngSim.entities;
import io.github.GRUMv2.EngSim.client.Renderer;

//import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.client.InputHandler;


// TODO: UI overhaul

public abstract class Entity {

    //private Vector2 pos;
    private Runnable handleClick = () -> {};  // Do nothing by default

    public abstract void update(Renderer renderer, InputHandler inputHandler);
    //
    //public Vector2 getPos() {
    //    return pos;
    //}
    //
    //public void setPos(Vector2 pos) {
    //    this.pos = pos;
    //}

    public void setHandleClick(Runnable handleClick) {
        this.handleClick = handleClick;
    }

    public void click() {
        handleClick.run();
    }

}
