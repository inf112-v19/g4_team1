package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.roborally.RoboRally;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class winScreen implements Screen, InputProcessor, ActionListener {
    private RoboRally roboRally;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Stage stage;
    private Skin uiskin;


    public winScreen(RoboRally roboRally) {
        this.roboRally = roboRally;
        camera = new OrthographicCamera();


        FitViewport viewport = new FitViewport(1279, 639, camera);
        stage = new Stage(viewport, roboRally.batch);
        Gdx.input.setInputProcessor(stage);

        //buttons
        uiskin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        Button newGame = new TextButton("new Game", uiskin);
        newGame.setSize(111, 111);
        newGame.setPosition(400, 400);
        newGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // go to main game screen
                //roboRally.setScreen(new PreferencesScreen(roboRally));
                ArrayList<String> names = new ArrayList<>();
                names.add("player1");
                names.add("Player2");
                roboRally.setScreen(new RoboRallyGame(roboRally, names));
                dispose();
                return true;

            }
        });
        stage.addActor(newGame);
    }


    @Override
    public boolean keyDown(int i) {
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

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        roboRally.batch.setProjectionMatrix(camera.combined);
        //mapRenderer.setView(camera);
       // mapRenderer.render();
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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
