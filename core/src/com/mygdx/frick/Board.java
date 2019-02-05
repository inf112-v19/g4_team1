package com.mygdx.frick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard {
    private List<ITile> board;
    private int height, width;

    public Board(int height, int width) {
        board = new ArrayList<ITile>(height * width);
        this.height = height;
        this.width = width;

        for (int i = 0; i < this.getHeight(); i++)
            for (int j = 0; j < this.getWidth(); j++)
                board.add(new Tile(i, j));
    }

    public void setTile(int x, int y, ITile tile) {
        int pos = x + (getWidth() * y);
        board.set(pos, tile);
    }

    public void addTileObject(int x, int y, ITileObject obj) {
        get(x, y).addObject(obj);
    }

    public ITile get(int x, int y) {
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
    public List<ITile> getBoard() {
        return board;
    }

    @Override
    public int getSize() {
        return getHeight() * getWidth();
    }
}