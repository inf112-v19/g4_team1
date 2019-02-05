package com.mygdx.frick;

import java.util.ArrayList;
import java.util.List;

public class Tile implements ITile {
    private List<ITileObject> content;
    private int x, y;

    public Tile(int x, int y) {
        content = new ArrayList<ITileObject>();
        this.x = x;
        this.y = y;
    }

    @Override
    public void addObject(ITileObject object) {
        content.add(object);
    }

    @Override
    public Boolean contains(ITileObject object) {
        return content.contains(object);
    }

    @Override
    public List<ITileObject> getContent() {
        return content;
    }

    @Override
    public void removeContent(ITileObject object) {
        if (this.contains(object))
            content.remove(object);
    }

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
}
