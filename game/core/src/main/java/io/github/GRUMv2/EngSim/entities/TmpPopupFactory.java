package io.github.GRUMv2.EngSim.entities;

/**
 * TmpPopupFactory
 */
public final class TmpPopupFactory {

    private static TmpPopupFactory instance;

    private Runnable popupHook;

    private TmpPopupFactory(Runnable popupHook) {
        this.popupHook = popupHook;
    }

    public static TmpPopupFactory getInstance(Runnable popupHook) {
        if (instance == null) {
            instance = new TmpPopupFactory(popupHook);
        }
        return instance;
    }

    public TmpInfoPopup newInfobox(String text) {
        TmpInfoPopup infobox = new TmpInfoPopup(this.popupHook, text);
        return infobox;
    }


}
