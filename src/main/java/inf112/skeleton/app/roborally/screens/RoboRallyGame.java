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
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.roborally.board.Board;

public class RoboRallyGame implements Screen, InputProcessor {

    private RoboRally roboRally;
    private TiledMap board;
    private OrthographicCamera camera;
    private TiledMapRenderer boardRenderer;
    private SpriteBatch sb;
    private Sprite sprite;
    private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles, mapWidthInPixels, mapHeightInPixels;
    private Board gameBoard;
    private Array<Rectangle> tiles;
    private Stage stage;
    private Skin uiSkin;
    FitViewport viewPort;
   //TODO? Player player = new Player("test");
    Card[] cards= new Card[9];
    Sprite[] cardSprite= new Sprite[9];


    public RoboRallyGame(RoboRally roboRally) {
        this.roboRally = roboRally;

        camera = new OrthographicCamera();
        viewPort = new FitViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT,camera);
        camera.position.set(viewPort.getWorldWidth()/2,viewPort.getWorldHeight()/2,0);

        sb = new SpriteBatch();

        board = new TmxMapLoader().load("assets/roborally/game_boardNew.tmx");

        MapProperties mProps = board.getProperties();
        tileWidth = mProps.get("tilewidth", Integer.class);
        tileHeight = mProps.get("tileheight", Integer.class);
        mapWidthInTiles = mProps.get("width", Integer.class);
        mapHeightInTiles = mProps.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        gameBoard = new Board(mapHeightInTiles, mapWidthInPixels, tileWidth, tileHeight);
        tiles = new Array<>();

        boardRenderer = new OrthogonalTiledMapRenderer(board,1);
        camera.setToOrtho(false, Constants.WORLD_PIXEL_WIDTH, Constants.WORLD_PIXEL_HEIGHT);
        boardRenderer.setView(camera);

        sprite = new Sprite(new Texture("assets/roborally/robot.png"));
        sprite.setSize(tileWidth, tileHeight);

        sprite.setPosition(96*11,96*11);

        Gdx.input.setInputProcessor(this);

        //Drawing the cards.
        setupCard();
    }


    //Her e metoden som setter opp kortene med sprites, spritesene e midlertidige for øyeblikket men, nå kver type kort får
    //sin egen sprite kan me sette den te å ta fra kort istedenfor.
    //Denne er kun for de 9 første kortene
    private void setupCard() {

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
        if (key== Input.Keys.LEFT)
           if (sprite.getX()>2){
            sprite.translate(-tileWidth, 0);}
        if (key== Input.Keys.RIGHT)
            if (sprite.getX()<(96*12)-1){
                sprite.translate(tileWidth, 0);}
        if (key== Input.Keys.UP)

            if (sprite.getY()<(96*12)-1){
            sprite.translate(0, tileWidth);}
        if (key== Input.Keys.DOWN)
            if (sprite.getY()>2){
            sprite.translate(0, -tileWidth);}
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
}
