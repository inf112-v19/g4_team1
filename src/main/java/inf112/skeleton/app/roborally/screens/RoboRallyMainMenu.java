package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.roborally.RoboRally;

public class RoboRallyMainMenu implements Screen {
    private RoboRally roboRally;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Stage stage;
    private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    private Skin uiSkin;

    public RoboRallyMainMenu(RoboRally roboRally) {
        this.roboRally = roboRally;
        camera = new OrthographicCamera();

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets/roborally/mainmenu.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        MapProperties mProps = map.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);
        mapWidthInTiles = mProps.get("width", Integer.class);
        mapHeightInTiles = mProps.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        // camera.setToOrtho(false, mapWidthInPixels, mapHeightInPixels);
        FitViewport viewPort = new FitViewport(1279, 639, camera);
        stage = new Stage(viewPort, roboRally.batch);
        Gdx.input.setInputProcessor(stage);

        uiSkin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        Button newGame = new TextButton("New Game", uiSkin);
        newGame.setSize(tileWidth * 10, tileHeight * 2);
        newGame.setPosition(mapWidthInPixels / 4f, mapHeightInPixels / 4.5f);
        newGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                roboRally.setScreen(new RoboRallyGame(roboRally));
                dispose();
                return true;
            }
        });
        stage.addActor(newGame);

        Button exit = new TextButton("Exit", uiSkin);
        exit.setSize(tileWidth * 10, tileHeight * 2);
        exit.setPosition(mapWidthInPixels / 4f, mapHeightInPixels / 12f);
        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        stage.addActor(exit);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        roboRally.batch.setProjectionMatrix(camera.combined);

        // mapRenderer.setView(camera);
        mapRenderer.setView(camera);
        mapRenderer.render();

        stage.act();
        stage.draw();
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
        mapRenderer.dispose();
        stage.dispose();
        uiSkin.dispose();
        map.dispose();
    }
}
