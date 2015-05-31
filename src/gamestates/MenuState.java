package gamestates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.tutorial.asteroids.Asteroids;
import com.tutorial.asteroids.entities.Asteroid;
import com.tutorial.asteroids.managers.GameKeys;
import com.tutorial.asteroids.managers.GameStateManager;

public class MenuState extends GameState{

	private SpriteBatch sb;
	
	private BitmapFont titleFont;
	private BitmapFont font;
	
	private final String title = "Asteroids";
	
	private int currentItem;
	private String[] menuItems;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		sb = new SpriteBatch();
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
		
		titleFont = gen.generateFont(56);
		titleFont.setColor(Color.WHITE);
		
		font = gen.generateFont(20);
		
		menuItems = new String[] {
				"Play",
				"Highscores",
				"Quit"
		};
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void draw() {
		//sb.setProjectionMatrix(Game.cam.combined);//FIXME: Not working. Checkout his code @ part 13 to see which Game he is referencing
		
		sb.begin();
		//Draw title
		float width = titleFont.getBounds(title).width;
		
		titleFont.draw(
				sb,
				title,
				(Asteroids.WIDTH - width) / 2,
				300);
		
		//Draw menu
		for(int i = 0; i < menuItems.length; ++i){
			width = font.getBounds(menuItems[i]).width;
			if(currentItem == i){
				font.setColor(Color.RED);
			}else{
				font.setColor(Color.WHITE);
			}
			
			font.draw(
					sb, 
					menuItems[i],
					(Asteroids.WIDTH - width) / 2,
					180 - 35 * i);
		}
		
		sb.end();
	}

	@Override
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.UP)){
			if(currentItem > 0) {
				--currentItem;
			}
		}
		
		if(GameKeys.isPressed(GameKeys.DOWN)){
			if(currentItem < menuItems.length - 1) {
				++currentItem;
			}
		}
		
		if(GameKeys.isPressed(GameKeys.ENTER)){
			select();
		}
	}
	
	private void select(){
		if(currentItem == 0){//PLAY selected
			gsm.setState(GameStateManager.PLAY);
		}
		else if(currentItem == 1){//HIGHSCORES selected
			//gsm.setState(GameStateManager.HIGHSCORES);//TODO: Implement
		}
		else if(currentItem == 2){
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		
	}

}
