package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.roborally.RoboRally;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class winScreen implements Screen {
    private Stage stage;
    private RoboRally roboRally;
    private Skin skin;
    Image mapimg;


    public winScreen(RoboRally roboRally, String winName) {
        this.roboRally = roboRally;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        Texture background= new Texture("assets/roborally/winGameImage.jpg");
        Image img = new Image(background);
        img.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()-100);
        stage.addActor(img);

        mapimg = new Image(new Texture("assets/roborally/winScreenText.png"));
        mapimg.setSize(900,100);
        mapimg.setPosition(Gdx.graphics.getWidth()/2f-450, Gdx.graphics.getHeight()/2f+750);
        stage.addActor(mapimg);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //roboRally.batch.setProjectionMatrix(camera.combined);
        //mapRenderer.setView(camera);
        // mapRenderer.render();
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
}
