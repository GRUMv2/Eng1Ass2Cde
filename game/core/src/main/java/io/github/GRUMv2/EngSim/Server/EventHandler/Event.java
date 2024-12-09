package io.github.GRUMv2.EngSim.Server.EventHandler;


abstract class Event {
    abstract public boolean tick(double delta, EventHandler handler);
}
