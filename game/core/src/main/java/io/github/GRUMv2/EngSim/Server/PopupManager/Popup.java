package io.github.GRUMv2.EngSim.Server.PopupManager;

import io.github.GRUMv2.EngSim.Server.TimeKeeper;

public class Popup {
    public String name;
    public String description;
    public PopupType type;
    public long autoKillAt;

    public Popup(String name, String description, PopupType type, int lastsFor, TimeKeeper timeKeeper) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.autoKillAt = (timeKeeper.currentGameTime() + (lastsFor * 1000));
    }
}
