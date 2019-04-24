package inf112.skeleton.app.base.board;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.board.boardelement.IActiveElement;
import inf112.skeleton.app.base.board.boardelement.WrenchTile;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic interface for a basic game board
 */
public interface IBoard {

    /**
     * Place a Tile at the position that replaces the previous
     *
     * @param pos position
     * @param e tile to place
     */
    void setTile(Pos pos, ITile e);

    /**
     * Add a tile object to the board
     *
     * @param obj object to place on board
     */
    void addTileObject(IBoardElement obj);

    /**
     * Get the element from the position
     *
     * @param pos position
     * @return tile at position
     */
    ITile get(Pos pos);

    /**
     * Get the size of the board
     *
     * @return size
     */
    int getSize();

    /**
     * Return the height of the board
     *
     * @return height
     */
    int getHeight();

    /**
     * Return the width of the board
     *
     * @return width
     */
    int getWidth();

    /**
     * Return the board itself
     *
     * @return game board
     */
    List<ITile> getBoard();

    /**
     * check if a position is within the boards coordinates
     *
     * @param pos position
     * @return True if position is on the board
     */
    boolean outOfBounds(Pos pos);

    /**
     * check if a position contains a robot
     *
     * @param pos position
     * @return true if contains robot
     */
    boolean containsRobot(Pos pos);

    /**
     * check if a position contains a Flag
     *
     * @param pos position
     * @return true if contains Flag
     */
    boolean containsFlag(Pos pos);

    /**
     * returns the robot in a position
     *
     * @param pos position
     * @return the robot
     */

    IRobot getRobot(Pos pos);
    /**
     * check if a position contains a wall
     *
     * @param pos position
     * @return the direction the wall is facing, or null if no wall
     */
    Direction getWallDir(Pos pos);

    /**
     * check if a position contains a pit
     *
     * @param pos position
     * @return true if position has a pit
     */
    boolean containsPit(Pos pos);

    /**
     * get a list active elements
     *
     * @return list of instances of active element
     */
    ArrayList<IActiveElement> getActiveElements();

    /**
     * get a list of flags
     *
     * @return list of flag objects
     */
    ArrayList<Flag> getFlags();

    /**
     * get a list of wrenches
     *
     * @return list of wrench objects
     */
    ArrayList<WrenchTile> getWrenches();

    /**
     * Finds a available spawn on the board. Returns first spawn with no robot currently at it,
     * or a random spawn with a robot if every spawn-point is occupied.
     * Throws exception if no spawns is found on board.
     * @return position of an available spawn on the board
     */
    Pos getSpawn();

    /**
     * moves a robot
     * @param robot
     * @param movementAction animation to use
     */
    void move(Robot robot, MovementAction movementAction);
    /**
     * moves a robot with default animation
     * @param robot

     */
    void move(Robot robot);

    RoboRallyGame getGame();
}
