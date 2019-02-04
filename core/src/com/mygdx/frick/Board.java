package com.mygdx.frick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board implements IBoard {
    private Tile[][] board;
    int height, width;

    public Board(int height, int width) {
        board = new Tile[height][width];
        this.height = height;
        this.width = width;

    }

    public void put(Tile element, int x, int y) {
        board[x][y] = element;
    }

    public void draw(SpriteBatch batch) {
        
    }

    /*@Override
    public void move(int steps, int x, int y, Direction direction) {
        if(containsRobot(x,y)==true) {
            if (direction == "North") {
                // do something
            }
        }
    }*/

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

    /*@Override
    public boolean containsRobot(int x, int y) {
        return board[x][y].Contains();
    }*/

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Tile[][] getBoard() {
        return board;
    }
}