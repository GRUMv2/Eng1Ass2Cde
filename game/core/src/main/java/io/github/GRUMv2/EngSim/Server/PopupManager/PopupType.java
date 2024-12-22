package io.github.GRUMv2.EngSim.Server.PopupManager;

public enum PopupType {
    INFO,
    WARNING,
    ERROR,  // event manager ¬may¬ push to this (scope dependant)
    DEBUG,  // events will be given a debug flag to push popups for debug
}
