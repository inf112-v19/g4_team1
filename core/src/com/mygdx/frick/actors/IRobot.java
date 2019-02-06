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
    void turnhalf();

    /**
     * make the robot move an amount of tiles in the direction its facing
     *
     * @param distance amount of tiles to move
     */
    void moveForward(int distance);
}
