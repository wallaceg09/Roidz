package gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.tutorial.asteroids.Asteroids;
import com.tutorial.asteroids.managers.GameKeys;
import com.tutorial.asteroids.managers.GameStateManager;
import com.tutorial.asteroids.managers.Save;

public class HighScoreState extends GameState{
	
	private SpriteBatch sb;
	
	private BitmapFont font;
	
	private long[] highScores;
	private String[] names;

	public HighScoreState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		sb = new SpriteBatch();
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
		font = gen.generateFont(20);
		
		Save.load();
		highScores = Save.gd.getHighScores();
		names = Save.gd.getNames();
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void draw() {
		sb.setProjectionMatrix(Asteroids.camera.combined);
		
		sb.begin();
		
		String s;
		
		s = "High Scores";
		
		drawCenteredString(sb, font, s, 300);
		
		for(int i = 0; i < highScores.length; ++i){
			s = String.format("%2d. %7d %s", i + 1, highScores[i], names[i]);
			drawCenteredString(sb, font, s, 270 - 20 * i);
		}
		
		sb.end();
		
	}

	@Override
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.ENTER) || GameKeys.isPressed(GameKeys.ESCAPE)){
			gsm.setState(GameStateManager.MENU);
		}
	}

	@Override
	public void dispose() {
		
	}

}
