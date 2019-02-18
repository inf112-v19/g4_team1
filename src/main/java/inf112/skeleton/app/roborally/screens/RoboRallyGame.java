package inf112.skeleton.app.roborally.screens;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.roborally.board.Board;

public class RoboRallyGame implements Screen {

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

    public RoboRallyGame(RoboRally roboRally) {
        this.roboRally = roboRally;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 639, 639);
        // camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        sb = new SpriteBatch();

        /*board = new TmxMapLoader().load("assets/roborally/game_board.tmx");

        MapProperties mProps = board.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);
        mapWidthInTiles = mProps.get("width", Integer.class);
        mapHeightInTiles = mProps.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;*/

        gameBoard = new Board(20, 20, 32, 32);
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

        // boardRenderer = new OrthogonalTiledMapRenderer(board);
        camera.setToOrtho(false, 639, 639);
        // boardRenderer.setView(camera);

        sprite = new Sprite(new Texture("assets/roborally/robot.png"));
        sprite.setSize(64,64);

        sprite.setPosition(gameBoard.get(19, 19).getX(), gameBoard.get(19, 19).getY());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // boardRenderer.render();
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
