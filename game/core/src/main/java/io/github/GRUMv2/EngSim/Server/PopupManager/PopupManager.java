package io.github.GRUMv2.EngSim.Server.PopupManager;

import io.github.GRUMv2.EngSim.Server.TimeKeeper;
import io.github.GRUMv2.EngSim.broker.PopupTicket;

import java.util.ArrayList;
import java.util.Iterator;

public class PopupManager {
    private final ArrayList<PopupTicket> popups;
    private final TimeKeeper timeKeeper;

    public PopupManager(TimeKeeper timeKeeper) {
        popups = new ArrayList<>();
        this.timeKeeper = timeKeeper;
    }

    public void serverTick(double delta) {
        Iterator<PopupTicket> iterator = popups.iterator();
        long cTime = timeKeeper.currentGameTime();

        while (iterator.hasNext()) {
            PopupTicket popup = iterator.next();

            if (popup.getAutoKillAt() > cTime) {
                iterator.remove();
                System.out.println(" [ POP ] Popup " + popup.getName() + " expired (EOL)");
            }
        }

    }

    public ArrayList<PopupTicket> getPopups() {
        return popups;
    }

    public void removePopup(PopupTicket popup) {
        popups.remove(popup);
        System.out.println(" [ POP ] Popup " + popup.getName() + " removed (DISMISSED)");
    }

    public void addPopup(String title, String description) {
        PopupTicket p = new PopupTicket(title, description, timeKeeper, 60);
        popups.add(p);
    }
}
