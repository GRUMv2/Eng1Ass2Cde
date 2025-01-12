package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.Random;

public class GovernmentGrantEvent extends Event {
    private final Random random = new Random();

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (random.nextInt(0, 100_000) < 5) {
            server.setMonthlyUpkeepCostsMultiuplyer(0);
            // only gives 1 month of free upkeep ish
            server.popupManager.addPopup("Government Grant!", "The government has given you a grant for this month,\nyour upkeep costs are free!");
            return false;
        }
        return true;
    }
}
