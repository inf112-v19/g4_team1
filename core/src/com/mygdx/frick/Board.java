package com.mygdx.frick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board {
    private Tile[][] tiles;

    public Board(int height, int width) {
        tiles = new Tile[height][width];
    }

    public void put(Tile element, int x, int y) {
        tiles[x][y] = element;
    }


    public void draw(SpriteBatch batch) {
        
    }
}