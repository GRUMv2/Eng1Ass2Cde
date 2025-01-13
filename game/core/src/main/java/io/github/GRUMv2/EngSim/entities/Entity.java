package io.github.GRUMv2.EngSim.entities;

import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;


// TODO: Button rendering abstraction

public abstract class Entity {

    //private Vector2 pos;
    private Runnable handleClick = () -> {
    };  // Do nothing by default

    public abstract void update(Renderer renderer, InputHandler inputHandler);


    public void setHandleClick(Runnable handleClick) {
        this.handleClick = handleClick;
    }

    public void click() {
        handleClick.run();
    }

}
