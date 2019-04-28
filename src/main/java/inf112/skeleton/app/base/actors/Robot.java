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
    private boolean movedthisround=false;
    private int oldRotation;
    private boolean diedThisRound;

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
            //TODO: add animation for unable to move
            return;
        }
        Pos newPos = pos.getAdjacent(moveDirection);
        // robot is moving outside board/to pit
        if (board.outOfBounds(newPos) || (board.containsPit(newPos))) {
            respawn();
            return;
        }
        if (board.containsRobot(newPos)) {
            // can go here if other robot can be pushed
            IRobot otherRobot = board.getRobot(newPos);
            if(board.outOfBounds(otherRobot.getPos().getAdjacent(moveDirection))){
                //special case if other robot is pushed out of board
                otherRobot.tryToMove(moveDirection);
                this.move(newPos, movementAction);
                return;
            }
            if(otherRobot.canGo(moveDirection)){
                //both robots move at the same time
                moveAdditionalRobot( otherRobot, moveDirection);
                return;
            }
        }
        move(newPos, movementAction);
    }

    public boolean canGo(Direction moveDir){
        if (moveDir == null)
            throw new IllegalArgumentException("No direction to tryToMove in.");
        Pos newPos = pos.getAdjacent(moveDir);
        if (board.outOfBounds(newPos) && !wallIsBlocking(pos,moveDir)) {
            return true;
        }
        if (board.containsRobot(newPos)) {
            return board.getRobot(newPos).canGo(moveDir);
        }

        if (board.getWallDir(newPos) != null) {
            return !wallIsBlocking(newPos, moveDir) ;
        }


        if(board.getWallDir(pos) != null) {
            if (wallIsBlocking(pos, moveDir)) {
                return false;
            }
        }


        return true;
    }
    @Override
    public void setPos(Pos pos) {
        this.pos = pos;
    }

    private void moveAdditionalRobot(IRobot otherRobot, Direction moveDirection) {
        //this robot
        board.get(pos).removeContent(this);
        board.get(pos.getAdjacent(moveDirection)).addObject(this);
        pos = pos.getAdjacent(moveDirection);
        System.out.println(owner +" moved to new pos "+pos+" facing "+dir + " pushing other robot");
        //other robot
        board.get(otherRobot.getPos()).removeContent(otherRobot);
        board.get(otherRobot.getPos().getAdjacent(moveDirection)).addObject(otherRobot);
        otherRobot.setPos(otherRobot.getPos().getAdjacent(moveDirection));
        System.out.println(owner +" was pushed to new pos "+otherRobot.getPos()+" facing "+otherRobot.getDir());

        board.moveSeveral(this, otherRobot);
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
        System.out.println("respawn");
        diedThisRound = true;
        lives--;
        if (lives < 0) {
            //lose
        } else {
            health = MAX_HEALTH;
            respawnPos = board.getSpawn();
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
    public void moveBackwards() {
        tryToMove(dir.opposite() );

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
        Direction dir = getDir();
        Pos pos = getPos();

        Laser laser = new Laser(dir, pos.getAdjacent(dir), board);
        laser.activate();

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


}
