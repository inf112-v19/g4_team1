package inf112.skeleton.app.roborally;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.base.MP.SimpleClient;
import inf112.skeleton.app.base.MP.SimpleServer;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;
import inf112.skeleton.app.roborally.screens.RoboRallyMainMenu;
import inf112.skeleton.app.roborally.screens.winScreen;
import inf112.skeleton.app.roborally.screens.*;

/**
 * the main game class that is passed between screens
 */
public class RoboRally extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public SimpleServer server;
    public SimpleClient client;

    @Override
    public void create() {
//        Gdx.graphics.setContinuousRendering(false);
//        Gdx.graphics.requestRendering();
        batch = new SpriteBatch();
        font = new BitmapFont();

        // go to main menu screen
        this.setScreen(new MainMenuScreen(this));
        //this.setScreen(new winScreen(this,"wa"));
    }


    @Override
    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
