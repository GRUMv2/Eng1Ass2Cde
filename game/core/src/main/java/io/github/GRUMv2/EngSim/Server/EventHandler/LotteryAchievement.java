package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.Random;

public class LotteryAchievement extends Achievement {
    private final Random random = new Random();

    LotteryAchievement() {
        super();
        awardName = "You won the lottery!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (random.nextInt(0, 100_000) < 3) {
            broker.spendMoney(-10_000_000);
            this.award();
            return false;
        }
        return true;
    }
}
