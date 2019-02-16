package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class RoboRallyGame extends Game {

    Texture img;
    TiledMap board;
    OrthographicCamera camera;
    TiledMapRenderer boardRenderer;
    SpriteBatch sb;
    Sprite sprite;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        // camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        sb = new SpriteBatch();

        board = new TmxMapLoader().load("assets/roborally/game_board.tmx");
        boardRenderer = new OrthogonalTiledMapRenderer(board, sb);
        boardRenderer.setView(camera.combined, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        sprite = new Sprite(new Texture("assets/roborally/robot.png"));
        sprite.setSize(64, 64);
        sprite.setPosition(32, 32);
    }

    @Override
    public void render() {
        boardRenderer.render();
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        board.dispose();
        sb.dispose();
    }
}
