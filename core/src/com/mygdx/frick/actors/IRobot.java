package com.mygdx.frick.actors;

public interface IRobot {

    /**
     * Get the name of the player that controls the object (if applicable)
     *
     * @return the name of the player
     */
    String getOwner();

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
     * moves the robot one tile in a given direction, regardless of where it is facing. e.g. when a robot is pushed
     * @param direction direction to move robot
     */
    void move(Direction direction);
}
