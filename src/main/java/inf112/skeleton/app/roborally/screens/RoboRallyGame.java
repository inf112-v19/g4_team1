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
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static inf112.skeleton.app.base.utils.Direction.EAST;

/**
 * main game screen
 */
public class RoboRallyGame implements Screen, InputProcessor {
    private RoboRally roboRally;
    private TiledMap board;
    private OrthographicCamera camera;
    private TiledMapRenderer boardRenderer;
    private SpriteBatch sb;
    private Sprite sprite;
    private Player player;
    private int tileWidth;
    private int tileHeight;
    private Board gameBoard;
    private Array<Rectangle> tiles;
    private Stage stage;
    private Skin uiSkin;
    private FitViewport viewPort;
    private Sprite[] cardSprite;
    private CardDecks cardDecks = new CardDecks();
    private ArrayList<Card > currentPlayerCards = new ArrayList<>();

    private enum State {
        PAUSE,
        RUN
    }
    private State state = State.RUN;


    //TODO? Player player = new Player("test");

    public RoboRallyGame(RoboRally roboRally) {
        stage = new Stage();

        // set of cards for testing
        //cards = new Card[9];
        //cardSprite = new Sprite[9];

        // get the game itself from the previous screen
        this.roboRally = roboRally;

        // set the camera
        camera = new OrthographicCamera();
        viewPort = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT,camera);
        camera.position.set(viewPort.getWorldWidth() / 2,viewPort.getWorldHeight() / 2,0);

        sb = new SpriteBatch();

        board = new TmxMapLoader().load("assets/roborally/game_boardNew.tmx");

        // get the properties of the tilemap
        MapProperties mProps = board.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);
        int mapWidthInTiles = mProps.get("width", Integer.class);
        int mapHeightInTiles = mProps.get("height", Integer.class);
        int mapWidthInPixels = mapWidthInTiles * tileWidth;
        int mapHeightInPixels = mapHeightInTiles * tileHeight;

        // create a board object using the text file
        try {
            gameBoard = new Board("assets/roborally/board1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // fill the tiles array depending on the size of the board
        tiles = new Array<>();
        for (int i = 0; i < gameBoard.getWidth(); i++) {
            for (int j = 0; j < gameBoard.getHeight(); j++) {
                Rectangle tile = new Rectangle();
                tile.width = tileWidth;
                tile.height = tileHeight;
                tile.x = i * tileWidth;
                tile.y = j * tileHeight;
                tiles.add(tile);
            }
        }

        // testing of the cards, WIP
        player = new Player("test");
        Card forward = new Card(CardType.MOVE_1_TILE, 100);
        Card right = new Card(CardType.TURN_RIGHT, 100);
        ArrayList<Card> cards = new ArrayList<>();
        //cards.add(forward);
        //cards.add(right);
        player.setCards(cards);

        // create the robot and link it with the player
        Pos pos = new Pos(5,5);
        Robot robot = new Robot(pos, Direction.EAST, player, gameBoard);
        player.addRobot(robot);

        // initialize the board renderer that will render the tiled map
        boardRenderer = new OrthogonalTiledMapRenderer(board,1);
        //This line decides how much the gamescreen will show in addition to the gameboard
        camera.setToOrtho(false, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        boardRenderer.setView(camera);

        // create a sprite that represents the robot
        sprite = new Sprite(new Texture("assets/roborally/robot.png"));
        sprite.setSize(tileWidth, tileHeight);
        sprite.setPosition(player.getRobot().getPos().x() * tileWidth, player.getRobot().getPos().y()* tileWidth);

        // initialize the input processor for testing purposes
        Gdx.input.setInputProcessor(stage);

        // draw the cards
        //setupCard();

        /**
         * added for testing, is supposed to be in the game loop
         */
        chooseCards(9);
        System.out.println(currentPlayerCards);
    }

    // WIP
    // trying different approaches to create game round and phase
    private void startGame() {
        int NPLAYERS = 1;
        ArrayList<IActiveElement> ActiveElements = gameBoard.getActiveElements();
        ArrayList<Flag> flags = gameBoard.getFlags();
        ArrayList<WrenchTile> wrenches = gameBoard.getWrenches();

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < NPLAYERS; i++) {
            Player player = new Player("test");
            Robot robot = new Robot(new Pos(5, 5), Direction.NORTH, player, gameBoard);
            player.addRobot(robot);
            players.add(player);

        }
        boolean gameFinished = false;
        boolean finishedExecute;


        while(!gameFinished){
            for (int i = 0; i < players.size(); i++) {
                //players program their robots

                //choosecards() updates currentPlayerCards
                chooseCards(player.getRobot().getHealth());
                players.get(i).setCards(currentPlayerCards);
                //after the players' list of cards should be what they have programmed
            }
            //here the program card should be revealed to other players
            finishedExecute=false;
            while(!finishedExecute){
                //players should be sorted by their first cards priority number
                players.sort(new Comparator<Player>() {
                    public int compare(Player player2, Player player1) {
                        return player2.getCards().get(0).getPriorityNumber()-player1.getCards().get(0).getPriorityNumber();
                    }
                });
                for(Player current : players){
                    if(current.getCards().size()!=0){
                        finishedExecute=true;
                        Card card = current.useFirstCard();
                        card.execute(current.getRobot());
                        cardDecks.addUsed(card);
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
        }
    }


    private void chooseCards(int nCards) {
        /**
         * this method updates currentPlayerCards with the cards that is selected
         */
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

                    // remove the available cards from the screen
                    int count = availableCards.size();
                    for (int i = 0; i <count; i++) {
                        stage.getActors().removeValue(buttonsAndCards.get(
                                availableCards.remove(0)), false);
                    }

                    // remove the finish button from the screen
                    stage.getActors().removeValue(finish_button, false);
                    stage.getActors().removeValue(reset_button,false);
                    for(Card card : selectedCards){
                        stage.getActors().get(stage.getActors().indexOf(
                                buttonsAndCards.get(card),false)).removeListener(buttonsAndCards.get(
                                        card).getListeners().get(0));
                    }

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

    // this method adds the sprites to card objects. WIP
    private void setupCard() {
        //Her e metoden som setter opp kortene med sprites, spritesene e midlertidige for øyeblikket, men når kver type kort får
        //sin egen sprite kan me sette den te å ta fra kort istedenfor.
        //Denne er kun for de 9 første kortene

        int x=0;
        int y=13;
        for (int i = 0; i <= cardSprite.length-1; i++) {
            //TODO   //Card temp= new Card( cards[i].getType(),i);
            cardSprite[i] = new Sprite(new Texture("assets/roborally/cards/option - 5.jpg"));
            cardSprite[i].setSize(tileWidth*2,tileHeight*2);
            cardSprite[i].setPosition(96*x,96*y);
            x=x+2;
//            if (y==3) {
//                y = 11;
//                x = 15;
//            }
//            if (i==cardSprite.length-1){
//                cardSprite[i].setPosition(96*14,96*3);
//            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if (state == State.RUN) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();
            boardRenderer.setView(camera);
            boardRenderer.render();

            sb.setProjectionMatrix(camera.combined);
            sb.begin();

            // WIP
            //for (int i = 0; i <=cardSprite.length-1 ; i++)
            //    cardSprite[i].draw(sb);

            stage.act(v);
            stage.draw();
            sprite.draw(sb);
            sb.end();
        }
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

        // reads input from user and moves the robot on the screen
        if (key == Input.Keys.LEFT)
            if (sprite.getX() > 2)
                sprite.translate(-tileWidth, 0);

        if (key == Input.Keys.RIGHT)
            if (sprite.getX() < (96 * 12) - 1)
                sprite.translate(tileWidth, 0);

        if (key == Input.Keys.UP)
            if (sprite.getY() < (96 * 12) - 1)
                sprite.translate(0, tileHeight);

        /*if (key== Input.Keys.DOWN)
            doStuff();*/

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

    // method to test if the changing the coordinates in the Robot object
    // changes the position on the screen
    public void doStuff() {
        move(EAST, player);
    }

    private void move(Direction dir, Player p) {
        p.getRobot().move(dir);
        sprite.setPosition(p.getRobot().getPos().x() * tileWidth,
                p.getRobot().getPos().y() * tileWidth);

    }
}
