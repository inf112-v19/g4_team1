package com.mygdx.frick;

public interface IBoard {

    void move(int steps);

    /**
     * Check if the move is in bounds...
     *
     * @param x
     * @param y
     * @return
     */
    boolean legalMove(int x, int y);

    /**
     * Check if the tile contains the robot...
     *
     * @param x
     * @param y
     * @return
     */
    boolean containsRobot(int x, int y);

    /**
     * Return the height of the board.
     *
     * @return
     */
    int getHeight();

    /**
     * Return the width of the board.
     *
     * @return
     */
    int getWidth();

    /**
     *
     *
     * @return
     */
    Tile[][] getBoard();

}
