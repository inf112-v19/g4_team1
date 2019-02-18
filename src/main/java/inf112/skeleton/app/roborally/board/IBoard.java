package inf112.skeleton.app.roborally.board;

import inf112.skeleton.app.actors.IRobot;
import inf112.skeleton.app.actors.ITileObject;

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
    void setTile(int x, int y, ITile e);

    /**
     * Add a tile object to the board on a position (x, y)
     *
     * @param x x
     * @param y y
     * @param obj object to place on board
     */
    void addTileObject(int x, int y, ITileObject obj);

    /**
     * Get the element from the position x,y
     *
     * @param x x
     * @param y y
     * @return tile at x,y
     */
    ITile get(int x, int y);

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
    boolean isValidPos(int x, int y);

    /**
     * check if a position contains a robot
     * @param x x
     * @param y x
     * @return true if contains robot
     */
    boolean containsRobot(int x, int y);

    /**
     * returns the robot in a position
     * @param x x
     * @param y y
     * @return the robot
     */
    IRobot getRobot(int x, int y);
}
