package com.tutorial.asteroids.managers;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import gamestates.GameState;
import gamestates.MenuState;
import gamestates.PlayState;

public class GameStateManager {

	private GameState gameState;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;

	public static final int INITIAL_STATE = PLAY;
	
	public GameStateManager(){
		setState(MENU);
	}
	
	public void setState(int state){
		if(gameState != null) gameState.dispose();
		if(state == MENU){
			gameState = new MenuState(this);
		}else if(state == PLAY){
			gameState = new PlayState(this);
		}
	}
	
	public void update(float dt){
		gameState.update(dt);
	}
	
	public void draw(){
		gameState.draw();
	}
}
