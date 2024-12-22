package io.github.GRUMv2.EngSim.entities;

/**
 * TmpInfoPopup
 */
public class TmpInfoPopup extends TmpPopup {

    private final String infoButtonText = "OK";

    public TmpInfoPopup(Runnable popupHook, String text) {
        super(popupHook, text);
        this.setHeaderText("Notice");
        this.setButtons(
            new TmpButton[] {
                new TmpButton(
                    this.infoButtonText,
                    // TODO: not this
                    // arbitrarily centre button based on arbitrary hardcoded coordinates for
                    // popup window in superclass and arbitrary hardcoded size of button in button class
                    this.getPos().add((this.getSize().x / 2) - 125f, 20f),
                    () -> this.dismiss()
                )
            }
        );
    }

    public void dismiss() {
        super.dismiss();
    }

}
