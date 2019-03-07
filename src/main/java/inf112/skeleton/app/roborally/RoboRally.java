package inf112.skeleton.app.roborally;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;
import inf112.skeleton.app.roborally.screens.RoboRallyMainMenu;

/**
 * the main game class that is passed between screens
 */
public class RoboRally extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // go to main menu screen
        this.setScreen(new RoboRallyMainMenu(this));
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
