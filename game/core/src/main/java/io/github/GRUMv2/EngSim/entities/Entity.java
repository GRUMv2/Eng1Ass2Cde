package io.github.GRUMv2.EngSim.entities;
import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.client.InputHandler;

public abstract class Entity {

    public abstract void update(Renderer renderer, InputHandler inputHandler);

}
