package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Client extends ApplicationAdapter {

    private int WIDTH;
    private int HEIGHT;

    private OrthographicCamera camera;
    private Renderer renderer;
    private InputHandler inputHandler;
    private Game game;

    public Client(int width, int height) {
        // NOTE: At this stage of execution libGDX is not initialised
        // and will not be until it calls Main.create()
        // Anything within the constructor of Client cannot attempt
        // to interact with libGDX objects until its create() function
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    @Override
    public void create() {
        camera = createCamera();
        renderer = new Renderer(camera);
        inputHandler = new InputHandler(camera);
        game = new Game();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        renderer.update();
        game.update(delta, renderer, inputHandler);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private OrthographicCamera createCamera() {
        Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        OrthographicCamera newCamera = new OrthographicCamera();
        Viewport viewport = new FitViewport(WIDTH, HEIGHT, newCamera);
        viewport.apply();
        viewport.update((int) screenSize.x, (int) screenSize.y, true);
        newCamera.position.set((int) newCamera.viewportWidth / 2, (int) newCamera.viewportHeight / 2, 0);
        newCamera.update();
        return newCamera;
    }
}
