package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.ScreenAdapter;

/**
 * AbstractGameScreen
 */
abstract public class AbstractGameScreen extends ScreenAdapter {

    private Renderer renderer;
    private InputHandler inputHandler;

    //private float delta;

    public AbstractGameScreen(Renderer renderer, InputHandler inputHandler) {
        this.renderer = renderer;
        this.inputHandler = inputHandler;
    }

    @Override
    public void render(float delta) {
        //this.delta = delta;
        renderer.update();
        this.update(this.renderer, this.inputHandler);
    }

    abstract public void update(Renderer renderer, InputHandler inputHandler);

}
