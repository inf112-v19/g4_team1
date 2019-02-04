package com.mygdx.frick;


import java.util.ArrayList;
import java.util.List;

public class Tile  {
    List<TileObject> content=new ArrayList<TileObject>();

    // boolean occupied;

    public Tile() {

    }

    public Tile( TileObject object) {

        content.add(object);
    }

    public void AddObject(TileObject object){
        content.add(object);
    }
    public Boolean Contains(TileObject object){
        return content.contains(object);

    }

    public List<TileObject> getContent() {
        return content;
    }
    public void removeContent(TileObject object){
        content.remove(object);
    }


}
