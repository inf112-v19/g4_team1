package com.mygdx.frick;

import java.util.ArrayList;
import java.util.List;

public class Tile implements ITile {
    private List<ITileObject> content;

    public Tile() {
        content = new ArrayList<ITileObject>();
    }

    public Tile(ITileObject object) {
        content = new ArrayList<ITileObject>();
        content.add(object);
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
}
