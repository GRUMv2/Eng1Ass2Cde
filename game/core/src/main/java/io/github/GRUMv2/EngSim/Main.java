package io.github.GRUMv2.EngSim;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.AbstractGameScreen;
import io.github.GRUMv2.EngSim.client.GameScreen;

/**
 * Main
 */
public class Main extends Game {

    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;

    private OrthographicCamera camera;
    private Renderer renderer;
    private InputHandler inputHandler;
    private AbstractGameScreen gameScreen;

    //private final Server server

    public enum Screens {
        MENU,
        GAME,
        PAUSE,
        END
    }

    public Main() {
        // NOTE: At this stage of execution libGDX is not initialised
        // and will not be until it calls Main.create()
        // Anything within the constructor of Client cannot attempt
        // to interact with libGDX objects until its create() function
        //server = new Server(120);
        //server.start();
    }

    public void create() {
        this.camera = createCamera();
        this.renderer = new Renderer(this.camera);
        this.inputHandler = new InputHandler(this.camera);
        this.gameScreen = new GameScreen(renderer, inputHandler);
        this.setScreen(this.gameScreen);
    }

    public void changeScreen(Screens screen) {
        this.gameScreen.dispose();
        switch (screen) {
            case MENU:
                //this.gameScreen = new MenuScreen(renderer, inputHandler);
                //this.setGameScreen();
                break;
            case GAME:
                //this.server.start_or_resume();
                this.gameScreen = new GameScreen(renderer, inputHandler);
                this.setScreen(this.gameScreen);
                break;
            case PAUSE:
                //this.server.pause();
                //this.gameScreen = new PauseScreen(renderer, inputHandler);
                //this.setGameScreen();
                break;
            case END:
                //this.server.stop();
                //this.gameScreen = new EndScreen(renderer, inputHandler);
                //this.setGameScreen();
                break;
            default:
                break;
        }
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

    @Override
    public void render() {
        super.render();
    }

    // This must only be called on sysexit otherwise everything is kil
    @Override
    public void dispose() {
        // server.Stop()
        this.renderer.dispose();
        super.dispose();
    }

    public void quit() {
        dispose();
        System.exit(0);
    }
}
