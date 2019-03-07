package inf112.skeleton.app.base.board;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

import java.util.List;

/**
 * Generic interface for a basic game board
 *
 */
public interface IBoard {

    /**
     * Place a Tile at the position x,y that replaces the previous
     *
     * @param x x
     * @param y y
     * @param e Tile to place
     */
    void setTile(Pos pos, ITile e);

    /**
     * Add a tile object to the board at the position obj.x, obj.y
     *
     * @param obj object to place on board
     */
    void addTileObject(ITileObject obj);

    /**
     * Get the element from the position x,y
     *
     * @param x x
     * @param y y
     * @return tile at x,y
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
     * @param x x cor
     * @param y y cor
     * @return True if (x,y) is on the board
     */
    boolean outOfBounds(Pos pos);

    /**
     * check if a position contains a robot
     * @param x x
     * @param y x
     * @return true if contains robot
     */
    boolean containsRobot(Pos pos);

    /**
     * returns the robot in a position
     * @param x x
     * @param y y
     * @return the robot
     */
    IRobot getRobot(Pos pos);
    /**
     * check if a position contains a wall
     * @param x x
     * @param y x
     * @return the direction the wall is facing, or null if no wall
     */
    Direction getWallDir(Pos pos);

    boolean containsPit(Pos pos);
}
