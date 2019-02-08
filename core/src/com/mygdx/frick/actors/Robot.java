package com.mygdx.frick.actors;

import com.mygdx.frick.board.IBoard;
import com.mygdx.frick.utils.Direction;

public class Robot extends TileObject implements IRobot {
    private Direction dir;
    private Player owner;
    private IBoard board;

    public Robot(int x, int y, Direction dir, Player owner, IBoard board) {
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
            if(board.containsRobot(newX, newY)){
                //robot has to push the other robot
                IRobot otherRobot = board.getRobot(x, y);
                otherRobot.move(dir);
                //path is clear now we try again
                move(dir);

            }else{
                //removes from current tile
                board.get(x, y).removeContent(this);
                //adds robot to next tile
                board.get(newX, newY).addObject(this);
                x = newX;
                y = newY;
            }
        }else{
            //TODO:
            //robot is moving outside board
            throw new UnsupportedOperationException("not implemented");
        }
    }

    @Override
    public Player getOwner() {
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
