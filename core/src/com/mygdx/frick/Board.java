package com.mygdx.frick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board {
public Tile[][] tiles;
public Player player;

    public Board(int height, int width) {
    tiles= new Tile[height][width];
        for (int i = 0; i <height; i++) {
            for (int j = 0; j <width ; j++) {
                tiles[i][j]= new Tile("tom");
            }
        }


    }


    public void draw(SpriteBatch batch) {

    }
}