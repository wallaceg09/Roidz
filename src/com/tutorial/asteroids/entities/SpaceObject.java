package com.tutorial.asteroids.entities;

import com.tutorial.asteroids.Asteroids;

public class SpaceObject {

	protected float x;
	protected float y;
	
	protected float dx;
	protected float dy;
	
	protected float radians;
	
	protected float speed;
	protected float rotationSpeed;
	
	protected int width;
	protected int height;
	
	protected float[] shapex;
	protected float[] shapey;
	
	protected void wrap(){
		//Horizontal wrapping
		if(x < 0) x = Asteroids.WIDTH;
		else if(x > Asteroids.WIDTH) x = 0;
		
		//Vertical wrapping
		if(y < 0) y = Asteroids.HEIGHT;
		else if(y > Asteroids.HEIGHT) y = 0;
	}
	
}
