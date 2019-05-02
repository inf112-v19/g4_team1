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

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        mapimg = new Image(new Texture("assets/roborally/winScreenText.png"));
        mapimg.setSize(900,100);
        mapimg.setPosition(Gdx.graphics.getWidth()/2f-450, Gdx.graphics.getHeight()/2f+750);
        stage.addActor(mapimg);


        TextButton mainMenu = new TextButton("Main menu", skin);
        TextButton exit = new TextButton("Exit", skin);


        table.add(mainMenu).fillX().uniformX().pad(10);
        table.row();
        table.add(exit).fillX().uniformX().pad(10);
        table.row();

        mainMenu.setPosition(Gdx.graphics.getWidth()/2f-100,Gdx.graphics.getHeight()/2f+100);

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
