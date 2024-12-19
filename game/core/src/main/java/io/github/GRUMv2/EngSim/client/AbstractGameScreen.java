package io.github.GRUMv2.EngSim.client;

import java.util.EnumMap;

import com.badlogic.gdx.ScreenAdapter;

/**
 * AbstractGameScreen
 */
abstract public class AbstractGameScreen extends ScreenAdapter {

    private Renderer renderer;
    private InputHandler inputHandler;

    private EnumMap<Screens, Runnable> changeEvents;

    //private float delta;

    public AbstractGameScreen(Renderer renderer, InputHandler inputHandler) {
        this.renderer = renderer;
        this.inputHandler = inputHandler;
        this.changeEvents = new EnumMap<Screens, Runnable>(Screens.class);
    }

    @Override
    public void render(float delta) {
        //this.delta = delta;
        renderer.update();
        this.update(this.renderer, this.inputHandler);
    }

    public void changeEvent(Screens screen) {
        this.changeEvents.getOrDefault(screen, () -> {}).run();
    }

    public void setChangeEvent(Screens screen, Runnable runnable) {
        this.changeEvents.put(screen, runnable);
    }

    abstract public void update(Renderer renderer, InputHandler inputHandler);

}
