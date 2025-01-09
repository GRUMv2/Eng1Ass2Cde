package io.github.GRUMv2.EngSim.Server.EventHandler;

public class Acheivement extends Event {
    void award() {
        System.out.println("Acheivement awarded");
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        return false;
    }
}
