package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.board.boardelement.Laser;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;
import inf112.skeleton.app.roborally.screens.graphics.RobotGraphics;

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
    private boolean movedthisround=false;
    private int oldRotation;
    private boolean diedThisRound;
    private Pos laserDestination;
    private int lastMoveHealth;

    public Robot(Pos pos, Direction dir, Player owner, IBoard board) {
        this.dir = dir;
        this.owner = owner;
        this.board = board;
        this.pos = pos;
        this.respawnPos = pos;
        this.health = MAX_HEALTH;
        this.lives = 3;
        this.lastMoveHealth = MAX_HEALTH;
    }

    public boolean hasNotMoved(){
        return !movedthisround;
    }

    public void setMoved(boolean moved){
        movedthisround = moved;
        System.out.println("moved this round is now "+movedthisround);
        diedThisRound=false;
        //System.out.println("set movedthis round to "+moved + " and diedthirround to true");
    }

    @Override
    public Direction getDir() {
        return dir;
    }

    public void tryToMove(Direction moveDirection){
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
        while (board.containsRobot(newPos)){
            IRobot otherRobot = board.getRobot(newPos);
            if(!otherRobot.canGo(moveDirection)) {
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
        if(!robotsToPush.isEmpty()) {
            robotsToPush.add(this);
            moveAdditionalRobots(robotsToPush, moveDirection);
        }else {
            this.move(newPos, movementAction);
        }
    }


    public boolean canGo(Direction moveDir){
        //System.out.println("er i canGo med robot på "+pos);
        if (moveDir == null)
            throw new IllegalArgumentException("No direction to tryToMove in.");
        Pos newPos = pos.getAdjacent(moveDir);

        if(board.getWallDir(pos) != null) {
            if(wallIsBlocking(pos, moveDir)) {
                return false;
            }
        }
        if (board.outOfBounds(newPos)) {
            return true;
        }
        if (board.getWallDir(newPos) != null) {
            return !wallIsBlocking(newPos, moveDir) ;
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
        for(IRobot robot : robots){
            Pos robotPos = robot.getPos();
            board.get(robotPos).removeContent(robot);
            board.get(robotPos.getAdjacent(moveDirection)).addObject(robot);
            robot.setPos(robotPos.getAdjacent(moveDirection));
            System.out.println(robot.getOwner() +" moved to new pos "+robot.getPos()+" facing "+robot.getDir() + " with sync movement");
        }
        board.moveSeveral(robots);
    }

    private void move(Pos newPos, MovementAction movetype) {
        board.get(pos).removeContent(this);
        board.get(newPos).addObject(this);
        pos = newPos;
        System.out.println(owner +" moved to new pos "+pos+" facing "+dir+ " with movetype"+ movetype);
        board.move(this, movetype);
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
        System.out.println(getOwner()+" respawn");
        diedThisRound = true;
        lives--;
        if (lives < 0) {
            //lose
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
                System.out.println("error, found no respawn pos");
                move(pos, MovementAction.DEATH_ANIMATION);
            }
        }
    }

    private boolean isValidRespawn(Pos newPos){
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
            if(!diedThisRound)
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
        System.out.println("respawn is "+pos);
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
        this.dir=dir;
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
}
