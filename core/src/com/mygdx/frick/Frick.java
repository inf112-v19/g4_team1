package com.mygdx.frick;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Frick extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Board board;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
	//	img = new Texture("badlogic.jpg");
		board= new Board( 5,5);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
	//	batch.draw(img, 0, 0);
		board.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	//	img.dispose();
	}
}
