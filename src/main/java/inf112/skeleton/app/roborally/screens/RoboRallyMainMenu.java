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

import java.util.ArrayList;

/**
 * main menu screen (WIP :D)
 */
public class RoboRallyMainMenu implements Screen {
    private RoboRally roboRally;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Stage stage;
    private Skin uiSkin;

    public RoboRallyMainMenu(RoboRally roboRally) {
        this.roboRally = roboRally;
        camera = new OrthographicCamera();

        // load the tiled map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets/roborally/mainmenu.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // get the props of the tiled map
        MapProperties mProps = map.getProperties();
        int tileWidth = mProps.get("tilewidth", Integer.class);
        int tileHeight = mProps.get("tileheight", Integer.class);
        int mapWidthInTiles = mProps.get("width", Integer.class);
        int mapHeightInTiles = mProps.get("height", Integer.class);
        int mapWidthInPixels = mapWidthInTiles * tileWidth;
        int mapHeightInPixels = mapHeightInTiles * tileHeight;

        // set the position of the camera
        FitViewport viewPort = new FitViewport(1279, 639, camera);

        // stage that contains objects that will be shown on the screen
        stage = new Stage(viewPort, roboRally.batch);

        // read the mouse input
        Gdx.input.setInputProcessor(stage);

        // skin for the buttons
        uiSkin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        // create two buttons for "New Game" and "Exit" respectively
        // and add them to the stage
        Button newGame = new TextButton("New Game", uiSkin);
        newGame.setSize(tileWidth * 10, tileHeight * 2);
        newGame.setPosition(mapWidthInPixels / 4f, mapHeightInPixels / 4.5f);
        newGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // go to main game screen
                //roboRally.setScreen(new PreferencesScreen(roboRally));
                ArrayList<String> names = new ArrayList<>();
                names.add("player1");
                names.add("Player2");
                //names.add("Player3");
                roboRally.setScreen(new RoboRallyGame(roboRally, names));
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

        // position of the background image is temporary
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
