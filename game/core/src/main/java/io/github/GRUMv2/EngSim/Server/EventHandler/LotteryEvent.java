package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.Random;

public class LotteryEvent extends Event {
    private final Random random = new Random();

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (random.nextInt(0, 100_000) < 3) {
            server.popupManager.addPopup("Congratulations!",
                "A student has just won the lottery!\n" +
                "They have donated $10,000,000 to the university!");
            broker.spendMoney(-10_000_000);
            return false;
        }
        return true;
    }
}
