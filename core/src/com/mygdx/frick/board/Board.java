package com.mygdx.frick.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.frick.actors.IRobot;
import com.mygdx.frick.actors.ITileObject;

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
        board.set(indexFromCor(x, y), tile);
    }

    public void addTileObject(int x, int y, ITileObject obj) {
        get(x, y).addObject(obj);
    }

    public ITile get(int x, int y) {
        return board.get(indexFromCor(x, y));
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
    public boolean isValidPos(int x, int y) {
        return (x >= 0 && x < getWidth() && y >= 0 & y < getHeight());
    }

    @Override
    public int getSize() {
        return getHeight() * getWidth();
    }

    public boolean containsRobot(int x, int y){
        List<ITileObject> tileObjects =  board.get(indexFromCor(x, y)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof IRobot) {
                return true;
            }
        }
        return false;
    }

    private int indexFromCor(int x, int y){
        return x + (getWidth() * y);
    }
}