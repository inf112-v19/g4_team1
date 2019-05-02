package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import inf112.skeleton.app.base.actors.AI;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.board.boardelement.*;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;

import inf112.skeleton.app.base.cards.CardType;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.roborally.screens.graphics.CardPhaseButtons;
import inf112.skeleton.app.roborally.screens.graphics.RobotGraphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

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
    private Board gameBoard;
    private Stage stage;
    private ArrayList<Player> players = new ArrayList<Player>();
    private CardDecks cardDecks = new CardDecks();
    private ArrayList<IActiveElement> ActiveElements;
    private ArrayList<Flag> flags;
    private ArrayList<WrenchTile> wrenches;
    private ArrayList<String> names;
    private RobotGraphics robotGraphics;
    private CardPhaseButtons cardPhaseButtons;
    private Label.LabelStyle labelStyle;
    private Group background;
    private Group foreground;
    private ArrayList<Image> cardAreaSlots = new ArrayList<>();
    private HashMap<Player, ArrayList<Image>> lives = new HashMap<>();
    private ArrayList<Player> playerPosition = new ArrayList<>();
    private ArrayList<Label> healthLabelPos = new ArrayList<>();
    private ArrayList<Label> flagLabelPos = new ArrayList<>();
    private ArrayList<Image> blockedImages = new ArrayList<>();
    private ArrayList<Image> backImages = new ArrayList<>();

    public RoboRallyGame(RoboRally roboRally, ArrayList<String> names, String mapFile) {
        board = new TmxMapLoader().load(mapFile);
        robotGraphics = new RobotGraphics(this);
        this.names = names;
        this.numPlayers = names.size();
        stage = new Stage();

        // labelstyle
        BitmapFont font = new BitmapFont();
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;

        //Rendering-groups(used if an image needs to be rendered over another image)
        this.background = new Group();
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.foreground = new Group();
        foreground.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        stage.addActor(foreground);

        sb = new SpriteBatch();
        this.roboRally = roboRally;
        camera = new OrthographicCamera();
        //FitViewport viewPort = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        ScalingViewport viewPort = new ScalingViewport(Scaling.stretch, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        viewPort.update(Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);

        cardPhaseButtons = new CardPhaseButtons(this, cardDecks);

        //create gameboard from tmx file
        gameBoard = new Board(board, this);

        ActiveElements = gameBoard.getActiveElements();
        flags = gameBoard.getFlags();
        wrenches = gameBoard.getWrenches();

        // initialize the board renderer that will render the tiled map
        boardRenderer = new OrthogonalTiledMapRenderer(board, 1);
        //This line decides how much the gamescreen will show in addition to the gameboard
        camera.setToOrtho(false, Gdx.graphics.getWidth() * 1.5f, Gdx.graphics.getHeight() * 1.5f);
        camera.update();
        boardRenderer.setView(camera);

        // initialize the input processor for testing purposes
        Gdx.input.setInputProcessor(stage);
        startGame();
    }

    private void startGame() {
        for (int i = 0; i < numPlayers; i++) {
            if (names.get(i).equals("AI")) {
                AI ai = new AI("AI " + i);
                Robot robot = new Robot(gameBoard.getSpawn(), Direction.NORTH, ai, gameBoard);
                gameBoard.addTileObject(robot);
                ai.addRobot(robot);
                players.add(ai);
                playerPosition.add(ai);
                robotGraphics.addImage(robot);
            } else {
                Player player = new Player(names.get(i));
                Robot robot = new Robot(gameBoard.getSpawn(), Direction.NORTH, player, gameBoard);
                gameBoard.addTileObject(robot);
                player.addRobot(robot);
                players.add(player);
                playerPosition.add(player);
                robotGraphics.addImage(robot);
            }
        }
        doTurn();
    }

    /**
     * start a turn after all cards have been selected
     */
    public void doTurn() {
        //check if finished
        boolean finished = true;
        for (Player player : players) {
            showBlockedSlots(player);
            if (!player.getCards().isEmpty()) {
                continue;
            }
            finished = false;
        }

        if (finished) {

            continueTurn();
        } else {
            for (Player player : players) {
                if (player.getCards().isEmpty()) {
                    System.out.println("choose cards");
                    if (player.getPowerDown() == 1) {
                        cardPhaseButtons.chooseCards(-1, player, true);

                    } else {
                        cardPhaseButtons.chooseCards(player.getRobot().getHealth()-1, player, false);
                    }

                    break;
                }
            }
        }

    }

    private void continueTurn() {

        for (Image backImage : backImages) {
            foreground.removeActor(backImage);
        }
        backImages.clear();
        //player have finished choosing cards
        boolean finishedExecute;
        while (true) {
            ArrayList<Player> currentPlayers = (ArrayList<Player>) players.clone();
            currentPlayers.sort((player2, player1) -> {
                if (!player1.getCards().isEmpty() && !player2.getCards().isEmpty())
                    return player1.getCards().get(0).getPriorityNumber()-player2.getCards().get(0).getPriorityNumber();
                else return 0;
            });
            finishedExecute = true;
            for (Player currentPlayer : currentPlayers) {
                if (currentPlayer.getCards().size() != 0) {
                    finishedExecute = false;
                    moveRobot(currentPlayer);
                    //after robot has moved, reset the moved boolean.
                    currentPlayer.getRobot().setMoved(false);

                    //check for flags on new pos
                    for (Flag flag : flags) {
                        flag.setRespawn();
                    }
                    //check if wincondition is met
                    for (Player player : players) {
                        if (player.getRobot().getFlags().size() == flags.size()) {
                            win(player);
                        }

                    }
                    //check for wrenches at new pos
                    for (WrenchTile wrench : wrenches) {
                        wrench.setRespawn();
                    }
                }
            }
            if(finishedExecute) break;
            System.out.println("_____start of activation phase. hasnotmoved is "+players.get(0).getRobot().hasNotMoved());
            //activates double speed first
            for (IActiveElement elem : ActiveElements) {
                if (elem instanceof DoubleSpeedConveyor) {
                    elem.activate();

                }
            }
            //resets all robots tryToMove boolean
            for (Player player : players) {
                player.getRobot().setMoved(false);
            }
            //activate board elements, then lasers
            for (IActiveElement elem : ActiveElements) {
                if (!(elem instanceof Laser)) {
                    elem.activate();
                }
            }
            for (IActiveElement elem : ActiveElements) {
                if (elem instanceof Laser) {
                    elem.activate();
                }
            }

        }
        cardPhaseButtons.clear();
        
        //starts next round
        Timer timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                shootRobotLasers();
                //updateUI();

                for (int i = 0; i < playerPosition.size(); i++) {
                    Robot robot = playerPosition.get(i).getRobot();
                    updateUI(playerPosition.get(i), robot.getHealth(), 0f);
                }
                for (Image blockedImage : blockedImages) {
                    foreground.removeActor(blockedImage);
                }
                blockedImages.clear();
                robotGraphics.resetDelay();
                doTurn();
            }

        };

        timer.scheduleTask(task, robotGraphics.getTotalDelay() + 0.5f);
    }

    private void win(Player player) {
        Timer timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                roboRally.setScreen(new winScreen(roboRally, player.getName()));

            }
        };

        timer.scheduleTask(task, robotGraphics.getTotalDelay());

    }

    @Override
    public void show() {
        int count = 0;
        int rowPixel = Gdx.graphics.getHeight() - 50;
        int columnPixel = 0;
        for (int i = 0; i < numPlayers; i++) {
            Texture cardArea = new Texture("assets/roborally/card_area.png");
            Image cardBox = new Image(cardArea);
            cardBox.setSize(cardBox.getWidth() / 1.5f, cardBox.getHeight() / 1.5f);

            //new column
            if (count == 1) {
                columnPixel = (int) (cardBox.getWidth() + 50);
            }
            //new row
            if (count == 2) {
                rowPixel -= cardBox.getHeight() + 50;
                count = 0;
            }

            Player player = players.get(i);
            ArrayList<Image> listLife = new ArrayList<>();

            for (int j = 0; j < player.getRobot().getLives(); j++) {
                Texture lifeTexture = new Texture("assets/roborally/one_life.png");
                Image life = new Image(lifeTexture);
                listLife.add(life);

                life.setSize(life.getWidth() / 1.5f, life.getHeight() / 1.5f);
                life.setPosition(98 * 18 / 1.5f + columnPixel + j * life.getWidth(), rowPixel + 10);
                background.addActor(life);
            }
            lives.put(player, listLife);

            Image powerDown = new Image(new Texture("assets/roborally/power_down.png"));

            powerDown.setSize(powerDown.getWidth() / 1.5f, powerDown.getHeight() / 1.5f);
            powerDown.setPosition(98 * 18 / 1.5f + columnPixel + player.getRobot().getLives() * listLife.get(0).getWidth(),
                    rowPixel + 10);
            player.setPowerButton(powerDown);

            Label flagLabel = new Label("Visited Flags: 0", labelStyle);
            flagLabel.setPosition(powerDown.getX() + 40, powerDown.getY());
            flagLabelPos.add(flagLabel);

            String name = player.getName();
            Label nameLabel = new Label(name, labelStyle);
            nameLabel.setPosition(98 * 16 / 1.5f + columnPixel, rowPixel + 15);
            cardBox.setPosition(98 * 16 / 1.5f + columnPixel, rowPixel - cardBox.getHeight());

            Label healthLabel = new Label("HP: " + player.getRobot().getHealth(), labelStyle);
            healthLabel.setPosition(98 * 16 / 1.5f + columnPixel, rowPixel);
            healthLabelPos.add(healthLabel);
            background.addActor(flagLabel);
            background.addActor(healthLabel);
            background.addActor(cardBox);
            background.addActor(nameLabel);
            background.addActor(powerDown);
            cardAreaSlots.add(cardBox);
            columnPixel = 0;
            count++;
        }
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
    }

    @Override
    public boolean keyDown(int i) {
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
        cardPhaseButtons.fadeCard(card);
        card.execute(player.getRobot());
        if(card.getType() == CardType.POWERDOWN) return;
        cardDecks.addUsed(card);
    }

    public CardPhaseButtons getCardButtons() {
        return cardPhaseButtons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public RobotGraphics getGraphics() {
        return robotGraphics;
    }

    public TiledMap getTiledMap() {
        return board;
    }

    public Stage getStage() {
        return stage;
    }

    public ArrayList<Image> getCardAreaSlots() {
        return cardAreaSlots;
    }

    public Group getForeground() {
        return foreground;
    }

    public Actor getLifeSprite(Player player) {
        if (player.getRobot().getLives() < 0) {
            return null;
        }
        Image life = lives.get(player).get(player.getRobot().getLives());
        return background.getChildren().get(background.getChildren().indexOf(life, false));
    }

    public int getPlayerPos(Player player) {
        return playerPosition.indexOf(player);
    }


    public void removePlayer(Player player, float delay) {
        players.remove(player);
        gameBoard.get(player.getRobot().getPos()).removeContent(player.getRobot());
        player.getCards().clear();
        Timer timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                Texture lifeTexture = new Texture("assets/roborally/Dead.png");
                Image dead = new Image(lifeTexture);
                float x = cardAreaSlots.get(playerPosition.indexOf(player)).getX();
                float y = cardAreaSlots.get(playerPosition.indexOf(player)).getY();
                dead.setPosition(x + 10, y);
                foreground.addActor(dead);
                robotGraphics.removeSprite(player.getRobot());
            }
        };
        timer.scheduleTask(task, delay);
        if(players.isEmpty()){
            roboRally.setScreen(new endGame(roboRally));
            dispose();
        }
    }

    public void showBlockedSlots(Player player) {
        if(player.getRobot().getHealth() > 5) {
            return;
        }
        for (int i = 4; i >= player.getRobot().getHealth()-1; i--) {
            Texture locked = new Texture("assets/roborally/locked.png");
            Drawable draw = new TextureRegionDrawable(locked);
            Image lockedImage =  new Image(draw);
            int x = (int) cardAreaSlots.get(playerPosition.indexOf(player)).getX()+4;
            int y = (int) cardAreaSlots.get(playerPosition.indexOf(player)).getY()+45;
            lockedImage.setSize(lockedImage.getWidth() /4.5f, lockedImage.getHeight() /4.5f);
            lockedImage.setPosition(x + i*(lockedImage.getWidth()+12), y);
            blockedImages.add(lockedImage);
            foreground.addActor(lockedImage);
        }
    }

    public void hideCards(Player player) {
        for(int i = 0; i < player.getCards().size(); i++) {
            Texture back = new Texture("assets/roborally/back.png");
            Drawable draw = new TextureRegionDrawable(back);
            Image backImage = new Image(draw);
            int x = (int) cardAreaSlots.get(playerPosition.indexOf(player)).getX() + 3;
            int y = (int) cardAreaSlots.get(playerPosition.indexOf(player)).getY() + 4;
            backImage.setSize(backImage.getWidth() / 1.5f, backImage.getHeight() / 1.5f);
            backImage.setPosition(x + i*(backImage.getWidth()+7), y);
            backImages.add(backImage);
            foreground.addActor(backImage);
        }
    }

    public void shootRobotLasers() {
        for (Player player : players) {
            Robot robot = player.getRobot();
            Image laser;

            //vertical laser
            if (player.getRobot().getDir() == Direction.SOUTH || player.getRobot().getDir() == Direction.NORTH) {
                Texture vLaser = new Texture("assets/roborally/laser-vertical.png");
                laser = new Image(vLaser);
            }
            //horizontal laser
            else {
                Texture hLaser = new Texture("assets/roborally/laser-horizontal.png");
                laser = new Image(hLaser);
            }
            laser.setSize(robotGraphics.getRobotSizex(), robotGraphics.getRobotSizey());
            robot.laser();
            Pos laserDest = robot.getLaserDestination();
            // only show visual laser if target is not on adjacent tile or the robots pos
            if (!gameBoard.outOfBounds(robot.getPos().getAdjacent(robot.getDir()))) {
                if (!robot.getPos().equals(laserDest)) {
                    int x = robot.getPos().getAdjacent(robot.getDir()).x();
                    int y = robot.getPos().getAdjacent(robot.getDir()).y();
                    float newX = robotGraphics.coordToPixel(laserDest.x());
                    float newY = robotGraphics.coordToPixel(laserDest.y());

                    laser.setPosition(robotGraphics.coordToPixel(x), robotGraphics.coordToPixel(y));
                    foreground.addActor(laser);
                    laser.addAction(new SequenceAction(Actions.moveTo(newX, newY, 0.7f), Actions.fadeOut(0f)));
                }
            }
        }
    }

    public void updateUI(Player player, int health, float delay) {
        Timer timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                int i = playerPosition.indexOf(player);
                player.getPowerButton().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("assets/roborally/power_down.png"))));
                Label healthLabel = healthLabelPos.get(i);
                healthLabel.setText("HP: " + health);
                Label flagLabel = flagLabelPos.get(i);
                flagLabel.setText("Visited Flags: " + player.getRobot().getFlags().size());

            }
        };
        timer.scheduleTask(task, delay);

    }
}
