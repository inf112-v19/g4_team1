package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.roborally.RoboRally;

public class RoboRallyGame implements Screen {

    RoboRally roboRally;
    Texture img;
    TiledMap board;
    OrthographicCamera camera;
    TiledMapRenderer boardRenderer;
    SpriteBatch sb;
    Sprite sprite;
    private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles, mapWidthInPixels, mapHeightInPixels;

    public RoboRallyGame(RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        // camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        sb = new SpriteBatch();

        board = new TmxMapLoader().load("assets/roborally/game_board.tmx");

        MapProperties mProps = board.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);
        mapWidthInTiles = mProps.get("width", Integer.class);
        mapHeightInTiles = mProps.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        boardRenderer = new OrthogonalTiledMapRenderer(board);
        camera.setToOrtho(false, mapWidthInPixels, mapHeightInPixels);
        boardRenderer.setView(camera);



        sprite = new Sprite(new Texture("assets/roborally/robot.png"));
        sprite.setSize(128,128);
        sprite.setPosition(32, 32);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boardRenderer.render();
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        board.dispose();
        sb.dispose();
    }
}
