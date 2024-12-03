package io.github.GRUMv2.EngSim.entities;
import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.client.InputHandler;


// TODO: UI overhaul

public abstract class Entity {

    public abstract void update(Renderer renderer, InputHandler inputHandler);

    // TODO: UI overhaul -> abstract click
    // possibly want a constructor here to abstract Cell's handleClick()
    // for non-clickable entities (Obstacle [Lake, Road]) just have it exit immediately

}
