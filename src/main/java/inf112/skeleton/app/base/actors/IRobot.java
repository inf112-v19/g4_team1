package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.utils.Direction;

public interface IRobot {

    /**
     * Get the name of the player that controls the object (if applicable)
     *
     * @return the name of the player
     */
    Player getOwner();

    /**
     * moves the robot one tile in a given direction, regardless of where it is facing. e.g. when a robot is pushed
     * all move functions calls this function
     * @param direction direction to move robot
     *
     *
     * @return whether the robot completed the movement. false if it hit a wall (or a robot that would not be pushed)
     **/
    boolean move(Direction direction);

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
     * make the robot move an amount of tiles in the direction its facing
     *
     * @param distance amount of tiles to move
     */
    void moveForward(int distance);

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
     * update the respawn position of the robot. the new respawn position is set as the robots current position
     */
    void setRespawn();


}
