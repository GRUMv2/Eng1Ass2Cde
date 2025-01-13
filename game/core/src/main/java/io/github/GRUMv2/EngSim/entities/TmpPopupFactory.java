package io.github.GRUMv2.EngSim.entities;

import io.github.GRUMv2.EngSim.broker.PopupTicket;

/**
 * TmpPopupFactory
 */
public final class TmpPopupFactory {

    private static TmpPopupFactory instance;

    private final Runnable popupHook;

    private TmpPopupFactory(Runnable popupHook) {
        this.popupHook = popupHook;
    }

    public static TmpPopupFactory getInstance(Runnable popupHook) {
        if (instance == null) {
            instance = new TmpPopupFactory(popupHook);
        }
        return instance;
    }

    public TmpInfoPopup newInfobox(PopupTicket ticket) {
        return new TmpInfoPopup(this.popupHook, ticket);
    }


}
