package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import inf112.skeleton.app.base.MP.SimpleClient;
import inf112.skeleton.app.base.MP.SimpleServer;
import inf112.skeleton.app.roborally.RoboRally;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class MultiplayerRoboRallyGame implements Screen, Runnable {
    private Stage stage;
    private RoboRally roboRally;
    private Table playerTable, table;
    private ArrayList<String> names = new ArrayList<>();
    private Skin skin;
    private SimpleServer server;
    private SimpleClient client;
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

        systemMessage = new Label("", skin);
        systemMessage.setFontScale(1.5f);

        TextButton createSer = new TextButton("Create a server", skin);
        TextButton connToSer = new TextButton("Connect to a server", skin);
        TextButton back = new TextButton("Back", skin);

        table.row();
        table.add(systemMessage).fillX().uniformX().padBottom(50);
        table.row();
        table.add(createSer).fillX().uniformX().pad(10);
        table.row();
        table.add(connToSer).fillX().uniformX().pad(10);
        table.row();
        table.add(back).fillX().uniformX().pad(10);

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

        connToSer.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                roboRally.setScreen(new ConnectToServerScreen());
                table.removeActor(createSer);
                table.removeActor(connToSer);
                table.removeActor(back);
                return true;
            }
        });

        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                roboRally.setScreen(new MainMenuScreen(roboRally, server));
                dispose();
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
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
    public void run() {

    }

    class CreateServerScreen implements Screen {
        int numPlayers;

        public CreateServerScreen() {
            Gdx.input.setInputProcessor(stage);

            TextButton back = new TextButton("Back", skin);
            TextButton createSrvr = new TextButton("Create server", skin);

            Input.TextInputListener inputMessage = new Input.TextInputListener() {
                @Override
                public void input(String text) {
                    numPlayers = Integer.parseInt(text);
                }
                @Override
                public void canceled() {

                }
            };

            Gdx.input.getTextInput(inputMessage, "Number of players (int): ", "", "");

            back.addListener(new ClickListener() {
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    roboRally.setScreen(new MultiplayerRoboRallyGame(roboRally));
                    dispose();
                }
            });

            createSrvr.addListener( new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    systemMessage.setText("Creating the server...");

                    try {
                        server = new SimpleServer();
                        server.numOfPlayers = numPlayers;
                        roboRally.setScreen(new MainMenuScreen(roboRally, server));
                        dispose();
                    } catch (Exception e) {
                        systemMessage.setText("Already hosting. Waiting for connections...");
                    }

                    if (server != null) {
                        systemMessage.setText(server.status);
                        roboRally.server = server;
                    }
                }
            });

            table.row();
            table.add(createSrvr).fillX().uniformX().pad(10);
            table.row();
            table.add(back).fillX().uniformX().pad(10);

        }
        @Override
        public void render(float v) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(v);
            stage.draw();
        }

        @Override
        public void dispose() {
            stage.dispose();
            skin.dispose();
        }

//        public void startServer() {
//            try {
//                server = new SimpleServer();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        @Override
        public void show() {

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
    }

    class ConnectToServerScreen implements Screen {
        private String host, message;
        private Label msg;

        public ConnectToServerScreen() {
            Gdx.input.setInputProcessor(stage);
            msg = new Label("", skin);
            msg.setFontScale(1.5f);

            TextButton back = new TextButton("Back", skin);
            TextButton connToSrvr = new TextButton("Connect to server", skin);
            TextButton play = new TextButton("Play", skin);
//            TextButton enterMsg = new TextButton("Enter the message", skin);
//            TextButton sendMsg = new TextButton("Send the message", skin);
//            TextButton refresh = new TextButton("Refresh", skin);

            Input.TextInputListener inputHost = new Input.TextInputListener() {
                @Override
                public void input(String text) {
                    host = text;
                    systemMessage.setText("Host @ " + text);

                    try {
                        systemMessage.setText("Connecting...");
                        client = new SimpleClient(text);
                        systemMessage.setText(client.status);
                    } catch (InterruptedException | UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void canceled() {

                }
            };

            Input.TextInputListener inputMessage = new Input.TextInputListener() {
                @Override
                public void input(String text) {
                    message = text;
                }
                @Override
                public void canceled() {

                }
            };

            Gdx.input.getTextInput(inputHost, "Enter the Host IP: ", "", "");

            connToSrvr.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        client = new SimpleClient(host);
                        systemMessage.setText(client.status);
                    } catch (InterruptedException | UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            });

            play.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.input.getTextInput(inputMessage, "Your nickname:", "", "");

                    try {
                        Thread.sleep(10000);
                        client.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            back.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    roboRally.setScreen(new MultiplayerRoboRallyGame(roboRally));
                    dispose();
                }
            });

//            enterMsg.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    Gdx.input.getTextInput(inputMessage, "Enter the message to send: ", "", "");
//
//                    try {
//                        Thread.sleep(1000);
//                        msg.setText("Sending message...");
//                        client.sendMessage(message);
//
//                        Thread.sleep(3000);
//
//                        if (client.gotMessage[0])
//                            msg.setText("Response from the server: \"" + client.getMessage().toString() + "\"");
//
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

//            sendMsg.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    try {
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

//            refresh.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    if (client.gotMessage[0])
//                        msg.setText(client.getMessage().toString());
//                }
//            });

            table.row();
            table.add(systemMessage).pad(10);
            table.row();
            table.add(connToSrvr).pad(10);
            table.row();

//            table.add(enterMsg).fillX().uniformX().pad(10);
//            table.row();
//            table.add(sendMsg).fillX().uniformX().pad(10);
//            table.row();
            table.add(play).pad(10);
            table.row();
            table.add(back).pad(10);
            table.row();
//            table.add(refresh).fillX().uniformX().pad(10);
//            table.row();
//            table.add(msg).fillX().uniformX().padTop(40);
        }

        @Override
        public void render(float v) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(v);
            stage.draw();
        }

        @Override
        public void dispose() {
            stage.dispose();
            skin.dispose();
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
        public void show() {

        }
    }
}
