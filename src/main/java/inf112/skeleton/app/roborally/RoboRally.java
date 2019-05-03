package inf112.skeleton.app.roborally;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.base.MP.SimpleClient;
import inf112.skeleton.app.base.MP.SimpleServer;

import inf112.skeleton.app.roborally.screens.*;

/**
 * The main game class that is passed between screens
 */
public class RoboRally extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    public SimpleServer server;
    public SimpleClient client;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // go to main menu screen
        this.setScreen(new MainMenuScreen(this, null));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
