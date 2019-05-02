package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.base.MP.SimpleServer;
import inf112.skeleton.app.roborally.RoboRally;

import java.io.IOException;
import java.util.ArrayList;

public class MultiplayerRoboRallyGame implements Screen, Runnable {

    private Stage stage;
    private RoboRally roboRally;
    private Table playerTable, table;
    private ArrayList<String> names = new ArrayList<>();
    private Skin skin;
    private SimpleServer server;
    private Thread screenThread;
    private Label systemMessage;


    public MultiplayerRoboRallyGame(RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture background = new Texture("assets/roborally/Robot-Wall.jpg");
        Image img = new Image(background);
        img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(img);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

//        playerTable = new Table();
//        playerTable.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 100);
//        playerTable.setDebug(false);
//        playerTable.top();
//        stage.addActor(playerTable);

        skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));

        TextButton createSer = new TextButton("Create a server", skin);
        TextButton connToSer = new TextButton("Connect to a server", skin);
        TextButton back = new TextButton("Back", skin);

        table.add(createSer).fillX().uniformX().pad(10);
        table.row();
        table.add(connToSer).fillX().uniformX().pad(10);
        table.row();
        table.add(back).fillX().uniformX().pad(10);

        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                roboRally.setScreen(new PreferencesScreen(roboRally));
                dispose();
                return true;
            }
        });

        createSer.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                roboRally.setScreen(new CreateServerScreen());
                table.removeActor(createSer);
                table.removeActor(connToSer);
                table.removeActor(back);
                return true;
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
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

    @Override
    public void run() {

    }

    class CreateServerScreen implements Screen {

        public CreateServerScreen() {
            TextButton back = new TextButton("Back", skin);
            TextButton createSrvr = new TextButton("Create server", skin);
            systemMessage = new Label("", skin);
            systemMessage.setFontScale(1.5f);

            table.row();
            table.add(systemMessage).fillX().uniformX().padBottom(50);

            back.addListener(new ClickListener() {
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    roboRally.setScreen(new MultiplayerRoboRallyGame(roboRally));
                    dispose();
                }
            });

            createSrvr.addListener( new ClickListener() {
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    startServer();
                    System.out.println("entered creating server");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (server != null) {
                        table.getCell(systemMessage).getActor().setText(server.message);
                    }
                }
            });

            table.row();
            table.add(createSrvr).fillX().uniformX().pad(10);
            table.row();
            table.add(back).fillX().uniformX().pad(10);

        }

        @Override
        public void show() {

        }

        @Override
        public void render(float v) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(v);
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
            stage.dispose();
            skin.dispose();
        }

        public void startServer() {
            try {
                server = new SimpleServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class ConnectToServerScreen implements Screen {

        public ConnectToServerScreen() {
            TextButton back = new TextButton("Back", skin);

            back.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    MultiplayerRoboRallyGame.this.show();
                    table.removeActor(back);
                }
            });

            table.row();
            table.add(back).fillX().uniformX().pad(10);
        }

        @Override
        public void show() {

        }

        @Override
        public void render(float v) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(v);
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
    }


}
