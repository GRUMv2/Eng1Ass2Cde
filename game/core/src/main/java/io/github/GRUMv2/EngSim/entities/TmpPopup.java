package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.PopupTicket;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * TmpPopup
 */
public abstract class TmpPopup extends Entity {

    // TODO: not this
    // See note in Cell()
    private final Vector2 POS = new Vector2(300, 20);
    private final Vector2 SIZE = new Vector2(680, 680);

    private final float BORDER_WIDTH = 5f;
    private final float CONTENT_SCALE = 0.7f;
    private final float HF_SCALE = ((1 - CONTENT_SCALE) / 2);

    private Color bg = Color.valueOf("cccccc");
    private Color bd = Color.valueOf("555555");
    private Color fg = Color.valueOf("333333");

    private Runnable popupHook;
    private PopupTicket ticket;
    private String content;
    private String headerText;

    private TmpButton[] buttons;
    private TmpTextBox headerBox;
    private TmpTextBox contentBox;

    public TmpPopup(Runnable popupHook, PopupTicket popup) {
        this.ticket = popup;
        this.content = popup.getDescription();
        this.popupHook = popupHook;

        Vector2 contentPos = new Vector2(this.POS.x + (2*this.BORDER_WIDTH), this.POS.y + (2*this.BORDER_WIDTH));
        Vector2 contentSize = new Vector2(this.SIZE.x - (4*this.BORDER_WIDTH), this.SIZE.y - (4*this.BORDER_WIDTH));

        this.headerBox = new TmpTextBox(
            // (x, bottomLeftCorner + divider + footer + content)
            new Vector2(contentPos.x, contentPos.y + (((this.HF_SCALE + this.CONTENT_SCALE) * contentSize.y) + this.BORDER_WIDTH)),
            new Vector2(contentSize.x, (contentSize.y * this.HF_SCALE) - this.BORDER_WIDTH)
        );
        this.contentBox = new TmpTextBox(
            new Vector2(contentPos.x, contentPos.y + (this.HF_SCALE * contentSize.y)),
            new Vector2(contentSize.x, contentSize.y * this.CONTENT_SCALE),
            this.content
        );
        this.headerBox.centreText();
        this.headerBox.setTextColor(fg);
        this.contentBox.centreText();
        this.contentBox.setTextColor(fg);
    }

    public TmpButton[] getButtons() {
        return buttons;
    }

    public void setButtons(TmpButton[] buttons) {
        this.buttons = buttons;
    }

    public Vector2 getPos() {
        return POS;
    }

    public Vector2 getSize() {
        return SIZE;
    }

    public String getContent() {
        return content;
    }

    public PopupTicket getTicket() {
        return ticket;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
        this.headerBox.setContent(this.headerText);
    }

    protected void dismiss(int index) {
        this.ticket.dismiss(index);
        this.popupHook.run();
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        // border hack
        Color[] bColors = new Color[]{bg, bd, bg};
        float ibw;
        for (int i = 0; i < 3; i++) {
            ibw = i * this.BORDER_WIDTH;
            renderer.drawRect(
                new Vector2(this.POS.x + ibw, this.POS.y + ibw),
                new Vector2(this.SIZE.x - (2 * ibw), this.SIZE.y - (2 * ibw)),
                bColors[i]
            );

            if (i == 2) {
                renderer.drawRect(
                    new Vector2(this.POS.x + (ibw * 2), this.POS.y + ibw + (this.HF_SCALE * (this.SIZE.y - (2 * ibw))) - this.BORDER_WIDTH),
                    new Vector2(this.SIZE.x - (4 * ibw), this.BORDER_WIDTH),
                    bd
                );

                renderer.drawRect(
                    new Vector2(this.POS.x + (ibw * 2), this.POS.y + ibw + ((this.HF_SCALE + this.CONTENT_SCALE) * (this.SIZE.y - (2 * ibw)))),
                    new Vector2(this.SIZE.x - (4 * ibw), this.BORDER_WIDTH),
                    bd
                );
            }
        }

        this.headerBox.update(renderer, inputHandler);
        this.contentBox.update(renderer, inputHandler);

        for (TmpButton button : buttons) {
            button.update(renderer, inputHandler);
        }
    }
}
