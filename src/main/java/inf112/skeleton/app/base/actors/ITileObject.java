package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.utils.Pos;

/**
 * Generic interface for the object that is located on the tile.
 *
 */
public interface ITileObject {
    /**
     * return the Pos object of the object
     */
    Pos getPos();

    /**
     * Get the [x,y] array with the coordinates of the object
     *
     * @return [x,y] array with coordinates
     */
    Integer[] getXY();

    /**
     * Get the symbol associated with the object
     *
     * @return symbol of the object
     */
    char getSymbol();

    /**
     * Set the symbol that represents the object
     *
     * @param symbol new symbol to represent the object
     */
    void setSymbol(char symbol);

    /**
     * Get the String name of the object
     *
     * @return name
     */
    String getName();

    /**
     * Set the String name of the object
     *
     * @param name new name
     */
    void setName(String name);
}