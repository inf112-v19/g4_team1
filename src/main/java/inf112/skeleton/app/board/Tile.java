package com.mygdx.frick.board;

import com.mygdx.frick.actors.ITileObject;

import java.util.ArrayList;
import java.util.List;

public class Tile implements ITile {
    private List<ITileObject> content;

    public Tile() {
        content = new ArrayList<ITileObject>();
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
