package gamestates;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tutorial.asteroids.Asteroids;
import com.tutorial.asteroids.managers.GameStateManager;

public abstract class GameState {
	protected GameStateManager gsm;
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public abstract void init();
	public abstract void update(float dt);
	public abstract void draw();
	public abstract void handleInput();
	public abstract void dispose();
	
	protected void drawCenteredString(SpriteBatch sb, BitmapFont font, String string, float y) {
		float w = font.getBounds(string).width;
		font.draw(sb, string, (Asteroids.WIDTH - w) / 2, y);
	}
}
