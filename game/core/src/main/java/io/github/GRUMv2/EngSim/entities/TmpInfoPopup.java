package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.PopupTicket;

/**
 * TmpInfoPopup
 */
public class TmpInfoPopup extends TmpPopup {

    private final String infoButtonText = "OK";

    public TmpInfoPopup(Runnable popupHook, PopupTicket popup) {
        super(popupHook, popup);
        this.setHeaderText(popup.getName());
        this.setButtons(
            new TmpButton[] {
                new TmpButton(
                    this.infoButtonText,
                    // TODO: not this
                    // arbitrarily centre button based on arbitrary hardcoded coordinates for
                    // popup window in superclass and arbitrary hardcoded size of button in button class
                    this.getPos().add((this.getSize().x / 2) - 125f, 20f),
                    new Vector2(250, 60),
                    () -> this.dismiss(0),
                    Color.WHITE
                )
            }
        );
    }
}
