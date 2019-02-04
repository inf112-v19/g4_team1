package com.mygdx.frick;

import java.util.List;

public interface IBoard {

    /**
     * Place an element in the position x,y
     *
     * @param x
     * @param y
     * @param e
     */
    void set(int x, int y, Tile e);

    /**
     * Get the element from the position x,y
     *
     * @param x
     * @param y
     * @return
     */
    Tile get(int x, int y);

    /**
     * Get the size of the board
     *
     * @return
     */
    int getSize();

    /**
     * Return the height of the board
     *
     * @return
     */
    int getHeight();

    /**
     * Return the width of the board
     *
     * @return
     */
    int getWidth();

    /**
     * Return the board itself
     *
     * @return
     */
    List<Tile> getBoard();
}
