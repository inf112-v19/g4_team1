package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Robot extends TileObject implements IRobot {
    private Pos pos;
    private Direction dir;
    private Player owner;
    private IBoard board;
    private int health;

    public Robot(Pos pos, Direction dir, Player owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.pos = pos;
        this.health = 10;
    }

    public Direction getDir() {
        return dir;
    }

    public boolean move(Direction moveDirection){
        if (moveDirection == null)
            throw new IllegalArgumentException("no direction to move in");

        Pos newPos = pos.getAdjacent(moveDirection);
        System.out.println("newPos " +newPos);

        if (board.outOfBounds(newPos) || (board.containsPit(newPos))) {
            //TODO:
            //robot is moving outside board/to pit
            throw new UnsupportedOperationException("not implemented");
        } else {

            if(board.containsRobot(newPos)){
                //robot has to push the other robot
                IRobot otherRobot = board.getRobot(newPos);
                boolean completedMove = otherRobot.move(moveDirection);
                if(completedMove){
                    //path is clear now we try again
                    return move(moveDirection);
                }
                else{
                    //the robot on the tile couldn't move, so this robot cant move either
                    return false;
                }
            }

            //has to check for wall in this and next tile
            if(board.getWallDir(newPos) != null){
                if (wallIsBlocking(newPos, moveDirection)){
                    return false;
                }
            }
            if(board.getWallDir(pos) != null) {
                if (wallIsBlocking(pos, moveDirection)) {
                    return false;
                }
            }
            //robot is free to move to new position
            board.get(pos).removeContent(this);
            board.get(newPos).addObject(this);
            pos=newPos;
            return true;
        }
    }

    private boolean wallIsBlocking(Pos wallPos, Direction moveDirection){
        Direction walldir = board.getWallDir(wallPos);
        if (wallPos.equals(pos)){
            //the wall is on the same tile. blocks if direction of wall is same as the movement
            if(moveDirection == walldir){
                return true;
            }
            return false;
        }else{
            //the wall is on the next tile. blocks movement if the directions are opposite
            if (moveDirection.opposite() == walldir){
                return true;
            }
            return false;
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
        /*
        calls moveForward n times so it doesn't jump walls or robots
         */
        for(int i = 0; i<distance; i++){
            moveForward();
        }
    }

    @Override
    public void moveBackwards() {
        System.out.println(dir.opposite());
        move(dir.opposite());
    }

    private void moveForward() {
        move(dir);
    }

    @Override
    public Pos getPos() {
        return pos;
    }
}
