package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.roborally.RoboRally;

public class endGame implements Screen {

    private Stage stage;
    private RoboRally roboRally;
    private Skin skin;
    Image mapimg;


    public endGame(RoboRally roboRally) {
        this.roboRally = roboRally;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void show() {
        Texture background = new Texture("assets/roborally/Robot-Wall.jpg");
        Image img = new Image(background);
        img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(img);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        TextButton mainMenu = new TextButton("Main menu", skin);
        TextButton exit = new TextButton("Exit", skin);

        mapimg = new Image(new Texture("assets/roborally/endGameText.png"));
        mapimg.setSize(800, 70);
        mapimg.setPosition(Gdx.graphics.getWidth() / 2f - 350, Gdx.graphics.getHeight() / 2f + 300);
        stage.addActor(mapimg);

        table.add(mainMenu).fillX().uniformX().pad(10);
        table.row();
        table.add(exit).fillX().uniformX().pad(10);
        table.row();


        //changeImage();
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setScreen(new MainMenuScreen(roboRally));
                dispose();
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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