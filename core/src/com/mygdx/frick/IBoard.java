package com.mygdx.frick;

import java.util.List;

/**
 * Generic interface for a basic game board
 *
 */
public interface IBoard {

    /**
     * Place an element at the position x,y
     *
     * @param x x
     * @param y y
     * @param e element
     */
    void set(int x, int y, ITile e);

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
