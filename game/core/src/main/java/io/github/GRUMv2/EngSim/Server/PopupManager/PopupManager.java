package io.github.GRUMv2.EngSim.Server.PopupManager;

import io.github.GRUMv2.EngSim.Server.TimeKeeper;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.broker.PopupTicket;

import java.util.ArrayList;

public class PopupManager {
    private final ArrayList<PopupTicket> popups;
    private final Broker broker;

    public PopupManager(TimeKeeper timeKeeper) {
        this.broker = Broker.getInstance();
        popups = new ArrayList<>();
    }

    public void serverTick(double delta) {
        // Technically possible to leave popups in the queue.
        // Given that server is paused while the user is reacting and
        // server ticks far faster than client renders, let alone the user's
        // reaction time, this should never occur. Even it somehow does, all
        // that happens is server takes an extra tick to retrieve each leftover

        PopupTicket p = broker.getResolvedPopups();
        if (p != null) {
            this.popups.remove(p);
        }
    }

    public ArrayList<PopupTicket> getPopups() {
        return popups;
    }

    // random example, replace as you will
    // Note:
    // - p1 = new PopupTicket(...)
    // -> broker.queuePopup(p1)
    // -> ticket = broker.getPendingPopups()
    // -> ticket.dismiss(int)
    // -> broker.resolvePopup()
    // -> p2 = broker.getResolvedPopups()
    // p1 == p2 => true
    //
    //public void addPopup(String title, String description, String[] options, int receipt) {
    //    // Interactive popup with options for return values
    //    PopupTicket p = new PopupTicket(title, description, options);
    //    this.popups.add(p);
    //    this.responses.put(receipt, p)
    //    broker.queuePopup(p);
    //}


    public void addPopup(String title, String description) {
        // Basic interactive info popup
        PopupTicket p = new PopupTicket(title, description, new String[]{});
        this.popups.add(p);
        broker.queuePopup(p);
    }

    public void addPopup(String content) {
        // Transient popup (notice)
        PopupTicket p = new PopupTicket(content);
        broker.queuePopup(p);
    }

}
