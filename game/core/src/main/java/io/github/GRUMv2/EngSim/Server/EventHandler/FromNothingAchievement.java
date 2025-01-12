package io.github.GRUMv2.EngSim.Server.EventHandler;


public class FromNothingAchievement extends Achievement {
    private boolean beenNegative = false;

    FromNothingAchievement() {
        super();
        awardName = "Build a fortune from nothing!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        double money = broker.getMoney();

        if (money < 0) {
            beenNegative = true;
        }
        if (beenNegative && money > 1_000_000) {
            this.award();
            return false;
        }

        return true;
    }
}
