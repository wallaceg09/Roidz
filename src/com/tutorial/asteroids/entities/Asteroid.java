package com.tutorial.asteroids.entities;

import java.util.Arrays;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends SpaceObject{
	
	private int type;
	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int LARGE = 2;
	
	private int numPoints;
	private float[] dists;

	private boolean remove;
	
	public Asteroid(float x, float y, int type){
		this.x = x;
		this.y = y;
		this.type = type;
		
		switch(type){
		case SMALL:
			numPoints = 8;
			width = height = 12;
			speed = MathUtils.random(70, 100);
			break;
		case MEDIUM:
			numPoints = 10;
			width = height = 20;
			speed = MathUtils.random(50, 60);
			break;
		case LARGE:
			numPoints = 12;
			width = height = 40;
			speed = MathUtils.random(20, 30);
			break;
		}
		
		rotationSpeed = MathUtils.random(-1, 1);//FIXME: This returns an int, so the possible values are {-1, 0, 1}. Bugger that.
		radians = MathUtils.random(2 * 3.1415f);
		
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		
		shapex = new float[numPoints];
		shapey = new float[numPoints];
		
		dists = new float[numPoints];
		
		int radius = width/2;
		
		for(int i = 0; i < numPoints; ++i){
			dists[i] = MathUtils.random(radius / 2, radius);
		}
		setShape();
		System.out.println(this.toString());
	}
	
	public void setShape(){
		float angle = 0;
		for (int i = 0; i < numPoints; ++i){
			shapex[i] = x + MathUtils.cos(angle + radians) * dists[i];
			shapey[i] = x + MathUtils.sin(angle + radians) * dists[i];
			angle += 2 * 3.1415f /numPoints;
		}
	}
	
	public int getType(){ return type;}
	public boolean shouldRemove(){ return remove; }
	
	public void update(float dt){
		x += dx * dt;
		y += dy * dt;
		
		radians += rotationSpeed * dt;
		setShape();
		
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

	@Override
	public String toString() {
		return "Asteroid [type=" + type + ", numPoints=" + numPoints
				+ ", dists=" + Arrays.toString(dists) + ", remove=" + remove
				+ ", x=" + x + ", y=" + y + ", dx=" + dx + ", dy=" + dy
				+ ", radians=" + radians + ", speed=" + speed
				+ ", rotationSpeed=" + rotationSpeed + ", width=" + width
				+ ", height=" + height + ", shapex=" + Arrays.toString(shapex)
				+ ", shapey=" + Arrays.toString(shapey) + "]";
	}	
}
