package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.roborally.RoboRally;

import java.util.ArrayList;

public class PreferencesScreen implements Screen {

    private Stage stage;
    private RoboRally roboRally;
    private Table playerTable;
    private ArrayList<String> names = new ArrayList<>();
    private Skin skin;



    public PreferencesScreen(RoboRally roboRally) {
        this.roboRally = roboRally;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Texture background = new Texture("assets/roborally/Robot-Wall.jpg");
        Image img = new Image(background);
        // img.setPosition(150,0);
        //  img.setHeight(img.getHeight()-50);
        img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(img);

        Table table = new Table();
        playerTable = new Table();
        playerTable.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 100);
        playerTable.setDebug(false);
        playerTable.top();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        stage.addActor(playerTable);
        skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        //todo: button for removing player from playerlist?
        TextButton add = new TextButton("Add Human", skin);
        TextButton start = new TextButton("Start Game", skin);
        TextButton back = new TextButton("Back", skin);
        TextButton AI = new TextButton("Add AI", skin);
        Label players = new Label("PLAYERS: ", skin);
        players.setFontScale(2);
        players.getStyle().fontColor = Color.RED;

        playerTable.add(players).uniform();
        table.add(add).fillX().uniformX();
        table.row();
        table.add(AI).fillX().uniformX();
        table.row();
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

        AI.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (names.size() == 8) {
                    System.out.println("Cant add more players");
                    addPlayerError();
                    return;
                }
                names.add("AI");
                Label namelabel = new Label("AI", skin);
                players.getStyle().fontColor = Color.GREEN;
                namelabel.setFontScale(1.5f);
                playerTable.row();
                playerTable.add(namelabel).uniform();

            }
        });

        add.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (names.size() == 8) {
                    //TODO: should display msg on screen
                    System.out.println("Cant add more players");
                    addPlayerError();
                    return;
                }
                TextInputListener inputName = new TextInputListener() {
                    @Override
                    public void input(String s) {
                        if (names.contains(s)) {
                            //todo: should display msg on screen
                            //name already exist
                            System.out.println("Name already taken");
                            return;
                        }
                        names.add(s);
                        Label namelabel = new Label(s, skin);
                        players.getStyle().fontColor = Color.GREEN;
                        namelabel.setFontScale(1.5f);
                        playerTable.row();
                        playerTable.add(namelabel).uniform();


                    }

                    @Override
                    public void canceled() {

                    }
                };
                Gdx.input.getTextInput(inputName, "Enter Player Name", "", "");

            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (names.size() < 1 || names.size() > 8) {
                    //todo: Should be explained with a message on the screen
                    System.out.println("Number of players not valid");
                    return;
                }
                roboRally.setScreen(new RoboRallyGame(roboRally, names));
                dispose();

            }
        });
    }

    private void addPlayerError(){
        Dialog dialog = new Dialog("warning", skin){
            public void result(Object obj){
                System.out.println(obj);
            }
        };
        dialog.setSize(300, 300);

        dialog.button("OK", true);
        dialog.show(stage);

        dialog.text("cant add more than 8 players").setSize(300, 300);
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