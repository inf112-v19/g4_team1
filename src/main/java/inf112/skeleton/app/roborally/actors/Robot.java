package inf112.skeleton.app.roborally.actors;

import inf112.skeleton.app.roborally.board.IBoard;
import inf112.skeleton.app.roborally.utils.Direction;

public class Robot extends TileObject implements IRobot {
    private Direction dir;
    private Player owner;
    private IBoard board;
    private int health;

    public Robot(int x, int y, Direction dir, Player owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.x = x;
        this.y = y;
        this.health = 10;
    }

    public Direction getDir() {
        return dir;
    }

    @Override
    public boolean move(Direction moveDirection) {
        if (moveDirection == null)
            throw new IllegalArgumentException("no direction to move in");

        int newX = x, newY = y;
        switch (moveDirection) {
            case EAST: newX++; break;
            case WEST: newX--; break;
            case NORTH: newY++; break;
            case SOUTH: newY--; break;
        }

        if (board.outOfBounds(newX, newY) || (board.containsPit(newX, newY))) {
            //TODO:
            //robot is moving outside board/to pit
            throw new UnsupportedOperationException("not implemented");
        }
        else {
            if(board.containsRobot(newX, newY)) {
                //robot has to push the other robot
                IRobot otherRobot = board.getRobot(newX, newY);
                boolean completedMove = otherRobot.move(moveDirection);
                if (completedMove) {
                    //path is clear now we try again
                    return move(moveDirection);
                }
                else {
                    //the robot on the tile couldn't move, so this robot cant move either
                    return false;
                }
            }

            //has to check for wall in this and next tile
            if (board.getWallDir(newX, newY) != null)
                if (wallIsBlocking(newX, newY, moveDirection))
                    return false;

            if(board.getWallDir(x, y) != null)
                if (wallIsBlocking(x, y, moveDirection))
                    return false;

            //robot is free to move to new position
            board.get(x, y).removeContent(this);
            board.get(newX, newY).addObject(this);
            x = newX;
            y = newY;
            return true;
        }
    }

    private boolean wallIsBlocking(int wallX, int wallY, Direction moveDirection){
        Direction walldir = board.getWallDir(wallX, wallY);
        if (wallX == this.x && wallY == this.y) {
            //the wall is on the same tile. blocks if direction of wall is same as the movement
            return moveDirection == walldir;
        }
        else {
            //the wall is on the next tile. blocks movement if the directions are opposite
            return moveDirection.opposite() == walldir;
        }
    }

    @Override
    public void damage() {
        this.health--;
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
        // calls moveForward n times so it doesn't jump walls or robots
        for(int i = 0; i < distance; i++)
            moveForward();
    }

    @Override
    public void moveBackwards() {
        move(dir.opposite());
    }

    private void moveForward() {
        move(dir);
    }
}
