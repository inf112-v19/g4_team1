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
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.board.boardElement.Flag;
import inf112.skeleton.app.base.board.boardElement.IActiveElement;
import inf112.skeleton.app.base.board.boardElement.Laser;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;
import inf112.skeleton.app.base.cards.CardType;

import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import static inf112.skeleton.app.base.utils.Direction.EAST;

public class RoboRallyGame implements Screen, InputProcessor {

    private RoboRally roboRally;
    private TiledMap board;
    private OrthographicCamera camera;
    private TiledMapRenderer boardRenderer;
    private SpriteBatch sb;
    private Sprite sprite;
    private Player player;
    private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    private Board gameBoard;
    private Array<Rectangle> tiles;
    private Stage stage;
    private Skin uiSkin;
    private FitViewport viewPort;
   //TODO? Player player = new Player("test");
    private Card[] cards;
    private Sprite[] cardSprite;


    public RoboRallyGame(RoboRally roboRally) {
        cards = new Card[9];
        cardSprite= new Sprite[9];

        this.roboRally = roboRally;

        camera = new OrthographicCamera();
        viewPort = new FitViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT,camera);
        camera.position.set(viewPort.getWorldWidth() / 2,viewPort.getWorldHeight() / 2,0);

        sb = new SpriteBatch();

        board = new TmxMapLoader().load("assets/roborally/game_boardNew.tmx");

        MapProperties mProps = board.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);
        mapWidthInTiles = mProps.get("width", Integer.class);
        mapHeightInTiles = mProps.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        try {
            gameBoard = new Board("assets/roborally/board1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        player = new Player("test");
        Card forward = new Card(CardType.MOVE_1_TILE, 100);
        Card right = new Card(CardType.TURN_RIGHT, 100);
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(forward);
        cards.add(right);
        player.setCards(cards);

        Pos pos = new Pos(5,5);
        Robot robot = new Robot(pos, Direction.EAST, player, gameBoard);
        player.addRobot(robot);

        boardRenderer = new OrthogonalTiledMapRenderer(board,1);
        camera.setToOrtho(false, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        boardRenderer.setView(camera);

        sprite = new Sprite(new Texture("assets/roborally/robot.png"));
        sprite.setSize(tileWidth, tileHeight);
        sprite.setPosition(player.getRobot().getPos().x() * tileWidth, player.getRobot().getPos().y()* tileWidth);

        Gdx.input.setInputProcessor(this);

        //Drawing the cards.
        setupCard();
    }

    private void startGame(){
        int NPLAYERS = 2;
        CardDecks cardDecks = new CardDecks();
        ArrayList<IActiveElement> ActiveElements = gameBoard.getActiveElements();
        ArrayList<Flag> flags = gameBoard.getFlags();

        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < NPLAYERS; i++) {
            Robot robot = new Robot(board.getSpawn, Direction.NORTH, gameBoard);
            Player player = new Player("test");
            player.addRobot(robot);
            players.add(player);

        }
        boolean gameFinished = false;
        boolean finishedExecute;


        while(!gameFinished){
            for (int i = 0; i < players.size(); i++) {
                //players program their robots

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
                    flags.get(i).activate();
                }
                for (int i = 0; i < wrenches.size(); i++) {
                    wrenches.get(i).respawned();
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

    private void win(Player player) {
        //a player wins
    }



    private void setupCard() {
        //Her e metoden som setter opp kortene med sprites, spritesene e midlertidige for øyeblikket, men når kver type kort får
        //sin egen sprite kan me sette den te å ta fra kort istedenfor.
        //Denne er kun for de 9 første kortene

        int x=13;
        int y=11;
        for (int i = 0; i <= cardSprite.length-1; i++) {
            //TODO   //Card temp= new Card( cards[i].getType(),i);
            cardSprite[i] = new Sprite(new Texture("assets/roborally/cards/option - 5.jpg"));
            cardSprite[i].setSize(tileWidth*2,tileHeight*2);
            cardSprite[i].setPosition(96*x,96*y);
            y=y-2;
            if (y==3) {
                y = 11;
                x = 15;
            }
            if (i==cardSprite.length-1){
                cardSprite[i].setPosition(96*14,96*3);
            }
        }
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
        for (int i = 0; i <=cardSprite.length-1 ; i++) {
            cardSprite[i].draw(sb);
        }
        sprite.draw(sb);
        sb.end();
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

    public void move(Direction dir, Player p) {
        p.getRobot().move(dir);
        sprite.setPosition(p.getRobot().getPos().x() * tileWidth, p.getRobot().getPos().y()* tileWidth);

    }

}
