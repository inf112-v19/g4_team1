package inf112.skeleton.app.roborally.screens.graphics;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public enum MovementAction {
    NORMAL,
    TELEPORT,
    FAST;


    private final float STANDARD_MOVE_DURATION = 1f;

    public void addActionToSequence(SequenceAction seq, IRobot robot, RoboRallyGame game) {
        //creates to basic movements as a base for all animations
        MoveToAction moveToAction = moveTo(coordToPixel(robot.getPos().x(), game.getGraphics().getTileWidth()), coordToPixel(robot.getPos().y(), game.getGraphics().getTileWidth()), 2f),
        RotateByAction rotateToAction = getRotateAction(robot);
        switch (this){
            case NORMAL:
                seq.addAction(sequence( rotateToAction, moveToAction));
                return;
            case TELEPORT:
                seq.addAction(parallel(
                        rotateBy(360f, 2f, Interpolation.exp5),
                        fadeOut( 2f)
                ));
                moveToAction.setDuration(0f);
                seq.addAction (moveToAction);
                seq.addAction(fadeIn(1f));
            return;
            case FAST:
                MoveToAction movement = moveTo(coordToPixel(robot.getPos().x(), game.getGraphics().getTileWidth()), coordToPixel(robot.getPos().y(), game.getGraphics().getTileWidth()), 1f);
                movement.setInterpolation(Interpolation.bounce);
                RotateByAction rotation = getRotateAction(robot);
                rotation.setDuration(1f);
                seq.addAction(sequence(movement,rotation));
                return;
        }
        throw new IllegalArgumentException("no movetype");
        }

    public float getActionTime() {
        switch (this){
            case NORMAL: return STANDARD_MOVE_DURATION;
            case TELEPORT: return 3f;
            case FAST: return 1f;
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
