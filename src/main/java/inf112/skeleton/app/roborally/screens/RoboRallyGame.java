package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.board.boardelement.IActiveElement;
import inf112.skeleton.app.base.board.boardelement.Laser;
import inf112.skeleton.app.base.board.boardelement.WrenchTile;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;
import inf112.skeleton.app.base.cards.CardType;

import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static inf112.skeleton.app.base.utils.Direction.EAST;

/**
 * main game screen
 */
public class RoboRallyGame implements Screen, InputProcessor, ActionListener {
    private RoboRally roboRally;
    private TiledMap board;
    private OrthographicCamera camera;
    private TiledMapRenderer boardRenderer;
    private SpriteBatch sb;
    private int tileWidth;
    private int tileHeight;
    private Board gameBoard;
    private Stage stage;
    private Map<Robot, Sprite> robotSprites = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>();
    private CardDecks cardDecks = new CardDecks();
    private ArrayList<IActiveElement> ActiveElements;
    private ArrayList<Flag> flags  ;
    private ArrayList<WrenchTile> wrenches;

    private ArrayList<Card > currentPlayerCards = new ArrayList<>();

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private enum State {
        PAUSE,
        RUN
    }
    private State state = State.RUN;


    //TODO? Player player = new Player("test");

    public RoboRallyGame(RoboRally roboRally) {
        stage = new Stage();

        // get the game itself from the previous screen
        this.roboRally = roboRally;

        // set the camera
        camera = new OrthographicCamera();
        FitViewport viewPort = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        camera.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2,0);

        sb = new SpriteBatch();

        board = new TmxMapLoader().load("assets/roborally/game_boardNew.tmx");

        // get the properties of the tilemap
        MapProperties mProps = board.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);

        //create gameboard from tmx file
        gameBoard = new Board(board);

        ActiveElements = gameBoard.getActiveElements();
        flags = gameBoard.getFlags();
        wrenches = gameBoard.getWrenches();

        // initialize the board renderer that will render the tiled map
        boardRenderer = new OrthogonalTiledMapRenderer(board,1);
        //This line decides how much the gamescreen will show in addition to the gameboard
        camera.setToOrtho(false, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        boardRenderer.setView(camera);

        // can be changed to "this"
        // initialize the input processor for testing purposes
        Gdx.input.setInputProcessor(stage);

        try {
            System.out.println("Start game");
            startGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // set up the players before starting game
    private void startGame() throws InterruptedException {
        int NPLAYERS = 1;
        for (int i = 0; i < NPLAYERS; i++) {
            Player player = new Player("test");
            Robot robot = new Robot(gameBoard.getSpawn(), Direction.NORTH, player, gameBoard);
            gameBoard.addTileObject(robot);
            player.addRobot(robot);
            players.add(player);
            Sprite sprite  = new Sprite(new Texture("assets/roborally/robot.png"));
            sprite.setSize(tileWidth, tileHeight);

            robotSprites.put(robot, sprite);
            System.out.println("finished adding robots");
        }
        updateAllSprites(players);
        doTurn();


    }
    private void doTurn() throws InterruptedException {
        for (Player player : players) {
            //players program their robots
            //choosecards() updates currentPlayerCards
            System.out.println("choose cards");
            chooseCards(player.getRobot().getHealth());
            player.setCards(currentPlayerCards);
        }
    }
    private void continueTurn(){
        //player have finished choosing cards
        boolean finishedExecute = false;
        while (!finishedExecute) {
            //players should be sorted by their first cards priority number
//            players.sort(new Comparator<Player>() {
//                public int compare(Player player2, Player player1) {
//                    if (!player1.getCards().isEmpty() && !player2.getCards().isEmpty())
//                        return player2.getCards().get(0).getPriorityNumber()-player1.getCards().get(0).getPriorityNumber();
//                    else return 0;
//                }
//            });
            finishedExecute = true;
            for (Player current : players) {
                if (current.getCards().size() != 0) {
                    finishedExecute = false;
                    Card card = current.useFirstCard();
                    card.execute(current.getRobot());
                    System.out.println(current.getRobot().getPos());
                    updateAllSprites(players);
                    cardDecks.addUsed(card);
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
            //activate board elements, then lasers
            for(IActiveElement elem : ActiveElements){
                if(!(elem instanceof Laser)){
                    elem.activate();
                }
            }
            for(IActiveElement elem : ActiveElements){
                if(elem instanceof Laser){
                    elem.activate();
                }
            }
            //end of phase
        }
        //check for flags and wrenches at end of turn
        for (Player player : players){
            Pos robotpos = player.getRobot().getPos();
            for (int i = 0; i < flags.size(); i++) {
                flags.get(i).setRespawn();
            }
            for (int i = 0; i < wrenches.size(); i++) {
                wrenches.get(i).setRespawn();
            }
        }
        //check for win condition
        for(Player player : players){
            if (player.getRobot().getFlags().size() == flags.size()){
                win(player);
            }
        }
        try {
            //starts next round
            doTurn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method updates currentPlayerCards with the cards that is selected
     */
    private void chooseCards(int nCards) throws InterruptedException {
        currentPlayerCards.clear();
        ArrayList<Card> availableCards = cardDecks.getCards(nCards);
        ArrayList<Card> selectedCards = new ArrayList<>();
        ArrayList<Boolean> usedslots = new ArrayList<>();
        ArrayList<Boolean> finished = new ArrayList<>();
        for (int i = 0; i < nCards; i++) {
            usedslots.add(false);
        }

        HashMap<Card, Button> buttonsAndCards = new HashMap<>();

        //Creating a button for each card
        for (int i = 0; i < availableCards.size(); i++) {
            Card card = availableCards.get(i);
            int number = i;
            Texture testTexture = new Texture("assets/roborally/cards/movement/" + card.imageFileName());
            Drawable drawable = new TextureRegionDrawable(testTexture);
            Button button = new Button(drawable);
            button.setPosition(96 + 125 * i, 96 * 9);
            buttonsAndCards.put(card, button);
            stage.addActor(button);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    System.out.println("klicked " + card);

                    //stage.getActors().removeIndex(stage.getActors().indexOf(button, false));

                    if (usedslots.get(number)) {

                    } else {
                        if (selectedCards.size() < 5) {
                            if (!selectedCards.contains(card)) {
                                stage.getActors().get(stage.getActors().indexOf(
                                        button, false)).setPosition(
                                        96 * 16, 96 * (9 - selectedCards.size() * 2));
                                selectedCards.add(card);
                                availableCards.remove(card);
                            }
                        }
                    }
                }
            });
        }

        //make reset button
        Texture resetTexture = new Texture("assets/roborally/cards/option - 3.jpg");
        Drawable resetDrawable = new TextureRegionDrawable(resetTexture);
        Button reset_button = new Button(resetDrawable);
        reset_button.setPosition((96*15)-30,96*5);
        reset_button.setSize(96,96);
        stage.addActor(reset_button);
        reset_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

                if (selectedCards.size() > 0) {
                    currentPlayerCards.removeAll(selectedCards);

                    // remove the available cards from the screen
                    int count= buttonsAndCards.size();
                    int j = 0;
                    for (Button btn: buttonsAndCards.values()) {
//                        stage.getActors().removeValue(buttonsAndCards.get(
//                                selectedCards.remove(0)), false);
                        stage.getActors().get(stage.getActors().indexOf(btn,false)).setPosition(
                                96+125*j,96*9);
                        j++;

                    }
                    availableCards.addAll(selectedCards);
                    selectedCards.clear();
                }
            }
        });

        //make finish button
        Texture testTexture = new Texture("assets/roborally/robot.png");
        Drawable drawable = new TextureRegionDrawable(testTexture);
        Button finish_button = new Button(drawable);
        finish_button.setPosition(96, 300);
        stage.addActor(finish_button);
        finished.add(false);

        finish_button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("klicked finish");
                if(selectedCards.size() == 5) {
                    currentPlayerCards.addAll(selectedCards);
                    System.out.println("selected: " + currentPlayerCards);

                    // remove the available and selected cards from the screen
                    int count = availableCards.size();
                    for (int i = 0; i <count; i++) {
                        cardDecks.addUsed(availableCards.get(0));
                        stage.getActors().removeValue(buttonsAndCards.get(
                                availableCards.remove(0)), false);
                    }

                    // remove the finish button from the screen
                    stage.getActors().removeValue(finish_button, false);
                    stage.getActors().removeValue(reset_button,false);
                    for(Card card : selectedCards){
                        stage.getActors().get(stage.getActors().indexOf(
                                buttonsAndCards.get(card),false)).remove();

                        // backup code to remove listeners from buttons
//                        stage.getActors().get(stage.getActors().indexOf(
//                                buttonsAndCards.get(card),false)).removeListener(buttonsAndCards.get(
//                                card).getListeners().get(0));
                    }
                    //continue game when finished selecting cards
                    continueTurn();

                }else{
                    System.out.println("not enough cards");
                }
            }
        });
    }

    // WIP
    private void win(Player player) {
        //TODO:
        // player wins
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        boardRenderer.setView(camera);
        boardRenderer.render();

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        for (Player player : players) {
            robotSprites.get(player.getRobot()).draw(sb);
        }
        sb.end();

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
        board.dispose();
        sb.dispose();
    }

    @Override
    public boolean keyDown(int key) {
        // reads input from user and moves the first robot on the screen for testing
        if (key == Input.Keys.LEFT)
            players.get(0).getRobot().move(Direction.WEST);
        if (key == Input.Keys.RIGHT)
            players.get(0).getRobot().move(EAST);
        if (key == Input.Keys.UP)
            players.get(0).getRobot().move(Direction.NORTH);
        if (key == Input.Keys.DOWN)
            players.get(0).getRobot().move(Direction.SOUTH);
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


    private void updateAllSprites(ArrayList<Player> players) {
        for(Player player : players){
            Robot robot = player.getRobot();
            Sprite sprite = robotSprites.get(robot);
            sprite.setPosition(robot.getPos().x() * tileWidth, robot.getPos().y() * tileWidth);
        }
    }
}
