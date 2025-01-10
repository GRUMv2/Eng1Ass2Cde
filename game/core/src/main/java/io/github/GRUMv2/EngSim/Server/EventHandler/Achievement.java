package io.github.GRUMv2.EngSim.Server.EventHandler;

public class Achievement extends Event {
    void award() {
        System.out.println("Achievement awarded");
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        return false;
    }
}
