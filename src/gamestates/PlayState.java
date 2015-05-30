package gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tutorial.asteroids.Asteroids;
import com.tutorial.asteroids.entities.Asteroid;
import com.tutorial.asteroids.entities.Bullet;
import com.tutorial.asteroids.entities.Player;
import com.tutorial.asteroids.managers.GameKeys;
import com.tutorial.asteroids.managers.GameStateManager;

public class PlayState extends GameState{
	
	private static final int MEDIUM_ROIDS_FROM_LARGE = 2;
	private static final int SMALL_ROIDS_FROM_MEDIUM = 4;
	
	private ShapeRenderer sr;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Asteroid> asteroids;
	
	private int level;
	private int totalAsteroids;
	private int numAsteroidsLeft;

	public  PlayState(GameStateManager gsm) {
		super(gsm);
		
	}
	
	@Override
	public void init() {
		sr = new ShapeRenderer();
		
		bullets = new ArrayList<Bullet>();
		
		player = new Player(bullets);
		
		asteroids = new ArrayList<Asteroid>();
		
		level = 1;
		
		spawnAsteroids();
	}
	
	private void splitAsteroid(Asteroid asteroid){
		--numAsteroidsLeft;
		switch(asteroid.getType()){
		case Asteroid.LARGE:
			for(int i = 0; i < MEDIUM_ROIDS_FROM_LARGE; ++i){
				asteroids.add(new Asteroid(asteroid.getx(), asteroid.gety(), Asteroid.MEDIUM));
			}
			break;
		case Asteroid.MEDIUM:
			for(int i = 0; i < SMALL_ROIDS_FROM_MEDIUM; ++i){
				asteroids.add(new Asteroid(asteroid.getx(), asteroid.gety(), Asteroid.SMALL));
			}
			break;
		case Asteroid.SMALL:
			break;
		}
	}
	
	private void spawnAsteroids(){
		asteroids.clear();
		
		int numToSpawn = 4 + level - 1;
		totalAsteroids = numToSpawn * 7;
		numAsteroidsLeft = totalAsteroids;
		
		for(int i = 0; i < numToSpawn; ++i){
			float x = 0;
			float y = 0;
			float dist = 0;
			do{
				x = MathUtils.random(Asteroids.WIDTH);
				y = MathUtils.random(Asteroids.HEIGHT);
				
				float dx = x - player.getx();
				float dy = y - player.gety();
				dist = (float ) Math.sqrt(dx * dx + dy * dy);
			}while(dist < 100);
			Asteroid a = new Asteroid(x, y, Asteroid.LARGE);
			asteroids.add(a);
			System.out.println(a.toString());
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		player.update(dt);
		
		for(int i = 0; i < bullets.size(); ++i){
			Bullet bullet = bullets.get(i);
			bullet.update(dt);
			if(bullet.shouldRemove()){
				bullets.remove(i);
				--i;
			}
		}
		
		for(int i = 0; i < asteroids.size(); ++i){
			Asteroid roid = asteroids.get(i);
			roid.update(dt);
			if(roid.shouldRemove()){
				asteroids.remove(i);
				--i;
			}
		}
		
		//check collisions
		checkCollisions();
	}
	
	public void checkCollisions(){
		//player-asteroid collision
		for(int i = 0; i < asteroids.size(); ++i){
			Asteroid a = asteroids.get(i);
			if(a.intersects(player)){
				player.hit();
				asteroids.remove(i--);
				splitAsteroid(a);
				break;
			}
			//if player intersects asteroid then game over maaaaan
		}
		
		//bullet-asteroid collision
		for(int i = 0; i < bullets.size(); ++i){
			Bullet b = bullets.get(i);
			for(int j = 0; j < asteroids.size(); ++j){
				Asteroid a = asteroids.get(j);
				//If a contains the point b
				if(a.contains(b.getx(),b.gety())){
					bullets.remove(i--);
					asteroids.remove(j--);//TODO: Move into splitAsteroid method
					splitAsteroid(a);
					break;
				}
			}
		}
	}

	@Override
	public void draw() {
		player.draw(sr);
		
		for(Bullet bullet : bullets){
			bullet.draw(sr);
		}
		
		for(Asteroid roid : asteroids){
			roid.draw(sr);
		}
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

	@Override
	public void dispose() {
		
	}

}
