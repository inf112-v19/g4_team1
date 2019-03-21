package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.board.boardelement.Laser;
import inf112.skeleton.app.base.board.boardelement.Wall;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

import java.util.ArrayList;

public class Robot extends TileObject implements IRobot {
    private Pos pos;
    private Direction dir;
    private Player owner;
    private IBoard board;
    private final int MAX_HEALTH = 10;
    private int health;
    private int lives;
    private Pos respawnPos;
    private ArrayList<Flag> visitedFlags = new ArrayList<Flag>();

    public Robot(Pos pos, Direction dir, Player owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.pos = pos;
        this.respawnPos = pos;
        this.health = MAX_HEALTH;
        this.lives = 3;
    }

    @Override
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
        System.out.println("newPos " + newPos); // for testing purposes

        // robot is moving outside board/to pit
        if (board.outOfBounds(newPos) || (board.containsPit(newPos))) {
            respawn();
            return true;
        } else {
            if (board.containsRobot(newPos)) {
                System.out.println("fant robot"); // for testing purposes
                // robot has to push the other robot
                IRobot otherRobot = board.getRobot(newPos);
                boolean completedMove = otherRobot.move(moveDirection);

                if (completedMove) {
                    // path is clear now we try again
                    return move(moveDirection);
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

            if (board.getWallDir(pos) != null) {
                if (wallIsBlocking(pos, moveDirection)) {
                    return false;
                }
            }

            // robot is free to move to new position
            board.get(pos).removeContent(this);
            board.get(newPos).addObject(this);
            pos = newPos;
            return true;
        }
    }

    private boolean wallIsBlocking(Pos wallPos, Direction moveDirection) {
        Direction walldir = board.getWallDir(wallPos);

        if (wallPos.equals(pos)) {
            // the wall is on the same tile
            // blocks the robot if direction of wall is same as the movement
            return moveDirection == walldir;
        } else {
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
        lives--;
        if (lives == 0) {
            //TODO:
            //robot is totally dead.. game over?

        }

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
        System.out.println(dir.opposite());
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




  public void laser( ) {

      Enum dir = this.getDir();
      Pos pos = this.getPos();
      if (dir == Direction.EAST) {
          Laser laser = new Laser(Direction.EAST, pos, 'a', (Board) board);
          laser.activate();


      }
      if (dir == Direction.WEST) {
          Laser laser = new Laser(Direction.WEST, pos, 'a', (Board) board);
          laser.activate();
      }
      if (dir == Direction.NORTH) {

          Laser laser = new Laser(Direction.NORTH, pos, 'a', (Board) board);
          laser.activate();
      }
      if (dir == Direction.SOUTH) {
          Laser laser = new Laser(Direction.SOUTH, pos, 'a', (Board) board);
          laser.activate();
      }

   /*     new Wall(Direction.EAST,1,'a',board)
        if(dir.equals(Direction.EAST)){
            Tile newTile = new Tile(getPos(y), +1);
            while(!newTile.contains(Wall || andre elementer) {
                if(newTile.contain(robot){
                    robot.hp --;
                } else {
                    fortsett i loopen
                }
            }

        }
        }
/*
        if()

        // checks for wall at the near side of the tile
        if (board.getWallDir() != null)
            if (dir == board.getWallDir(laserPos).opposite())
                return;

        // damages robot at the tile
        if (board.containsRobot(laserPos)) {
            // shoots robot
            board.getRobot(laserPos).damage();
            return;
        }

        // check if hits wall at the far side of the tile
        if (board.getWallDir(laserPos) != null)
            if (dir == board.getWallDir(laserPos))
                return;

        // checks next tile in the loop
        laserPos = laserPos.getAdjacent(dir);

        if (board.outOfBounds(laserPos)) return;*/

  }

}
