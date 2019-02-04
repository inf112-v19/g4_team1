package com.mygdx.frick;


public class Tile {
     String type;
     boolean occupied;


    public Tile() {

    }

    public Tile(String type) {
        this.type=type;
        occupied=false;
    }
}
