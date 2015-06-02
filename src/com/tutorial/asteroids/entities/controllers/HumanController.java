package com.tutorial.asteroids.entities.controllers;

import com.tutorial.asteroids.entities.Player;
import com.tutorial.asteroids.managers.GameKeys;

public class HumanController extends PlayerController {
	
	private Player player;
	
	public HumanController(Player player) {
		this.player = player;
	}

	@Override
	public void handleInput() {
	
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		if(GameKeys.isPressed(GameKeys.SPACE)){
			player.shoot();
		}			
	}

}
