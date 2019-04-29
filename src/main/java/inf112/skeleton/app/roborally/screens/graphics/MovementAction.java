package inf112.skeleton.app.roborally.screens.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public enum MovementAction {
    NORMAL,
    DEATH_ANIMATION,
    FAST,
    POWERDOWN;


    private final float STANDARD_MOVE_DURATION = 0.5f;
    private final float SHORT_MOVE_DURATION = 0.5f;

    public void addActionToSequence(SequenceAction seq, IRobot robot, RoboRallyGame game) {
        //creates to basic movements as a base for all animations
        MoveToAction moveToAction = moveTo(coordToPixel(robot.getPos().x(), game.getGraphics().getTileWidth()), coordToPixel(robot.getPos().y(), game.getGraphics().getTileWidth()), STANDARD_MOVE_DURATION);
        RotateByAction rotateToAction = getRotateAction(robot);
        switch (this){
            case NORMAL:
                seq.addAction(parallel( rotateToAction, moveToAction));
                return;
            case DEATH_ANIMATION:
                //remove life from screen
                Actor life = game.getLifeSprite(robot.getOwner());
                Action removeLife = Actions.fadeOut(0f);
                removeLife = Actions.color(Color.BROWN);
                removeLife.setActor(life);
                seq.addAction(removeLife);
                seq.addAction(parallel(
                        rotateBy(360f, SHORT_MOVE_DURATION, Interpolation.exp5),
                        fadeOut( SHORT_MOVE_DURATION)

                ));
                if (robot.getLives() > 0) {
                    moveToAction.setDuration(0f);
                    seq.addAction(moveToAction);
                    seq.addAction(fadeIn(SHORT_MOVE_DURATION));

                    return;
                }
            case FAST:
                //rotate and tryToMove is paralell so turn conveyors look smooth
                moveToAction.setInterpolation(Interpolation.fastSlow);
                moveToAction.setDuration(SHORT_MOVE_DURATION);
                rotateToAction.setDuration(SHORT_MOVE_DURATION);
                seq.addAction(parallel(moveToAction, rotateToAction));
                return;
            case POWERDOWN:

                Action start = color(Color.BROWN, SHORT_MOVE_DURATION, Interpolation.linear);
                Action end = color(Color.valueOf("ffffffff"), SHORT_MOVE_DURATION, Interpolation.linear);
                seq.addAction(sequence(start, end));
                return;
        }
        throw new IllegalArgumentException("no movetype");
        }

    public float getActionTime() {
        switch (this){
            case NORMAL: return STANDARD_MOVE_DURATION;
            case DEATH_ANIMATION: return SHORT_MOVE_DURATION*2;
            case FAST: return SHORT_MOVE_DURATION;
            case POWERDOWN: return SHORT_MOVE_DURATION*2;
        }
        throw new IllegalStateException("no movetype");
    }

    /**
     * Translates a grid-coordinate to a pixel-coordinate.
     * @param x The grid-coordinate(row or column number)
     * @return Pixel-coordinate
     */
    private int coordToPixel(int x, int tileWidth) {
        if(x > 15) {
            throw new IllegalArgumentException("coordinate is outside of grid");
        }
        return (int) (x*tileWidth / 1.5f);
    }



    private RotateByAction getRotateAction(IRobot robot){
        int oldRot = robot.getOldRotation();
        int newRot = (robot.getDir().getRotationDegrees());
        RotateByAction rotationAction;
        //needs to rotate
        if (newRot - oldRot > 180) { //perform negative rotation
            int rotation = 360 - (newRot-oldRot);
            rotationAction = (rotateBy(-rotation, STANDARD_MOVE_DURATION));
        }
        else if (newRot - oldRot < -180) { //positive rotation
            int rotation = 360 + (newRot-oldRot);
            rotationAction =(rotateBy(rotation, STANDARD_MOVE_DURATION));
        }
        else {
            rotationAction =(rotateBy(newRot-oldRot, STANDARD_MOVE_DURATION));
        }
        return rotationAction;
    }
}
