package com.mygdx.frick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board implements IBoard {
    public Tile[][] board;

    public Board(int height, int width) {
        board = new Tile[height][width];

    }

    public void put(Tile element, int x, int y) {
        board[x][y] = element;
    }

    public void draw(SpriteBatch batch) {
        
    }

    @Override
    public void move(int steps) {

    }

    @Override
    public boolean legalMove(int x, int y) {

        return false;
    }

    @Override
    public boolean containsRobot(int x, int y) {
        return false;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public Tile[][] getBoard() {
        return board;
    }
}