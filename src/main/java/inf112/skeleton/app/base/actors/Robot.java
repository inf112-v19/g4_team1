package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.boardElement.WrenchTile;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;


public class Robot extends TileObject implements IRobot {
    private Pos pos;
    private Direction dir;
    private Player owner;
    private IBoard board;
    private static final int MAX_HEALTH = 10;
    private int health;
    private int lives;
    private Pos respawnPos;

    public Robot(Pos pos, Direction dir, Player owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.pos = pos;
        this.respawnPos = pos;
        this.health=MAX_HEALTH;
        lives = 3;

    }

    public Pos getPos() {
        return pos;
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

            //robot is moving outside board/to pit
            respawn();
            return true;
        } else {

            if(board.containsRobot(newPos)){
                System.out.println("fant robot");
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
            return moveDirection == walldir;
        }else{
            //the wall is on the next tile. blocks movement if the directions are opposite
            return moveDirection.opposite() == walldir;
        }
    }

    @Override
    public void damage() {
        this.health--;
        if(health<=0){
            respawn();
        }
    }
    public void respawn(){
        lives--;
        if(lives==0){
            //TODO:
            //robot is totally dead

        }
        pos=respawnPos;
        health=MAX_HEALTH;

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

    public void gainHealth(){
        this.health++;}

    @Override
    public void moveBackwards() {
        System.out.println(dir.opposite());
        move(dir.opposite());
    }

    private void moveForward() {
        move(dir);
    }

    public int getHealth(){
        return this.health;
    }

    public void respawned(){
        respawnPos = getPos();

    }

}
