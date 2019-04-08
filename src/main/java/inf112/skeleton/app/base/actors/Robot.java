package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.board.boardelement.Laser;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

import java.util.ArrayList;

public class Robot implements IRobot {
    private Pos pos;
    private Direction dir;
    private Player owner;
    private IBoard board;
    private final int MAX_HEALTH = 10;
    private int health;
    private int lives;
    private Pos respawnPos;
    private ArrayList<Flag> visitedFlags = new ArrayList<Flag>();
    private boolean movedthisround=false;

    public Robot(Pos pos, Direction dir, Player owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.pos = pos;
        this.respawnPos = pos;
        this.health = MAX_HEALTH;
        this.lives = 3;
    }

    public boolean hasNotMoved(){
        return !movedthisround;
    }

    public void setMoved(boolean moved){
         movedthisround = moved;
    }

    public Pos getPos() {
        return pos;
    }

    @Override
    public Direction getDir() {
        return dir;
    }

    @Override
    public boolean move(Direction moveDirection) {
        if (moveDirection == null)
            throw new IllegalArgumentException("No direction to move in.");

        Pos newPos = pos.getAdjacent(moveDirection);
        //System.out.println("newPos " + newPos); // for testing purposes

        // robot is moving outside board/to pit
        if (board.outOfBounds(newPos) || (board.containsPit(newPos))) {
            respawn();
            return true;
        }

        else {
            if (board.containsRobot(newPos)) {
                //System.out.println("fant robot"); // for testing purposes
                // robot has to push the other robot
                IRobot otherRobot = board.getRobot(newPos);
                boolean completedMove = otherRobot.move(moveDirection);
                //temp
                if (completedMove) {
                    // path is clear now we try again
                    //for testing
                    return move(moveDirection);
                    //return false;
                } else {
                    // the robot on the tile couldn't move, so this robot cant move either
                    return false;
                }
            }

            // robot has to check for wall in this and next tile
            if (board.getWallDir(newPos) != null) {
                if (wallIsBlocking(newPos, moveDirection)) {
                    return false;
                }
            }

            if(board.getWallDir(pos) != null) {
                if (wallIsBlocking(pos, moveDirection)) {
                    return false;
                }
            }

            // robot is free to move to new position
            board.get(pos).removeContent(this);
            board.get(newPos).addObject(this);
            pos = newPos;
            System.out.println(owner +" moved to new pos "+pos+" facing "+dir);
            return true;
        }
    }

    private boolean wallIsBlocking(Pos wallPos, Direction moveDirection) {
        Direction walldir = board.getWallDir(wallPos);

        if (wallPos.equals(pos)) {
            // the wall is on the same tile
            // blocks the robot if direction of wall is same as the movement
            return moveDirection == walldir;
        }

        else {
            // the wall is on the next tile
            // blocks the robot if the directions are opposite
            return moveDirection.opposite() == walldir;
        }
    }

    @Override
    public void damage() {
        this.health--;
        if (health <= 0) {
            respawn();
        }
    }

    private void respawn() {
        System.out.println("respawn");
        lives--;
        if (lives >= 0) {

            board.get(pos).removeContent(this);

        }
        board.get(pos).removeContent(this);
        board.get(respawnPos).addObject(this);
        pos = respawnPos;
        health = MAX_HEALTH;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void turnLeft() {
        dir = dir.left();
    }

    @Override
    public void turnRight() {
        dir = dir.right();
    }

    @Override
    public void turnHalf() {
        dir = dir.opposite();
    }

    @Override
    public void moveForward(int distance) {
        // calls moveForward n times so it doesn't jump walls or robots
        for (int i = 0; i < distance; i++) {
            moveForward();
        }
    }

    @Override
    public void gainHealth() {
        this.health++;
    }

    @Override
    public void moveBackwards() {
        move(dir.opposite());
    }

    private void moveForward() {
        move(dir);
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setRespawn() {
        respawnPos = getPos();
    }

    @Override
    public void addFlag(Flag flag) {
        if (!visitedFlags.contains(flag)) {
            visitedFlags.add(flag);
        }
    }

    @Override
    public ArrayList<Flag> getFlags() {
        return visitedFlags;
    }




    public void laser() {
        Direction dir = getDir();
        Pos pos = getPos();

        Laser laser = new Laser(dir, pos.getAdjacent(dir), board);
        laser.activate();

    }

}
