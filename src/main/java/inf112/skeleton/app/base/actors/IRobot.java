package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.IBoardElement;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;

import java.util.ArrayList;

/**
 * Basic interface that describes the functionality of the Robot.
 */
public interface IRobot extends IBoardElement {
    /**
     * check if the robot has not been moved by pushers this phase
     * @return true if not pushed
     */
    boolean hasNotMoved();

    /**
     * set the moved by pushers state
     * @param moved true if it has been moved this round
     */
    void setMoved(boolean moved);

    /**
     * Get the name of the player that controls the object (if applicable)
     *
     * @return the name of the player
     */
    Player getOwner();

    /**
     * tries to move the robot one tile in a given direction,
     * regardless of where it is facing. e.g. when a robot is pushed
     * all tryToMove functions calls this function
     * @param direction direction to tryToMove robot
     *
     *
     * @param movementAction specify the animation to use. default is NORMAL
     * @return whether the robot completed the movement,
     *         false if it hit a wall (or a robot that would not be pushed)
     */
    void tryToMove(Direction direction, MovementAction movementAction);

    /**
     * moves the robot one tile in a given direction,
     * regardless of where it is facing. e.g. when a robot is pushed
     * all tryToMove functions calls this function
     * (uses standard movementAction type)
     * @param direction direction to tryToMove robot
     *
     * @return whether the robot completed the movement,
     *         false if it hit a wall (or a robot that would not be pushed)
     */
    void tryToMove(Direction direction);
    /**
     * make the robot turn 90 degrees left
     */
    void turnLeft();

    /**
     * make the robot turn 90 degrees right
     */
    void turnRight();

    /**
     * make the robot turn 180 around
     */
    void turnHalf();

    /**
     * make the robot tryToMove an amount of tiles in the direction its facing
     *
     * @param distance amount of tiles to tryToMove
     */
    void moveForward(int distance);

    void maxHealth();

    /**
     * moves the robot 1 tile backwards
     */
    void moveBackwards();

    /**
     * Damage the robot for 1 hp
     */
    void damage();

    /**
     * get the direction the robot is facing
     * @return robot's direction
     */
    Direction getDir();

    /**
     * get the health of the robot
     * @return int (normally between 0 and 10)
     */
    int getHealth();

    /**
     * update the respawn position of the robot
     * the new respawn position is set as the robots current position
     */
    void setRespawn();

    /**
     *
     * @return remaining lives
     */
    int getLives();


    /**
     * robot gains 1 health
     */
    void gainHealth();

    /**
     * save the visited flag
     */
    void addFlag(Flag flag);

    /**
     * get the list of all visited flags by this robot
     */
    ArrayList<Flag> getFlags();

    /**
     *
     * @return previous direction of robot before movement
     */
    int getOldRotation();

    /**
     *
     * @param rotation set the old direction of robot
     */

    void setOldRotation(int rotation);

    /**
     *
     * @param dir update direction of robot is facing
     */

    void setDir(Direction dir);

    /**
     *
     * @return the board robot is placed at
     */

    IBoard getBoard();

    /**
     * check is movedir as a valid move. can be blocked by an unmovable robot or a wall
     * @param moveDir dir to move
     * @return true is valid move
     */
    boolean canGo(Direction moveDir);

    /**
     * update pos or robot
     * @param pos new position
     */
    void setPos(Pos pos);

    /**
     * get the end point of the laser the robot shoots
     * @return Pos end position
     */
    Pos getLaserDestination();

    /**
     * max health of robot
     * @return max health
     */
    int getMAX_HEALTH();


    /**
     * Get the health the robot had before last move
     * @return
     */
    int getLastMoveHealth();

    /**
     * Sets Last move health, should always be set equal to current health
     * @param i current health
     */
    void setLastMoveHealth(int i);

    /**
     * Get number of visited flags before last move
     * @return
     */
    int getLastNumFlags();

    /**
     * Sets last number of flags, should always be set equal to current flags
     * @param i number of flags
     */
    void setLastNumFlags(int i);
}
