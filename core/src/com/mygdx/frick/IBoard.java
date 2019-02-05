package com.mygdx.frick;

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
}
