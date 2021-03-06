package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.board.boardelement.Laser;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;

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
    private ArrayList<Flag> visitedFlags = new ArrayList<>();
    private boolean movedthisround = false;
    private int oldRotation;
    private boolean diedThisRound;
    private Pos laserDestination;
    private int lastMoveHealth;
    private int lastNumFlags;

    public Robot(Pos pos, Direction dir, Player owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.pos = pos;
        this.respawnPos = pos;
        this.health = MAX_HEALTH;
        this.lives = 3;
        this.lastMoveHealth = MAX_HEALTH;
        this.lastNumFlags = 0;
    }

    public boolean hasNotMoved() {
        return !movedthisround;
    }

    public void setMoved(boolean moved) {
        movedthisround = moved;
        diedThisRound = false;
    }

    @Override
    public Direction getDir() {
        return dir;
    }

    public void tryToMove(Direction moveDirection) {
        tryToMove(moveDirection, MovementAction.NORMAL);
    }

    @Override
    public void tryToMove(Direction moveDirection, MovementAction movementAction) {
        if (!canGo(moveDirection)) {
            board.move(this, MovementAction.STUCK);
            return;
        }
        Pos newPos = pos.getAdjacent(moveDirection);
        // robot is moving outside board/to pit
        if (board.outOfBounds(newPos) || (board.containsPit(newPos))) {
            respawn();
            return;
        }
        //creates a list of robots to push
        ArrayList<IRobot> robotsToPush = new ArrayList<>();
        while (board.containsRobot(newPos)) {
            IRobot otherRobot = board.getRobot(newPos);
            if (!otherRobot.canGo(moveDirection)) {
                return;
            }
            if (!board.outOfBounds(otherRobot.getPos().getAdjacent(moveDirection)) && !board.containsPit(otherRobot.getPos().getAdjacent(moveDirection))) {
                robotsToPush.add(otherRobot);
                newPos = newPos.getAdjacent(moveDirection);
            } else {
                //moves the robot out of board to respawn
                otherRobot.tryToMove(moveDirection);
                break;
            }
        }
        if (!robotsToPush.isEmpty()) {
            robotsToPush.add(this);
            moveAdditionalRobots(robotsToPush, moveDirection);
        } else {
            this.move(newPos, movementAction);
        }
    }


    public boolean canGo(Direction moveDir) {
        if (moveDir == null)
            throw new IllegalArgumentException("No direction to tryToMove in.");
        Pos newPos = pos.getAdjacent(moveDir);

        if (board.getWallDir(pos) != null) {
            if (wallIsBlocking(pos, moveDir)) {
                return false;
            }
        }
        if (board.outOfBounds(newPos)) {
            return true;
        }
        if (board.getWallDir(newPos) != null) {
            return !wallIsBlocking(newPos, moveDir);
        }
        if (board.containsRobot(newPos)) {
            return board.getRobot(newPos).canGo(moveDir);
        }
        return true;
    }

    @Override
    public void setPos(Pos pos) {
        this.pos = pos;
    }

    private void moveAdditionalRobots(ArrayList<IRobot> robots, Direction moveDirection) {
        //this robot
        for (IRobot robot : robots) {
            Pos robotPos = robot.getPos();
            board.get(robotPos).removeContent(robot);
            board.get(robotPos.getAdjacent(moveDirection)).addObject(robot);
            robot.setPos(robotPos.getAdjacent(moveDirection));
        }
        board.moveSeveral(robots);
    }

    private void move(Pos newPos, MovementAction movetype) {
        board.get(pos).removeContent(this);
        board.get(newPos).addObject(this);
        pos = newPos;
        board.move(this, movetype);
    }

    /**
     * check if a wall is blocking robots movement
     * @param wallPos pos of wall
     * @param moveDirection move dir of robot
     * @return true if blocking
     */
    private boolean wallIsBlocking(Pos wallPos, Direction moveDirection) {
        Direction walldir = board.getWallDir(wallPos);
        return wallPos.equals(pos) ? moveDirection == walldir : moveDirection.opposite() == walldir;
    }

    @Override
    public void damage() {
        this.health--;
        if (health <= 0) {
            respawn();
        }
    }

    /**
     * moves the robot to its respawn tile, or adjacent if occupied
     */
    private void respawn() {
        diedThisRound = true;
        lives--;
        if (lives < 0) {
            return;
        } else {
            health = MAX_HEALTH;
            //respawnPos = board.getSpawn();
            if (isValidRespawn(respawnPos)) {
                move(respawnPos, MovementAction.DEATH_ANIMATION);
            } else {
                //has to find new respawn
                for (Pos newpos : pos.getAllAdjacent()) {
                    if (isValidRespawn(newpos)) {
                        move(newpos, MovementAction.DEATH_ANIMATION);
                        return;
                    }
                }
                move(pos, MovementAction.DEATH_ANIMATION);
            }
        }
    }

    private boolean isValidRespawn(Pos newPos) {
        return !board.outOfBounds(newPos) && !board.containsPit(newPos) && !board.containsRobot(newPos);
    }


    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void turnLeft() {
        dir = dir.left();
        board.move(this, MovementAction.NORMAL);

    }

    @Override
    public void turnRight() {
        dir = dir.right();
        board.move(this, MovementAction.NORMAL);

    }

    @Override
    public void turnHalf() {
        dir = dir.opposite();
        board.move(this, MovementAction.NORMAL);

    }

    @Override
    public void moveForward(int distance) {
        // calls moveForward n times so it doesn't jump walls or robots
        for (int i = 0; i < distance; i++) {
            if (!diedThisRound)
                moveForward();
        }
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public void gainHealth() {
        this.health++;
    }

    @Override
    public void maxHealth() {
        health = MAX_HEALTH;
    }

    @Override
    public void moveBackwards() {
        tryToMove(dir.opposite());

    }

    public Pos getPos() {
        return pos;
    }

    private void moveForward() {
        tryToMove(dir);
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
        if (board.getWallDir(pos) != null && board.getWallDir(pos) == dir || board.outOfBounds(pos.getAdjacent(dir))) {
            laserDestination = pos;
            return;
        }
        Pos newPos = pos.getAdjacent(dir);
        if(board.getWallDir(newPos) != null && board.getWallDir(newPos).opposite() == dir) {
            laserDestination = pos;
            return;
        }

        Laser laser = new Laser(dir, newPos, board);
        laser.activate();
        laserDestination = laser.getDestination();
    }

    @Override
    public int getOldRotation() {
        return oldRotation;
    }

    @Override
    public void setOldRotation(int rot) {
        this.oldRotation = rot;
    }

    @Override
    public void setDir(Direction dir) {
        this.dir = dir;
    }

    @Override
    public IBoard getBoard() {
        return board;
    }

    @Override
    public Pos getLaserDestination() {
        return laserDestination;
    }

    @Override
    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    @Override
    public int getLastMoveHealth() {
        return lastMoveHealth;
    }

    @Override
    public void setLastMoveHealth(int i) {
        lastMoveHealth = i;
    }

    @Override
    public int getLastNumFlags() {
        return lastNumFlags;
    }

    @Override
    public void setLastNumFlags(int i) {
        lastNumFlags = i;
    }
}
