package io.github.GRUMv2.EngSim;

import com.badlogic.gdx.Game;

import io.github.GRUMv2.EngSim.client.Client;

/**
 * Main
 */
public class Main extends Game {

    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;

    private final Client client;
    //private final Server server

    public Main() {
        // NOTE: At this stage of execution libGDX is not initialised
        // and will not be until it calls Main.create()
        // Anything within the constructor of Client cannot attempt
        // to interact with libGDX objects until its create() function
        client = new Client(WIDTH, HEIGHT);
        //server = new Server(120);
        //server.start();
    }

    public void create() {
        client.create();
    }

    @Override
    public void render() {
        super.render();
        // TODO: Become screen
        client.render();
    }

    @Override
    public void dispose() {
        // server.Stop()
        this.client.dispose();
        super.dispose();
    }

    public void quit() {
        dispose();
        System.exit(0);
    }
}
