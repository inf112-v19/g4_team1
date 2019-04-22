package inf112.skeleton.app.roborally.screens.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RobotGraphics {

    private final int tileHeight;
    private SequenceAction sequenceAction;
    private Map<Robot, Image> robotSprites = new HashMap<>();
    private RoboRallyGame game;
    private final int tileWidth;
    private ArrayList<Texture> textures = new ArrayList<>();
    private int textureCounter = 0;


    public RobotGraphics(RoboRallyGame game){
        sequenceAction = new SequenceAction();
        this.game = game;
        this.tileWidth=game.getTiledMap().getProperties().get("tilewidth", Integer.class);
        this.tileHeight=game.getTiledMap().getProperties().get("tileheight", Integer.class);

        //Create list of Robot textures
        Texture texture = new Texture("assets/roborally/robot.png");
        Texture texture2 = new Texture("assets/roborally/robot2.png");
        Texture texture3 = new Texture("assets/roborally/robot2.png");
        textures.add(texture);
        textures.add(texture2);
        textures.add(texture3);

    }

    public void addActionToRobot(IRobot robot){
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



    public void addImage(Robot robot) {
        Drawable drawable= new TextureRegionDrawable(textures.get(textureCounter));
        textureCounter++;
        Image robotImage= new Image(drawable);
        robotImage.setSize(tileWidth / 1.5f, tileHeight / 1.5f);
        robotImage.setPosition(coordToPixel(robot.getPos().x()), coordToPixel(robot.getPos().y()));
        //get center of image so rotation is correct
        robotImage.setOrigin(robotImage.getWidth()/2, robotImage.getHeight()/2);
        robotSprites.put(robot, robotImage);
        game.getStage().addActor(robotImage);
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

    public SequenceAction getSeqAction() {
    return sequenceAction;
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
