package com.tutorial.asteroids.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.stream.FileImageInputStream;

import com.badlogic.gdx.Gdx;

public class Save {
	public static GameData gd;
	private static final String saveFilename = "highscores.sav";
	
	public static void init() {
		gd = new GameData();
		gd.init();
		save();
	}
	
	public static void save() {
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFilename));
			out.writeObject(gd);
			out.close();
		}
		catch(Exception e){//TODO: Handle exceptions properly
			e.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	public static void load() {
		try{
			if(!saveFileExists()){
				init();
				return;
			}
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFilename));
			gd = (GameData) in.readObject();
			in.close();
		}
		catch(Exception e){//TODO: Handle exceptions properly
			e.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	public static boolean saveFileExists() {
		File f = new File(saveFilename);
		return f.exists();
	};
}
