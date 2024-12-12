package io.github.GRUMv2.EngSim.entities;

abstract public class Button extends Entity {

    public Button(Runnable command) {
        this.setHandleClick(command);
    }

    // TODO: Button rendering abstraction
}
