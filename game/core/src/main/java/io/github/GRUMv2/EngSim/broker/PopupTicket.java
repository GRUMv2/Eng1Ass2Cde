package io.github.GRUMv2.EngSim.broker;

import io.github.GRUMv2.EngSim.Server.TimeKeeper;

public class PopupTicket {
    private boolean dismissed = false;

    private String name;
    private String description;
    private boolean transience;
    private long autoKillAt;

    private String[] options;
    private int response;

    public PopupTicket(String name, String description, String[] options) {
        // Interactive popup
        this.transience = false;
        this.name = name;
        this.description = description;
        this.options = options;
    }

    public PopupTicket(String name, String description, TimeKeeper timeKeeper, int lastsFor) {
        // Transient popup
        this.transience = true;
        this.name = name;
        this.description = description;
        this.autoKillAt = (timeKeeper.currentGameTime() + (lastsFor * 1000));
    }

    public boolean isTransient() {
        return transience;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getAutoKillAt() {
        return autoKillAt;
    }

    public void dismiss() {
        this.dismissed = true;
    }

    public void dismiss(int response) {
        this.response = response;
        this.dismissed = true;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public String[] getOptions() {
        if (!this.transience) {
            return options;
        }
        return new String[]{};
    }

    public int getResponse() {
        return response;
    }

}
