package com.tutorial.asteroids.managers;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.tutorial.asteroids.Asteroids;
import com.tutorial.asteroids.entities.controllers.RandomController;

import gamestates.GameOverState;
import gamestates.GameState;
import gamestates.HighScoreState;
import gamestates.MenuState;
import gamestates.PlayState;

public class GameStateManager {

	private GameState gameState;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int HIGHSCORES = 2;
	public static final int GAMEOVER = 3;
	public static final int RANDOM_AI = 4;

	public static final int INITIAL_STATE = PLAY;
	
	public GameStateManager(){
		if(Asteroids.USE_AI){
			
		}
		setState(MENU);
	}
	
	public void setState(int state){
		if(gameState != null) gameState.dispose();
		
		if(state == MENU){
			gameState = new MenuState(this);
		}else if(state == PLAY){
			gameState = new PlayState(this);
		}
		else if(state == HIGHSCORES){
			gameState = new HighScoreState(this);
		}
		else if(state == GAMEOVER){
			gameState = new GameOverState(this);
		}else if(state == RANDOM_AI){
			gameState = new PlayState(this, new RandomController(player))
		}
	}
	
	public void update(float dt){
		gameState.update(dt);
	}
	
	public void draw(){
		gameState.draw();
	}
}
