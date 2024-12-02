package io.github.GRUMv2.EngSim.Renderer;

import io.github.GRUMv2.EngSim.InputHandler;
import io.github.GRUMv2.EngSim.Renderer.Renderer;

public abstract class Entity {

    public abstract void update(Renderer renderer, InputHandler inputHandler);

}
