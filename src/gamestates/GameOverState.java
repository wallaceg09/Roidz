package gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.tutorial.asteroids.Asteroids;
import com.tutorial.asteroids.managers.GameKeys;
import com.tutorial.asteroids.managers.GameStateManager;
import com.tutorial.asteroids.managers.Save;

public class GameOverState extends GameState {
	
	private static final char[] POSSIBLE_CHARS = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private boolean newHighScore;
	private char[] newName;
	private int[] newNameIndices;
	private int currentChar;
	
	private BitmapFont gameOverFont;
	private BitmapFont font;

	public GameOverState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		
		newHighScore = Save.gd.isHighScore(Save.gd.getTentativeScore());
		if(newHighScore){
			newNameIndices = new int[] {1, 1, 1};
			updateNewName();
			currentChar = 0;
		}
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
		gameOverFont = gen.generateFont(32);
		font = gen.generateFont(20);
	}

	@Override
	public void update(float dt) {
		handleInput();		
		updateNewName();
	}

	@Override
	public void draw() {
		sb.setProjectionMatrix(Asteroids.camera.combined);
		sr.setProjectionMatrix(Asteroids.camera.combined);
		
		sb.begin();
		
		String s;
		
		s = "Game Over";
		drawCenteredString(sb, gameOverFont, s, 220);
		
		if(!newHighScore){
			sb.end();
			return;
		}
		
		s = "New high Score: " + Save.gd.getTentativeScore();
		drawCenteredString(sb, font, s, 180);
		
		for(int i = 0; i < newName.length; ++i){
			font.draw(sb,
					Character.toString(newName[i]),
					230 + 14 * i,
					120
			);
		}
		
		sb.end();
		
		sr.begin(ShapeType.Line);
		sr.line(
				230 + 14 * currentChar,
				100,
				244 + 14 * currentChar,
				100
		);
		sr.end();
	}

	@Override
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.ENTER)){
			if(newHighScore){
				Save.gd.addHighScore(Save.gd.getTentativeScore(), new String(newName));
				Save.save();
			}
			gsm.setState(GameStateManager.HIGHSCORES);
		}
		
		if(GameKeys.isPressed(GameKeys.UP)){
			incrementNameIndex();
		}
		
		if(GameKeys.isPressed(GameKeys.DOWN)){
			decrementNameIndex();
		}
		
		if(GameKeys.isPressed(GameKeys.LEFT)){
			decrementCurrentChar();
		}
		
		if(GameKeys.isPressed(GameKeys.RIGHT)){
			incrementCurrentChar();
		}
	}

	@Override
	public void dispose() {
		sb.dispose();
		sr.dispose();
		gameOverFont.dispose();
		font.dispose();
	}
	
	private void updateNewName(){
		newName = new char[newNameIndices.length];
		for(int i = 0; i < newNameIndices.length; ++i){
			newName[i] = POSSIBLE_CHARS[newNameIndices[i]];
		}
	}
	
	private void incrementNameIndex(){
		if(newNameIndices[currentChar] < POSSIBLE_CHARS.length - 1){
			++newNameIndices[currentChar];
		}
		else{
			newNameIndices[currentChar] = 0;			
		}
	}
	
	private void decrementNameIndex(){
		
		if(newNameIndices[currentChar] > 0){
			--newNameIndices[currentChar];
		}
		else{
			newNameIndices[currentChar] = POSSIBLE_CHARS.length - 1;
		}
	}
	
	private void incrementCurrentChar(){
		int max = newNameIndices.length - 1;
		if(currentChar < max){
			++currentChar;
		}
		else{
			currentChar = 0;
		}
	}
	
	private void decrementCurrentChar(){
		int max = newNameIndices.length - 1;
		if(currentChar > 0){
			--currentChar;
		}
		else{
			currentChar = max;
		}
	}

}
