package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.roborally.RoboRally;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * A win screen when one of the players/AI robots visits all flags
 */
public class WinScreen implements Screen {
    private Stage stage;
    private RoboRally roboRally;
    private Skin skin;
    private String winName;

    WinScreen(RoboRally roboRally, String winName) {
        this.roboRally = roboRally;
        this.winName = winName;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));
    }

    @Override
    public void show() {
        Texture background = new Texture("assets/roborally/Robot-Wall.jpg");
        Texture winner = new Texture("assets/roborally/winner.png");
        Image img = new Image(background);
        Image winnerImg = new Image(winner);
        img.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        winnerImg.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-400);
        Label label = new Label(winName + " won!", skin);
        label.setPosition(Gdx.graphics.getWidth()/2 - 50, Gdx.graphics.getHeight()-430);
        label.setFontScale(1.5f);
        stage.addActor(img);
        stage.addActor(winnerImg);
        stage.addActor(label);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        TextButton mainMenu = new TextButton("Main menu", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(mainMenu).fillX().uniformX().pad(10);
        table.row();
        table.add(exit).fillX().uniformX().pad(10);
        table.row();

        mainMenu.setPosition(Gdx.graphics.getWidth()/2f-100,Gdx.graphics.getHeight()/2f+100);

        // exit button
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // main menu button
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setScreen(new MainMenuScreen(roboRally, null));
                dispose();
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        skin.dispose();
    }
}
