package com.mygdx.frick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard {
    private List<Tile> board;
    private int height, width;

    public Board(int height, int width) {
        board = new ArrayList<Tile>(height * width);
        this.height = height;
        this.width = width;

        for (int i = 0; i < this.getSize(); i++)
            board.add(new Tile());
    }

    public void setTile(int x, int y, Tile tile) {
        int pos = x + (getWidth() * y);
        board.set(pos, tile);
    }

    public void addTileObject(int x, int y, ITileObject obj){
        get(x, y).addObject(obj);
    }

    public Tile get(int x, int y) {
        int pos = x + (getWidth() * y);
        return board.get(pos);
    }

    public void draw(SpriteBatch batch) {
        
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public List<Tile> getBoard() {
        return board;
    }

    @Override
    public int getSize() {
        return getHeight() * getWidth();
    }
}