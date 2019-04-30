package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.roborally.RoboRally;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class winScreen implements Screen, InputProcessor, ActionListener {
    private RoboRally roboRally;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin uiskin;
    private SpriteBatch batch;
    private BitmapFont font;
    private String winner;
    private Label.LabelStyle labelStyle;


    public winScreen(RoboRally roboRally, String winName) {
        this.roboRally = roboRally;
        camera = new OrthographicCamera();
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().scale(13);
        batch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;
        Label winMessage = new Label(winName + " is the winner!!!", labelStyle);
        //FitViewport viewport = new FitViewport(1279, 639, camera);
        ScalingViewport viewPort = new ScalingViewport(Scaling.stretch, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        viewPort.update(Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        stage = new Stage(viewPort, roboRally.batch);
        winMessage.setPosition(500, 1000);
        stage.addActor(winMessage);
        Gdx.input.setInputProcessor(stage);

        //buttons
        uiskin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        Button newGame = new TextButton("new Game", uiskin);
        newGame.setSize(200, 111);
        newGame.setPosition(300, 100);
        newGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // go to main game screen
                roboRally.setScreen(new PreferencesScreen(roboRally));
                //ArrayList<String> names = new ArrayList<>();
                //names.add("player1");
                //names.add("Player2");
                //roboRally.setScreen(new RoboRallyGame(roboRally, names));
                dispose();
                return true;

            }
        });
        stage.addActor(newGame);
        Button exit = new TextButton("exit", uiskin);
        exit.setSize(200, 111);
        exit.setPosition(700, 100);
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
        //  Gdx.gl.glClearColor(119/255f,136/255f,153/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //roboRally.batch.setProjectionMatrix(camera.combined);
        //mapRenderer.setView(camera);
        // mapRenderer.render();
        camera.update();
        stage.act();
        stage.draw();
        //batch.begin();
       // font.draw(batch, winner, 500, 1200);
        //batch.end();

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
