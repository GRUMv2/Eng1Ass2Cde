package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.ScreenAdapter;

import java.util.EnumMap;

/**
 * AbstractGameScreen - Base screen class
 */
abstract public class AbstractGameScreen extends ScreenAdapter {

    private final Renderer renderer;
    private final InputHandler inputHandler;

    private final EnumMap<Screens, Runnable> changeEvents;

    public AbstractGameScreen(Renderer renderer, InputHandler inputHandler) {
        this.renderer = renderer;
        this.inputHandler = inputHandler;
        this.changeEvents = new EnumMap<>(Screens.class);
    }

    /**
     * Top level render class called by libGDX
     * This triggers the chain reaction of update() throughout the game
     */
    @Override
    public void render(float delta) {
        renderer.update();
        this.update(this.renderer, this.inputHandler);
    }

    /**
     * Call passed Main.changeEvent() method
     */
    public void changeEvent(Screens screen) {
        this.changeEvents.getOrDefault(screen, () -> {
        }).run();
    }

    /**
     * Setter method for changeEvent from Main
     */
    public void setChangeEvent(Screens screen, Runnable runnable) {
        this.changeEvents.put(screen, runnable);
    }

    /**
     * Parent update() method for all client screen and entity rendering
     */
    abstract public void update(Renderer renderer, InputHandler inputHandler);

}
