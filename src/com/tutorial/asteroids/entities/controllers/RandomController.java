package com.tutorial.asteroids.entities.controllers;

import java.util.Random;

import com.tutorial.asteroids.entities.Player;

public class RandomController extends PlayerController{
	
	private Player player;
	private Random rand;
	
	public RandomController(Player player) {
		this.player = player;
		this.rand = new Random();
	}
	
	@Override
	public void handleInput() {
		player.setLeft(rand.nextBoolean());
		player.setRight(rand.nextBoolean());
		player.setUp(rand.nextBoolean());
		if(rand.nextBoolean() == true){
			player.shoot();
		}
	}

}
