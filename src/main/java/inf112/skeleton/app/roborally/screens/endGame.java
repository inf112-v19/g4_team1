package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.roborally.RoboRally;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class endGame implements Screen {

    private Stage stage;
    private RoboRally roboRally;
    private Table playerTable;
    private ArrayList<String> names = new ArrayList<>();
    private Skin skin;
    private ArrayList<String> maps= new ArrayList<>();
    private int mapindex = 0;
    Image mapimg;




    public endGame(RoboRally roboRally) {
        this.roboRally = roboRally;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.maps.add("assets/roborally/game_board1.tmx");
        this.maps.add("assets/roborally/game_board2.tmx");
        this.maps.add("assets/roborally/game_board3.tmx");

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
                roboRally.setScreen(new PreferencesScreen(roboRally));
                dispose();
            }
        });
    }


    private void changeImage(){
        mapimg.setDrawable(new SpriteDrawable(new Sprite(new Texture("assets/roborally/mapimages/map"+(mapindex+1)+".png"))));


        /*
        mapimg = new Image(new Texture("assets/roborally/mapimages/map"+(mapindex+1)+".png"));
        mapimg.setSize(400, 400);
        mapimg.setPosition(500, 500);
        stage.addActor(mapimg);
        */


    }
    private void errorMsg(String err){
        Dialog dialog = new Dialog("warning", skin){
            public void result(Object obj){
            }
        };
        dialog.setSize(300, 300);

        dialog.button("OK", true);
        dialog.show(stage);

        dialog.text(err).setSize(300, 300);
        dialog.getTitleLabel().setFontScale(1);
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