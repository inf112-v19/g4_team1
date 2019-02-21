package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.roborally.board.Board;

public class RoboRallyGame implements Screen, InputProcessor {

    private RoboRally roboRally;
    private Texture img;
    private TiledMap board;
    private OrthographicCamera camera;
    private TiledMapRenderer boardRenderer;
    private SpriteBatch sb;
    private Sprite sprite;
    private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles, mapWidthInPixels, mapHeightInPixels;
    private Board gameBoard;
    private Array<Rectangle> tiles;
    private Stage stage;
    private Skin uiSkin;

    public RoboRallyGame(RoboRally roboRally) {
        this.roboRally = roboRally;

        camera = new OrthographicCamera();
        // camera.setToOrtho(false, 639, 639);
        // camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        sb = new SpriteBatch();

        board = new TmxMapLoader().load("assets/roborally/game_board32New.tmx");

        MapProperties mProps = board.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);
        mapWidthInTiles = mProps.get("width", Integer.class);
        mapHeightInTiles = mProps.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        gameBoard = new Board(mapHeightInTiles, mapWidthInPixels, tileWidth, tileHeight);
        tiles = new Array<>();

        for (int i = 0; i < gameBoard.getWidth(); i++) {
            for (int j = 0; j < gameBoard.getHeight(); j++) {
                Rectangle tile = new Rectangle();
                tile.x = gameBoard.get(i, j).getX();
                tile.y = gameBoard.get(i, j).getY();
                tile.width = tileWidth;
                tile.height = tileHeight;
                tiles.add(tile);
            }
        }

        boardRenderer = new OrthogonalTiledMapRenderer(board);
        camera.setToOrtho(false, mapWidthInPixels - 1, mapHeightInPixels - 1);
        boardRenderer.setView(camera);

        sprite = new Sprite(new Texture("assets/roborally/robot.png"));
        sprite.setSize(tileWidth, tileHeight);

        sprite.setPosition(gameBoard.get(19, 8).getX(), gameBoard.get(19, 8).getY());

        Gdx.input.setInputProcessor(this);

        FitViewport viewPort = new FitViewport(mapWidthInPixels - 1, mapHeightInPixels - 1, camera);
    }

    @Override
    public void show() {

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

    @Override
    public boolean keyDown(int key) {
        if (key== Input.Keys.LEFT)
            sprite.translate(-tileWidth, 0);
        if (key== Input.Keys.RIGHT)
            sprite.translate(tileWidth, 0);
        if (key== Input.Keys.UP)
            sprite.translate(0, tileWidth);
        if (key== Input.Keys.DOWN)
            sprite.translate(0, -tileWidth);
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
