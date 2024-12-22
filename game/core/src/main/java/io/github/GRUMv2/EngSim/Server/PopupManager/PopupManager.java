package io.github.GRUMv2.EngSim.Server.PopupManager;

import io.github.GRUMv2.EngSim.Server.TimeKeeper;

import java.util.ArrayList;
import java.util.Iterator;

public class PopupManager {
    private final ArrayList<Popup> popups;
    private final TimeKeeper timeKeeper;

    public PopupManager(TimeKeeper timeKeeper) {
        popups = new ArrayList<>();
        this.timeKeeper = timeKeeper;
    }

    public void serverTick(double delta) {
        Iterator<Popup> iterator = popups.iterator();
        long cTime = timeKeeper.currentGameTime();

        while (iterator.hasNext()) {
            Popup popup = iterator.next();

            if (popup.autoKillAt > cTime) {
                iterator.remove();
                System.out.println(" [ POP ] Popup " + popup.name + " expired (EOL)");
            }
        }

    }

    public ArrayList<Popup> getPopups() {
        return popups;
    }

    public void removePopup(Popup popup) {
        popups.remove(popup);
        System.out.println(" [ POP ] Popup " + popup.name + " removed (DISMISSED)");
    }

    public void addPopup(String title, String description, PopupType type) {
        Popup p = new Popup(title, description, type, 60, timeKeeper);
        popups.add(p);
    }
}
