package inf112.skeleton.app.roborally.screens.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.utils.Direction;
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
    private float robotSizex;
    private float robotSizey;
    private long totalDelay;

    public RobotGraphics(RoboRallyGame game){
        sequenceAction = new SequenceAction();
        this.game = game;
        this.tileWidth=game.getTiledMap().getProperties().get("tilewidth", Integer.class);
        this.tileHeight=game.getTiledMap().getProperties().get("tileheight", Integer.class);
        robotSizex = tileWidth/1.5f;
        robotSizey = tileHeight/1.5f;

        //Create list of Robot textures
        Texture texture = new Texture("assets/roborally/robot.png");
        Texture texture2 = new Texture("assets/roborally/robot2.png");
        Texture texture3 = new Texture("assets/roborally/robot3.png");
        Texture texture4 = new Texture("assets/roborally/robot4.png");
        Texture texture5 = new Texture("assets/roborally/robot5.png");
        Texture texture6 = new Texture("assets/roborally/robot6.png");
        Texture texture7 = new Texture("assets/roborally/robot7.png");
        Texture texture8 = new Texture("assets/roborally/robot8.png");
        textures.add(texture);
        textures.add(texture2);
        textures.add(texture3);
        textures.add(texture4);
        textures.add(texture5);
        textures.add(texture6);
        textures.add(texture7);
        textures.add(texture8);
    }

    public void addActionToRobot(IRobot robot, MovementAction movementAction) {
        if (robot != null) {
            System.out.println("adding action to " + robot.getOwner() + " with action "+movementAction);
            sequenceAction.setActor(robotSprites.get(robot));
            movementAction.addActionToSequence(sequenceAction, robot, game);
            robot.setOldRotation(robot.getDir().getRotationDegrees());
            //fix for syncing the fading of cards
            totalDelay += (long) movementAction.getActionTime();
            game.getCardButtons().addDelay(movementAction.getActionTime());
        }
    }

    public void addImage(Robot robot) {
        Drawable drawable= new TextureRegionDrawable(textures.get(textureCounter));
        textureCounter++;
        Image robotImage= new Image(drawable);
        robotImage.setSize(robotSizex, robotSizey);
        robotImage.setPosition(coordToPixel(robot.getPos().x()), coordToPixel(robot.getPos().y()));
        //get center of image so rotation is correct
        robotImage.setOrigin(robotImage.getWidth()/2, robotImage.getHeight()/2);
        robotImage.setRotation((robot.getDir().getRotationDegrees()));
        robotSprites.put(robot, robotImage);
        game.getStage().addActor(robotImage);
    }

    public SequenceAction getSeqAction() {
        return sequenceAction;
    }

    private int coordToPixel(int x) {
        if(x > 12) {
            throw new IllegalArgumentException("coordinate is outside of grid");
        }
        return (int) (x*tileWidth / 1.5f);
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void removeSprite(Robot robot) {
        game.getStage().getActors().removeValue(robotSprites.get(robot), false);
    }
    public long getTotalDelay() {
        return totalDelay;
    }
}
