package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

// TODO: No longer extends ApplicationAdapter
public class Client {

    private int WIDTH;
    private int HEIGHT;

    private OrthographicCamera camera;
    private Renderer renderer;
    private InputHandler inputHandler;
    private AbstractGameScreen gameScreen;
    private Runnable setScreen;
    // TODO: private GameScreen screen;
    // +getter
    // +setter
    // +switchbox

    public Client(int width, int height, Runnable setScreen) {
        // NOTE: At this stage of execution libGDX is not initialised
        // and will not be until it calls Main.create()
        // Anything within the constructor of Client cannot attempt
        // to interact with libGDX objects until its create() function
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setScreen = setScreen;
    }

    public AbstractGameScreen getGameScreen() {
        return gameScreen;
    }

    private void setGameScreen(AbstractGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.setScreen.run();
    }

    public void create() {
        this.camera = createCamera();
        this.renderer = new Renderer(this.camera);
        this.inputHandler = new InputHandler(this.camera);
        this.setGameScreen(new Game(this.renderer, this.inputHandler));
    }

    // This must only be called on sysexit otherwise everything is kil
    public void dispose() {
        renderer.dispose();
    }

    // Not sure why it's necessary to create a custom camera here rather than
    // creating a viewport and using that for everything.
    // Runs once and isn't in the way of anything so leave for now.
    private OrthographicCamera createCamera() {
        Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        OrthographicCamera newCamera = new OrthographicCamera();
        FitViewport viewport = new FitViewport(WIDTH, HEIGHT, newCamera);
        viewport.apply();
        viewport.update((int) screenSize.x, (int) screenSize.y, true);
        newCamera.position.set((int) newCamera.viewportWidth / 2, (int) newCamera.viewportHeight / 2, 0);
        newCamera.update();
        return newCamera;
    }
}
