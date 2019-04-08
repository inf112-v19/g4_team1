package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.board.boardelement.*;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;

import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static inf112.skeleton.app.base.utils.Direction.EAST;

/**
 * main game screen
 */
public class RoboRallyGame implements Screen, InputProcessor, ActionListener {
    private int numPlayers;
    private SpriteBatch sb;
    private RoboRally roboRally;
    private TiledMap board;
    private OrthographicCamera camera;
    private TiledMapRenderer boardRenderer;
   // private SpriteBatch sb;
    private int tileWidth;
    private int tileHeight;
    private Board gameBoard;
    private Stage stage;
    private Map<Robot, Image> robotSprites = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>();
    private CardDecks cardDecks = new CardDecks();
    private ArrayList<IActiveElement> ActiveElements;
    private ArrayList<Flag> flags  ;
    private ArrayList<WrenchTile> wrenches;
    private ArrayList<String> names;
    ArrayList<Texture> textures = new ArrayList<>();

    private SequenceAction sequenceAction = new SequenceAction();



    private ArrayList<Card > currentPlayerCards = new ArrayList<>();
    private Player currentPlayer;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private enum State {
        PAUSE,
        RUN
    }
    private State state = State.RUN;


    //TODO? Player player = new Player("test");

    public RoboRallyGame(RoboRally roboRally, ArrayList<String> names) {
        this.names = names;
        this.numPlayers = names.size();
        stage = new Stage();
        sb= new SpriteBatch();

        //Create list of Robot textures
        Texture texture = new Texture("assets/roborally/robot.png");
        Texture texture2 = new Texture("assets/roborally/robot2.png");
        textures.add(texture);
        textures.add(texture2);


        // get the game itself from the previous screen
        this.roboRally = roboRally;
        // set the camera
        camera = new OrthographicCamera();
        //FitViewport viewPort = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        ScalingViewport viewPort = new ScalingViewport(Scaling.none, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        viewPort.update(Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);

        System.out.println(viewPort.getWorldWidth() + " width");
        System.out.println(viewPort.getWorldHeight() + " height");

        //sb = new SpriteBatch();

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
        camera.setToOrtho(false, Gdx.graphics.getWidth()*1.5f, Gdx.graphics.getHeight()*1.5f);
        camera.update();
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
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player(names.get(i));
            Robot robot = new Robot(gameBoard.getSpawn(), Direction.NORTH, player, gameBoard);
            gameBoard.addTileObject(robot);
            player.addRobot(robot);
            players.add(player);

            Drawable drawable= new TextureRegionDrawable(textures.get(i));
            Image robotImage= new Image(drawable);

            robotSprites.put(robot, robotImage);

            robotImage.setSize(tileWidth / 1.5f, tileHeight / 1.5f);
            robotImage.setPosition(coordToPixel(robot.getPos().x()), coordToPixel(robot.getPos().y()));
            stage.addActor(robotImage);
            System.out.println("finished adding robots");

        }
        doTurn();

    }

    private void doTurn() throws InterruptedException {
        //check if finished
        boolean finished=true;
        for (Player player : players){
            if(player.getCards().isEmpty()) finished=false;
        }
        if(finished) {
            continueTurn();
        }else{
            for (Player player : players) {
                if(player.getCards().isEmpty()) {
                    System.out.println("choose cards");
                    currentPlayer = player;
                    chooseCards(player.getRobot().getHealth());
                    break;
                }
            }
        }
    }

    private void continueTurn() {
        for (Player p : players){
            System.out.println(p +" has cards "+p.getCards());
        }
        //player have finished choosing cards
        boolean finishedExecute = false;
        sequenceAction = new SequenceAction();
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
            for (Player currentPlayer : players) {
                if (currentPlayer.getCards().size() != 0) {

                    System.out.println("inside execution"+currentPlayer);
                    finishedExecute = false;
                    moveRobot(currentPlayer);
                    Image roboImage = robotSprites.get(currentPlayer.getRobot());
                    //get center of image so rotation is correct
                    roboImage.setOrigin(roboImage.getWidth()/2, roboImage.getHeight()/2);
                    addActionToRobot(currentPlayer.getRobot());
                }
            }
            //activate board elements, then lasers
            for(IActiveElement elem : ActiveElements){
                if(!(elem instanceof Laser)){
                    IRobot robot = elem.activate();
                    if(robot != null) System.out.println("activates "+elem.getClass().getSimpleName()+" on "+robot.getOwner());
                    addActionToRobot(robot);
                }
            }
            for(IActiveElement elem : ActiveElements){
                if(elem instanceof Laser){
                    elem.activate();
                }
            }
        }
        //check for flags and wrenches at end of turn
        for (Player player : players){
            for (Flag flag : flags) {
                flag.setRespawn();
            }
            for (WrenchTile wrench : wrenches) {
                wrench.setRespawn();
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

    private void addActionToRobot(IRobot robot){
        if(robot != null){
            System.out.println("adding action to "+robot.getOwner());
            sequenceAction.setActor(robotSprites.get(robot));

            //if(getRotationDegrees(robot.getDir()) != robotSprites.get(currentPlayer.getRobot()).getRotation()){
                //needs to rotate
                sequenceAction.addAction(Actions.rotateTo(getRotationDegrees(robot.getDir()), 2f));
            //}
            sequenceAction.addAction(Actions.moveTo(coordToPixel(robot.getPos().x()), coordToPixel(robot.getPos().y()),1f));
        }
    }

    /**
     * this method updates currentPlayerCards with the cards that is selected
     */
    private void chooseCards(int nCards) {
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
                    //System.out.println("klicked " + card);

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
        finish_button.setPosition((96*15)-30,96*3);
        finish_button.setSize(96,96);
        stage.addActor(finish_button);
        finished.add(false);

        finish_button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("klicked finish");
                //TODO: ENDRE TILBAKE TIL 5
                //SATT TIL 1 for testing
                if(selectedCards.size() == 5) {
                    currentPlayerCards.addAll(selectedCards);
                    currentPlayer.setCards(new ArrayList<>(currentPlayerCards));
                    System.out.println("selected for "+currentPlayer+" : " + currentPlayerCards);

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

                    float n = 0;
                    for(Card card : selectedCards) {
                        stage.getActors().get(stage.getActors().indexOf(
                                buttonsAndCards.get(card),false)).addAction(new SequenceAction(
                                        Actions.delay(n), Actions.fadeOut(3f), new RemoveActorAction()));

                        n += 3f;
                        // backup code to remove listeners from buttons
//                        stage.getActors().get(stage.getActors().indexOf(
//                                buttonsAndCards.get(card),false)).removeListener(buttonsAndCards.get(
//                                card).getListeners().get(0));
                    }
                    //continue game when finished selecting cards if there are no more players

                    try {
                        doTurn();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
        System.out.println(player+" won");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        boardRenderer.setView(camera);
        boardRenderer.render();
        camera.update();


        sb.setProjectionMatrix(camera.combined);
        sb.begin();

      /*  for (Player player : players) {
            //robotSprites.get(player.getRobot()).draw(sb);
            Robot robot = player.getRobot();
            Image robotImage = robotSprites.get(robot);
            robotImage.draw(sb,1.0f);
        } */
        sb.end();
        stage.act(v);
        stage.draw();

        sequenceAction.act(v);
    }

    @Override
    public void resize(int i, int i1) {

        stage.getViewport().update(i, i1);

    }

    @Override
    public void pause() {
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        state = State.RUN;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        board.dispose();
        //sb.dispose();
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

    private int getRotationDegrees(Direction dir){
        switch(dir){
            case NORTH: return 0;
            case EAST: return 270;
            case SOUTH: return 180;
            case WEST: return 90;
        }
        throw new IllegalArgumentException();
    }


    private void moveRobot(Player player) {
        Card card = player.useFirstCard();
        card.execute(player.getRobot());
        cardDecks.addUsed(card);
    }

    /**
     * Translates a grid-coordinate to a pixel-coordinate.
     * @param x The grid-coordinate(row or column number)
     * @return Pixel-coordinate
     */
    private int coordToPixel(int x) {
        if(x == 0) {
            return x;
        }
        if(x > 12) {
            throw new IllegalArgumentException("coordinate is outside of grid");
        }
        int pixel = (int) (x*tileWidth / 1.5f);
        return pixel;
    }

  
}
