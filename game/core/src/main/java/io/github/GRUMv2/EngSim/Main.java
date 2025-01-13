package io.github.GRUMv2.EngSim;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.GRUMv2.EngSim.server.Server;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.client.*;

import java.io.IOException;
import java.io.Writer;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main - Init class
 * Init LibGDX, server and general setup; handle screen changes
 */

public class Main extends Game {

    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;

    private OrthographicCamera camera;
    private InputMultiplexer inputMultiplexer;
    private Renderer renderer;
    private InputHandler inputHandler;
    private AbstractGameScreen gameScreen;

    private final Server server;
    private final Broker broker;
    private GameScreen game;

    public Main() {
        // NOTE: At this stage of execution libGDX is not initialised
        // and will not be until it calls Main.create()
        // Anything within the constructor of Client cannot attempt
        // to interact with libGDX objects until its create() function

        server = new Server(60); // Tick 60 times a second
        server.start();  // starts paused dont worry

        broker = Broker.getInstance();
    }

    /**
     * Initialise master libGDX classes used throughout client
     */
    public void create() {
        this.loadLeaderboard();
        this.camera = createCamera();
        this.inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.renderer = new Renderer(this.camera);
        this.inputHandler = new InputHandler(this.camera);
        this.game = new GameScreen(renderer, inputHandler);
        this.game.setChangeEvent(Screens.PAUSE, () -> changeScreen(Screens.PAUSE));
        this.game.setChangeEvent(Screens.END, () -> changeScreen(Screens.END));
        this.game.setChangeEvent(Screens.GAME, () -> this.changeScreen(Screens.GAME));
        this.gameScreen = this.game;
        // Start with switching to main menu
        this.changeScreen(Screens.MENU);
    }

    /**
     * Switch box to swap between different game screens, signalling the server accordingly
     * Assigns ability to switch to other screens selectively
     *
     * @param screen screen to change to
     */
    public void changeScreen(Screens screen) {
        this.gameScreen.dispose();
        switch (screen) {
            case MENU:
                this.gameScreen = new MenuScreen(renderer, inputHandler);
                this.gameScreen.setChangeEvent(Screens.GAME, () -> this.changeScreen(Screens.GAME));
                this.gameScreen.setChangeEvent(Screens.QUIT, () -> this.changeScreen(Screens.QUIT));

                if (!this.server.isPaused()) {
                    this.server.Pause();
                }
                break;
            case GAME:
                this.gameScreen = this.game;
                // hacky but works
                if (this.server.timeKeeper.currentGameTime() == 0) {
                    this.server.timeKeeper.start();
                }
                if (!this.game.isInDialog() && this.server.isPaused()) {
                    this.server.Resume();
                } else if (this.game.isInDialog()) {
                    this.server.Pause();
                }
                break;
            case PAUSE:
                this.gameScreen = new PauseScreen(renderer, inputHandler);
                this.gameScreen.setChangeEvent(Screens.GAME, () -> this.changeScreen(Screens.GAME));
                this.gameScreen.setChangeEvent(Screens.QUIT, () -> this.changeScreen(Screens.QUIT));
                this.server.Pause();
                broker.incrementGamePausedCount();
                break;
            case END:
                this.gameScreen = new EndScreen(renderer, inputHandler);
                this.gameScreen.setChangeEvent(Screens.QUIT, () -> this.changeScreen(Screens.QUIT));
                this.server.Pause();
                break;
            case QUIT:
                this.quit();
                this.server.Stop();
                break;
            default:
                break;
        }
        this.setScreen(this.gameScreen);
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
        // according to local CI this is the most efficient way to do this lol
        newCamera.position.set((float) (int) newCamera.viewportWidth / 2, (float) (int) newCamera.viewportHeight / 2, 0);
        newCamera.update();
        return newCamera;
    }

    @Override
    public void render() {
        broker.setClientHeartbeat(System.currentTimeMillis());

        // If server is kil, quit client
        if (broker.clientSuicide()) {
            System.out.println("[ HTM ] THE WHITMAN IS ACTIVE AND HAS BEEN DISPATCHED (client)");
            this.quit();
        }

        super.render();
    }

    // kill everything
    // This must only be called on sysexit otherwise everything is kil
    @Override
    public void dispose() {
        this.writeLeaderboard();
        server.Stop();
        this.renderer.dispose();
        super.dispose();
        System.out.println("aAAAaaaAAaaaAAaAA");
    }

    public void quit() {
        dispose();
        System.exit(0);
    }

    private FileHandle getLeaderboardFile() {
        return Gdx.files.local("leaderboard.csv");
    }

    /**
     * Parse saved leaderboard, if it exists, and push to Broker as SimpleImmutableEntry
     */
    private void loadLeaderboard() {
        FileHandle leaderboardFile = this.getLeaderboardFile();
        if (!leaderboardFile.exists() || leaderboardFile.isDirectory()) {
            System.out.println("[ MAN ] Could not load leaderboard file");
            return;
        }

        String leaderboard = "";
        try {
            leaderboard = leaderboardFile.readString();
        } catch (GdxRuntimeException e) {
            System.out.println("[ MAN ] Could not load leaderboard file");
            // Continue because we'll just use a blank one
        }

        Broker broker = Broker.getInstance();

        for (String line : leaderboard.split("\\r?\\n")) {
            // Ignore invalid lines and continue
            // Leaderboard is written anew on each exit
            String[] splitLine = line.split(",");
            if (splitLine.length < 2) {
                System.out.println("[ MAN ] Invalid line in leaderboard. Skipping.");
                continue;
            }
            String scoreS = splitLine[splitLine.length - 1];
            String name = String.join(",", Arrays.copyOfRange(splitLine, 0, splitLine.length - 1));

            if (name.isEmpty() || scoreS.isEmpty()) {
                System.out.println("[ MAN ] Invalid line in leaderboard. Skipping.");
                continue;
            }

            try {
                int score = Integer.parseInt(scoreS);
                broker.updateLeaderboard(name, score);
            } catch (NumberFormatException e) {
                System.out.println("[ MAN ] Invalid line in leaderboard. Skipping.");
            }
        }
    }

    /**
     * Save leaderboard to disk
     */
    private void writeLeaderboard() {
        FileHandle leaderboardFile = this.getLeaderboardFile();
        if (leaderboardFile.isDirectory()) {
            // TODO: Check file permissions?
            System.out.println("[ MAN ] Could not open leaderboard file for writing");
            return;
        }

        Writer writer;
        try {
            writer = leaderboardFile.writer(false);
        } catch (GdxRuntimeException e) {
            System.out.println("[ MAN ] Could not open leaderboard file for writing");
            System.out.println("[ MAN ] " + e);
            return;
        }

        ArrayList<SimpleImmutableEntry<String, Integer>> leaderboard = new ArrayList<>(Broker.getInstance().getLeaderboard());
        String[] splitLine = new String[leaderboard.size()];
        for (int i = 0; i < splitLine.length; i++) {
            try {
                writer.write(String.format(String.join(",", leaderboard.get(i).getKey(), String.valueOf(leaderboard.get(i).getValue())) + "%n"));
            } catch (IOException e) {
                System.out.println("[ MAN ] Could not open leaderboard file for writing");
                System.out.println("[ MAN ] " + e);
                return;
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("[ MAN ] Could not open leaderboard file for writing");
            System.out.println("[ MAN ] " + e);
        }
    }
}
