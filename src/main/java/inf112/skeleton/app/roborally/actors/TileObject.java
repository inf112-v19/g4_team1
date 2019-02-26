package inf112.skeleton.app.roborally.actors;

import inf112.skeleton.app.roborally.board.ITileObject;

public abstract class TileObject implements ITileObject {
    protected int x = -1, y = -1;
    protected String name = "Tile Object";
    protected char symbol = '#';

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Integer[] getXY() {
        Integer[] pos = new Integer[2];
        pos[0] = x;
        pos[1] = y;
        return pos;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
