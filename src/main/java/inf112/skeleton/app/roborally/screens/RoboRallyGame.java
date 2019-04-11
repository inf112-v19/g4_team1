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
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.board.boardelement.*;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;

import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.roborally.screens.graphics.CardPhaseButtons;
import inf112.skeleton.app.roborally.screens.graphics.RobotGraphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import static inf112.skeleton.app.base.utils.Direction.EAST;

/**
 * main game screen
 */
public class RoboRallyGame implements Screen, InputProcessor, ActionListener {
    private int numPlayers;
    private SpriteBatch sb;
    private RoboRally roboRally;
    private TiledMap board = new TmxMapLoader().load("assets/roborally/game_boardNew.tmx");
    private OrthographicCamera camera;
    private TiledMapRenderer boardRenderer;
    private Board gameBoard;
    private Stage stage;
    private ArrayList<Player> players = new ArrayList<>();
    private CardDecks cardDecks = new CardDecks();
    private ArrayList<IActiveElement> ActiveElements;
    private ArrayList<Flag> flags  ;
    private ArrayList<WrenchTile> wrenches;
    private ArrayList<String> names;
    private float delay = 0f;
    private RobotGraphics robotGraphics = new RobotGraphics(this);
    private CardPhaseButtons cardPhaseButtons;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private enum State {
        PAUSE,
        RUN
    }
    public RoboRallyGame(RoboRally roboRally, ArrayList<String> names) {
        this.names = names;
        this.numPlayers = names.size();
        stage = new Stage();
        sb= new SpriteBatch();
        // get the game itself from the previous screen
        this.roboRally = roboRally;
        // set the camera
        camera = new OrthographicCamera();
        //FitViewport viewPort = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        ScalingViewport viewPort = new ScalingViewport(Scaling.stretch, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        viewPort.update(Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);

        cardPhaseButtons = new CardPhaseButtons(this, cardDecks);

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

        // initialize the input processor for testing purposes
        Gdx.input.setInputProcessor(stage);

        System.out.println("Start game");
        startGame();
    }

    // set up the players before starting game
    private void startGame() {
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player(names.get(i));
            Robot robot = new Robot(gameBoard.getSpawn(), Direction.NORTH, player, gameBoard);
            gameBoard.addTileObject(robot);
            player.addRobot(robot);
            players.add(player);
            robotGraphics.addImage(robot);
            System.out.println("finished adding robots");
        }
        doTurn();
    }

    /**
     * start a turn after all cards have been selected
     */
    public void doTurn() {
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
                    cardPhaseButtons.chooseCards(player.getRobot().getHealth(), player);
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
        //sequenceAction = new SequenceAction();
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
                    robotGraphics.addActionToRobot(currentPlayer.getRobot());
                }
            }
            //activate board elements, then lasers
            for(IActiveElement elem : ActiveElements){
                if(!(elem instanceof Laser)){
                    IRobot robot = elem.activate();
                    if(robot != null) System.out.println("activates "+elem.getClass().getSimpleName()+" on "+robot.getOwner());
                    robotGraphics.addActionToRobot(robot);
                }
            }
            for(IActiveElement elem : ActiveElements){
                if(elem instanceof Laser){
                    elem.activate();
                }
            }
        }
        cardPhaseButtons.clear();

        //check for flags and wrenches at end of turn
        for (Player ignored : players){
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
        //starts next round
        doTurn();
    }



    private void win(Player player) {
        //TODO:
        ArrayList<Flag> flags = player.getRobot().getFlags();
        if(flags.size() == 3){
            System.out.println(player+" won");
        }

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
        sb.end();
        stage.act(v);
        stage.draw();

        robotGraphics.getSeqAction().act(v);
    }

    @Override
    public void resize(int i, int i1) {

        stage.getViewport().update(i, i1);

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


    private void moveRobot(Player player) {
        Card card = player.useFirstCard();
        card.execute(player.getRobot());
        cardDecks.addUsed(card);
        cardPhaseButtons.fadeCard(card);
    }

    public TiledMap getTiledMap(){
        return board;
    }

    public Stage getStage(){
        return stage;
    }
}
