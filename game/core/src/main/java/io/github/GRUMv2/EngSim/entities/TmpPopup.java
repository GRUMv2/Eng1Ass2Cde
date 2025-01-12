package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

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

    private Color bg = Color.valueOf("cccccc");
    private Color bd = Color.valueOf("555555");
    private Color fg = Color.valueOf("333333");

    private TmpButton[] buttons;
    private Runnable popupHook;
    private PopupTicket ticket;
    private String content;
    private String headerText;

    public TmpPopup(Runnable popupHook, PopupTicket popup) {
        this.ticket = popup;
        this.content = popup.getDescription();
        this.popupHook = popupHook;
    }

    public TmpButton[] getButtons() {
        return buttons;
    }

    public void setButtons(TmpButton[] buttons) {
        this.buttons = buttons;
    }

    public Vector2 getPos() {
        return POS.cpy();
    }

    public Vector2 getSize() {
        return SIZE.cpy();
    }

    public String getContent() {
        return content;
    }

    public PopupTicket getTicket() {
        return ticket;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    protected void dismiss(int index) {
        this.ticket.dismiss(index);
        this.popupHook.run();
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {

        Vector2 floatingPos = this.getPos();
        Vector2 floatingSize = this.getSize();

        // border hack
        renderer.drawRect(
            floatingPos,
            floatingSize,
            bg
        );
        renderer.drawRect(
            floatingPos.add(5f, 5f),
            floatingSize.sub(10f, 10f),
            bd
        );
        renderer.drawRect(
            floatingPos.add(5f, 5f),
            floatingSize.sub(10f, 10f),
            bg
        );

         //Header
        renderer.drawRect(
            floatingPos.add(10f, floatingSize.y * 0.8f),
            new Vector2(floatingSize.x - 40f, 10f),
            bd
        );
        renderer.drawText(
            this.headerText,
            this.getPos().add(this.getSize().scl(0.5f, 0.9f)),
            fg,
            2f,
            Align.center
        );

         //Central text
        renderer.drawText(
            this.content,
            this.getPos().add(this.getSize().scl(0.5f)),
            fg,
            1.5f,
            Align.center
        );

         //Footer
        renderer.drawRect(
            this.getPos().add(20f, this.getSize().y * 0.2f),
            new Vector2(this.getSize().x - 40f, 10f),
            bd
        );

        for (TmpButton button : buttons) {
            button.update(renderer, inputHandler);
        }
    }
}
