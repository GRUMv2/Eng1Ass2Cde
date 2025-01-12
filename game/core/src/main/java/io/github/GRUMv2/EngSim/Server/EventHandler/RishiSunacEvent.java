package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.Random;

public class RishiSunacEvent extends Event {
    private final Random random = new Random();

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (random.nextInt(0, 1_000_000) < 3) {
            // 'income' is a generated property, to tank the income
            // we need to set the monthlyUpkeepCostsMultiuplyer to a high value
            server.setMonthlyUpkeepCostsMultiuplyer(4);
            server.popupManager.addPopup("Uh oh!",
                "The government has undervalued the importance of universities\n" +
                "and has cut your funding by 75%!\n\n" +
                    "Expect a significant drop in income for a while."
            );
            return false;
        }
        return true;
    }
}
