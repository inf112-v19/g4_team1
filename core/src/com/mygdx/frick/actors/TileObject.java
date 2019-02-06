package com.mygdx.frick.actors;

public abstract class TileObject implements ITileObject {
    private int x = -1, y = -1;
    private String name = "Tile Object";
    private char symbol = '#';

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Integer[] getPos() {
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
