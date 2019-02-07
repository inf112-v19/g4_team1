package com.mygdx.frick.actors;

import com.mygdx.frick.board.IBoard;
import com.mygdx.frick.board.ITile;
import com.mygdx.frick.board.Tile;

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
    //TODO:
    //find a nice way to get the new coordinates in a given direction, eg DIRECTION.getCor(x, y, dir)

    public void move(Direction dir){
        int newX=x;
        int newY=y;
        switch (dir){
            case EAST: newX++; break;
            case WEST: newX--; break;
            case NORTH: newY++; break;
            case SOUTH: newY--; break;
        }

        if(board.isValidPos(newX, newY)) {
            if(board.get(x, y).containsRobot()) {
                //if (robot.move != Null) {
                //    this.pos = pos.getCor(x, y, dir);
                //}
            }else{
                ITile currentTile = board.get(x, y);
                ITile nextTile = board.get(newX, newY);
                currentTile.removeContent(this);
                nextTile.addObject(this);
                x = newX;
                y = newY;
            }
        }
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
    public void turnHalf() {
        dir=dir.opposite();
    }

    @Override
    public void moveForward(int distance) {
        /*
        calls moveForward n times so it doesnt jump walls or robots
         */
        for(int i = 0; i<distance; i++){
            moveForward();
        }
    }

    @Override
    public void moveBackwards() {
        move(dir.opposite());
    }

    private void moveForward() {
        move(dir);
    }
}
