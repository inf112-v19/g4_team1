package inf112.skeleton.app.actors;

import inf112.skeleton.app.board.IBoard;
import inf112.skeleton.app.utils.Direction;

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

    public boolean move(Direction dir){
        int newX=x;
        int newY=y;
        switch (dir){
            case EAST: newX++; break;
            case WEST: newX--; break;
            case NORTH: newY++; break;
            case SOUTH: newY--; break;
        }
        //TODO::
        //check if there is a wall
        if(board.isValidPos(newX, newY)) {
            if(board.containsRobot(newX, newY)){
                //robot has to push the other robot
                IRobot otherRobot = board.getRobot(newX, newY);
                boolean completedMove = otherRobot.move(dir);
                if(completedMove){
                    //path is clear now we try again
                    return move(dir);
                }
                else{
                    //the robot on the tile couldnt move, so this robot cant move either
                    return false;
                }
            }else if(board.hasWall(x, y)){

            }
            else{
                //robot is free to move to new position
                board.get(x, y).removeContent(this);
                board.get(newX, newY).addObject(this);
                x = newX;
                y = newY;
                return true;
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
