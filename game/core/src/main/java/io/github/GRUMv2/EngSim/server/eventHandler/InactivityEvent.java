package io.github.GRUMv2.EngSim.server.eventHandler;

public class InactivityEvent extends Event {
    private int buildingCount = -1;
    private double lastChanged;

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (buildingCount != broker.getTotalBuildings()) {
            buildingCount = broker.getTotalBuildings();
            lastChanged = server.timeKeeper.currentGameTime();
            return true;
        }

        if (server.timeKeeper.currentGameTime() - lastChanged > 30_000) {
            server.popupManager.addPopup("Union upset",
                    "The union feels like your not investing in the growth of the uni.\n" +
                            "They have started organising strikes");
            server.setMonthlyUpkeepCostsMultiplier(2f);
            lastChanged = server.timeKeeper.currentGameTime();
        }

        return true;
    }
}
