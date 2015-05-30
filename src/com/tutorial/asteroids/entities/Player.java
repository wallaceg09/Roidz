package com.tutorial.asteroids.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.tutorial.asteroids.Asteroids;

public class Player extends SpaceObject{
	private boolean left;
	private boolean right;
	private boolean up;
	
	private float maxSpeed;
	private float acceleration;
	private float deceleration;
	
	private float deltaRadians = 4 * 3.1415f / 5; //Angle from center that two wing thingies should be. https://youtu.be/kQn2QkkNaW0?t=713
	
	public Player(){
		x = Asteroids.WIDTH/2;
		y = Asteroids.HEIGHT/2;
		
		maxSpeed = 300;
		acceleration = 200;
		deceleration = 10;
		
		shapex = new float[4];
		shapey = new float[4];
		
		radians = 3.1415f / 2.0f;
		rotationSpeed = 3.0f;
		
	}
	
	private void setShape(){
		shapex[0] = x + MathUtils.cos(radians) * 8;
		shapey[0] = y + MathUtils.sin(radians) * 8;
		
		shapex[1] = x + MathUtils.cos(radians - deltaRadians) * 8;
		shapey[1] = y + MathUtils.sin(radians - deltaRadians) * 8;
		
		shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
		shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;
		
		shapex[3] = x + MathUtils.cos(radians + deltaRadians) * 8;
		shapey[3] = y + MathUtils.sin(radians + deltaRadians) * 8;
	}
	
	public void setLeft(boolean b){ left = b; }
	public void setRight(boolean b){ right = b; }
	public void setUp(boolean b){ up = b; }
	
	public void update(float dt){
		//Turning
		if(left){
			radians += rotationSpeed * dt;
		}else if(right){
			radians -= rotationSpeed * dt;
		}
		
		//acceleration
		if(up){
			dx += MathUtils.cos(radians) * acceleration * dt;
			dy += MathUtils.sin(radians) * acceleration * dt;
		}
		
		//deceleration
		float vec = (float) Math.sqrt(dx * dx + dy * dy);
		if(vec > 0){
			dx -= (dx / vec) * deceleration * dt;
			dy -= (dy / vec) * deceleration * dt;
		}
		
		if(vec > maxSpeed){
			dx = (dx/vec) * maxSpeed;
			dy = (dx/vec) * maxSpeed;
		}
		
		//set position
		x += dx * dt;
		y += dy * dt;
		
		//set Shape
		this.setShape();
		
		//screen wrap
		wrap();
	}
	
	public void draw(ShapeRenderer sr){
		sr.setColor(1, 1, 1, 1);
		
		sr.begin(ShapeType.Line);
		
		for(int i = 0, j = shapex.length - 1;
				i < shapex.length;
				j = i++){
			sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
		}
		
		sr.end();
	}
}
