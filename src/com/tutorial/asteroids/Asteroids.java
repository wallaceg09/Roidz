package com.tutorial.asteroids;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tutorial.asteroids.managers.GameINputProcessor;
import com.tutorial.asteroids.managers.GameKeys;
import com.tutorial.asteroids.managers.GameStateManager;

public class Asteroids implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private GameStateManager gsm;
	
	public static int WIDTH;
	public static int HEIGHT;
	
	@Override
	public void create() {		
		float w = WIDTH = Gdx.graphics.getWidth();
		float h = HEIGHT = Gdx.graphics.getHeight();
		
		//camera = new OrthographicCamera(1, h/w);
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.translate(WIDTH/2, HEIGHT/2);
		camera.update();
		batch = new SpriteBatch();
				
		Gdx.input.setInputProcessor(new GameINputProcessor());
		
		gsm = new GameStateManager();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		
		GameKeys.update();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
