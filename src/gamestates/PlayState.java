package gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tutorial.asteroids.Asteroids;
import com.tutorial.asteroids.entities.Asteroid;
import com.tutorial.asteroids.entities.Bullet;
import com.tutorial.asteroids.entities.Particle;
import com.tutorial.asteroids.entities.Player;
import com.tutorial.asteroids.managers.GameKeys;
import com.tutorial.asteroids.managers.GameStateManager;
import com.tutorial.asteroids.managers.Jukebox;
import com.tutorial.asteroids.managers.Save;

public class PlayState extends GameState{
	
	private static final int MEDIUM_ROIDS_FROM_LARGE = 2;
	private static final int SMALL_ROIDS_FROM_MEDIUM = 4;
	
	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private BitmapFont font;
	
	private Player hudPlayer;
	private Player player;
	
	private ArrayList<Bullet> bullets;
	private ArrayList<Asteroid> asteroids;
	
	private ArrayList<Particle> particles;
	
	private int level;
	private int totalAsteroids;
	private int numAsteroidsLeft;
	
	private float maxDelay;
	private float minDelay;
	private float currentDelay;
	private float bgTimer;
	private boolean playLowPulse;

	public  PlayState(GameStateManager gsm) {
		super(gsm);
		
	}
	
	@Override
	public void init() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		
		//set font
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
		font = gen.generateFont(20);
		
		bullets = new ArrayList<Bullet>();
		
		player = new Player(bullets);
		
		asteroids = new ArrayList<Asteroid>();
		
		particles = new ArrayList<Particle>();
		
		level = 1;
		
		spawnAsteroids();
		
		hudPlayer = new Player(null);
		
		//setup background music
		maxDelay = 1;
		minDelay = 0.25f;
		currentDelay = maxDelay;
		bgTimer = maxDelay;
		playLowPulse = true;
	}
	
	private void createParticles(float x, float y){
		for(int i = 0; i < 6; ++i){//TODO: make "6" a parameterized value
			particles.add(new Particle(x, y));
		}
	}
	
	private void splitAsteroid(Asteroid asteroid){
		createParticles(asteroid.getx(), asteroid.gety());
		--numAsteroidsLeft;
		currentDelay = ((maxDelay - minDelay) * numAsteroidsLeft / totalAsteroids) + minDelay;
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
		
		currentDelay = maxDelay;
		
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
		
		//next level
		if(asteroids.size() == 0){
			++level;
			spawnAsteroids();
		}
		
		player.update(dt);
		
		if(player.isDead()){
			if(player.getLives() == 0){
				Jukebox.stopAll();
				Save.gd.setTentativeScore(player.getScore());
				gsm.setState(GameStateManager.GAMEOVER);
				return;
			}
			player.reset();
			player.loseLife();
			return;
		}
		
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
		
		//update particles
		for(int i = 0; i < particles.size(); ++i){
			particles.get(i).update( dt );
			if(particles.get(i).shouldRemove()){
				particles.remove(i--);
			}
		}
		
		//check collisions
		checkCollisions();
		
		//play background music
		bgTimer += dt;
		if(!player.isHit() && bgTimer >= currentDelay){
			if(playLowPulse){
				Jukebox.play("pulselow");
			}
			else{
				Jukebox.play("pulsehigh");
			}
			playLowPulse = !playLowPulse;
			bgTimer = 0;
		}
	}
	
	public void checkCollisions(){
		//player-asteroid collision
		if(!player.isHit()){
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
					player.incrementScore(a.getScore());
					Jukebox.play("explode");//TODO: Move to splitAsteroid?
					break;
				}
			}
		}
	}

	@Override
	public void draw() {
		
		sb.setProjectionMatrix(Asteroids.camera.combined);
		sr.setProjectionMatrix(Asteroids.camera.combined);		
		
		player.draw(sr);
		
		for(Bullet bullet : bullets){
			bullet.draw(sr);
		}
		
		for(Asteroid roid : asteroids){
			roid.draw(sr);
		}
		
		for(Particle part : particles){
			part.draw(sr);
		}
		
		//draw score
		sb.setColor(Color.WHITE);
		
		sb.begin();
		font.draw(sb, Long.toString(player.getScore()), 40, 390);
		sb.end();
		
		//draw lives
		for(int i = 0; i < player.getLives(); ++i){
			hudPlayer.setPosition(40 + 15 * i, 360);
			hudPlayer.draw(sr);			
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
