package com.mygdx.frick.actors;

import com.mygdx.frick.board.IBoard;

public class Robot extends TileObject implements IRobot {
    private Direction dir;
    private String owner;
    private IBoard board;

    public Robot(int x, int y, Direction dir, String owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.x = x;
        this.y = y;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public void turnLeft() {
        dir=dir.left();
    }

    @Override
    public void turnRight() {
        dir=dir.right();
    }

    @Override
    public void turnhalf() {
        dir=dir.left().left();
    }

    @Override
    public void moveForward(int distance) {
        for(int i = 0; i<distance; i++){
            moveForward();
        }
    }

    private void moveForward() {
        //x=x+1
    }
}
