package com.tutorial.asteroids.entities.controllers;

import com.tutorial.asteroids.entities.Player;

import gamestates.PlayState;

public abstract class PlayerController {
	
	protected PlayState playState;
	protected Player player;
	
	public void addPlayState(PlayState playState){
		this.playState = playState;
		this.player = playState.getPlayer();
	}
	
	public abstract void handleInput();
}
