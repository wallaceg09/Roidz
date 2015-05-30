package gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tutorial.asteroids.entities.Player;
import com.tutorial.asteroids.managers.GameKeys;
import com.tutorial.asteroids.managers.GameStateManager;

public class PlayState extends GameState{
	
	private ShapeRenderer sr;
	
	private Player player;

	public  PlayState(GameStateManager gsm) {
		super(gsm);
		
	}
	
	@Override
	public void init() {
		sr = new ShapeRenderer();
		
		player = new Player();
	}

	@Override
	public void update(float dt) {
		handleInput();	
		player.update(dt);
	}

	@Override
	public void draw() {
		player.draw(sr);
	}

	@Override
	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
	}

	@Override
	public void dispose() {
		
	}

}
