package inf112.skeleton.app.roborally.screens.graphics;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;

public enum MovementAction {
    NORMAL,
    TELEPORT;

    private final float STANDARD_MOVE_DURATION = 2f;

    public Action getAction(IRobot robot, RoboRallyGame game) {
        switch (this){
            case NORMAL:
                int oldRot = robot.getOldRotation();
                int newRot = (robot.getDir().getRotationDegrees());

                //needs to rotate
                if(oldRot != newRot) {

                    if (newRot - oldRot > 180) { //perform negative rotation
                        int rotation = 360 - (newRot-oldRot);
                        return (Actions.rotateBy(-rotation, STANDARD_MOVE_DURATION));
                    }

                    else if (newRot - oldRot < -180) { //positive rotation
                        int rotation = 360 + (newRot-oldRot);
                        return (Actions.rotateBy(rotation, STANDARD_MOVE_DURATION));
                    }

                    else {
                        return (Actions.rotateBy(newRot-oldRot, STANDARD_MOVE_DURATION));
                    }
                }

                //needs to move
                else{
                    return (Actions.moveTo(coordToPixel(robot.getPos().x(), game.getGraphics().getTileWidth()), coordToPixel(robot.getPos().y(), game.getGraphics().getTileWidth()), 2f));
                }
            case TELEPORT:

        }
        throw new IllegalArgumentException("no movetype");
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

    public float getActionTime() {
        switch (this){
            case NORMAL: return STANDARD_MOVE_DURATION;
            case TELEPORT: return STANDARD_MOVE_DURATION;
        }
        throw new IllegalStateException("no movetype");
    }
}
