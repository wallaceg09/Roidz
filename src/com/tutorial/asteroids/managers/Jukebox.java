package com.tutorial.asteroids.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Jukebox {
	private static HashMap<String, Sound> sounds;
	
	static{
		sounds = new HashMap<String, Sound>();
	}
	
	public static void load(String path, String name){//TODO: Exception handling
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(name, sound);
	}
	
	public static void play(String name){//TODO: Exception handling
		sounds.get(name).play();
	}
	
	public static void loop(String name){//TODO: Exception handling
		sounds.get(name).loop(1);
	}
	
	public static void stop(String name){//TODO: Exception handling
		sounds.get(name).stop();
	}
	
	public static void stopAll(){//TODO: Exception handling
		for(Sound s : sounds.values()){
			s.stop();
		}
	}
}
