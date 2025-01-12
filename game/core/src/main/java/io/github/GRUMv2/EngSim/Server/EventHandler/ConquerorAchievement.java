package io.github.GRUMv2.EngSim.Server.EventHandler;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;

public class ConquerorAchievement extends Achievement {
    private final Broker broker;

    ConquerorAchievement() {
        super();
        broker = Broker.getInstance();
        awardName = "Conquered all 4 corners";
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (broker.getGrid().get(new Vector2(0, 0)) != null &&
                broker.getGrid().get(new Vector2(broker.getMapCells() - 1, 0)) != null &&
                broker.getGrid().get(new Vector2(0, broker.getMapCells() - 2)) != null &&
                broker.getGrid().get(new Vector2(broker.getMapCells() - 1,
                        broker.getMapCells() - 1)) != null) {
            this.award();
            return false;
        }
        return true;
    }
}
