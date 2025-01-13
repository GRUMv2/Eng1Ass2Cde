package io.github.GRUMv2.EngSim.server.eventHandler;

public class StartTheGameAchievement extends Achievement {

    StartTheGameAchievement() {
        super();
        awardName = "Start the game!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        this.award();
        return false;
    }
}
