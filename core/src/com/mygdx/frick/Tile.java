package com.mygdx.frick;

import java.util.ArrayList;
import java.util.List;

public class Tile implements ITile {
    private List<TileObject> content;

    public Tile() {
        content = new ArrayList<TileObject>();
    }

    public Tile(TileObject object) {
        content = new ArrayList<TileObject>();
        content.add(object);
    }

    @Override
    public void addObject(TileObject object) {
        content.add(object);
    }

    @Override
    public Boolean contains(TileObject object) {
        return content.contains(object);
    }

    @Override
    public List<TileObject> getContent() {
        return content;
    }

    @Override
    public void removeContent(TileObject object) {
        if (this.contains(object))
            content.remove(object);
    }
}
