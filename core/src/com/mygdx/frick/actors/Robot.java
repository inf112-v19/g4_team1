package com.mygdx.frick.actors;

public class Robot extends TileObject implements IRobot {
    private String owner;

    public Robot(int x, int y, String owner) {
        this.owner = owner;
        this.x = x;
        this.y = y;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public void turnLeft() {

    }

    @Override
    public void turnRight() {

    }

    @Override
    public void turnhalf() {
        this.turnLeft();
        this.turnLeft();
    }

    @Override
    public void moveForward(int distance) {

    }
}
