package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.roborally.RoboRally;

import java.util.ArrayList;

public class PreferencesScreen implements Screen, TextInputListener {

    private Stage stage;
    private RoboRally roboRally;
    //todo: Every player should be able to enter their player-name. Names should go in this list.
    private ArrayList<String> names = new ArrayList<>();
    private int numPlayers = 0;


    public PreferencesScreen(RoboRally roboRally) {
        this.roboRally = roboRally;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        TextButton numP = new TextButton("Enter Number of Players", skin);
        TextButton start = new TextButton("Start Game", skin);
        TextButton back = new TextButton("Back", skin);

        table.add(numP).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(start).fillX().uniformX();
        table.row();
        table.add(back).fillX().uniformX();

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roboRally.setScreen(new RoboRallyMainMenu(roboRally));
                dispose();
            }
        });

        numP.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getInput();
                System.out.println(numPlayers);
            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(numPlayers < 1 || numPlayers > 4) {
                    //todo: Should be explained with a message on the screen
                    System.out.println("Number of players not valid");
                    return;
                }
                roboRally.setScreen(new RoboRallyGame(roboRally, numPlayers));
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

    }

    @Override
    public void input(String s) {
        //todo: input needs be between an integer and a valid number of players. If not, an error message should be displayed.
        numPlayers = Integer.parseInt(s);
    }

    @Override
    public void canceled() {

    }
    public void getInput() {
        Gdx.input.getTextInput(this, "Number of Players", "", "1-4");

    }

    public ArrayList<String> getNames() {
        return names;
    }
}
