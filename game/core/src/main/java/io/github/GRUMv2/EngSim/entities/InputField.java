package io.github.GRUMv2.EngSim.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;

/**
 * InputField
 */
public class InputField extends TmpTextBox {

    private final Vector2 pos;
    private final Vector2 size;

    private final InputAdapter keyboardProcessor;

    private boolean selected = false;

    public InputField(Vector2 pos, Vector2 size) {
        super(pos, size);

        this.pos = pos;
        this.size = size;

        // TODO: pull out input processor into actual input system
        keyboardProcessor = new InputAdapter() {

            private boolean handled;

            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case 61:
                        if (!selected) {
                            select();
                        }
                        this.handled = true;
                        return true;
                    case 66:
                    case 111:
                        if (selected) {
                            select();
                            setContent(getContent());
                        }
                        this.handled = true;
                        return true;
                    default:
                        if (!selected) {
                            // Eat key input if not selected by returning true
                            this.handled = true;
                            return true;
                        }
                        this.handled = false;
                        break;
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                String content = getContent();
                if (!selected) {
                    setContent(content);
                    return false;
                } else if (this.handled) {
                    setContent(content);
                    return true;
                }
                if ((int) character == 8) {
                    if (!content.isEmpty()) {
                        content = content.substring(0, content.length() - 1);
                    }
                } else {
                    content += character;
                }
                setContent(content);

                return true;
            }
        };

        ((InputMultiplexer) Gdx.input.getInputProcessor()).addProcessor(keyboardProcessor);
    }

    public void select() {
        this.selected = !selected;
        this.setContent(this.getContent());
    }

    @Override
    public String getContent() {
        String content = super.getContent();
        if (!content.isEmpty() && content.lastIndexOf("|") == content.length() - 1) {
            content = content.substring(0, content.length() - 1);
        }
        return content;
    }

    @Override
    public void setContent(String content) {
        if (!selected) {
            super.setContent(content);
        } else {
            super.setContent(content + "|");
        }
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        if (inputHandler.getMouseClicked()) {
            if (inputHandler.getMouseInBounds(this.pos, this.size)) {
                if (!this.selected) {
                    this.select();
                }
            } else {
                if (this.selected) {
                    this.select();
                }
            }
        }
        super.update(renderer, inputHandler);
    }
}
