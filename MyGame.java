package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;


public class MyGame extends Game {
	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short PLAYER_HEAD_BIT = 512;
	public static final short FIREBALL_BIT = 1024;

	public SpriteBatch batch;
	public GameStateManager gsm;
	public static AssetManager manager;
	private AppPreferences preferences;
	public static Music music;
	public static Music audio;
	public static Music musicLevel1;
	public static Music dead;
	private static int screenID;

	@Override
	public void create () {
		preferences = new AppPreferences();
		batch = new SpriteBatch();
		manager = new AssetManager();

		music = Gdx.audio.newMusic(Gdx.files.internal("MONTERO.mp3"));
		audio = Gdx.audio.newMusic(Gdx.files.internal("fuoco.mp3"));
		musicLevel1 = Gdx.audio.newMusic(Gdx.files.internal("Level1.mp3"));
		dead = Gdx.audio.newMusic(Gdx.files.internal("Killed.mp3"));

		music.setVolume(preferences.getMusicVolume());
		audio.setVolume(preferences.getSoundVolume());
		screenID = 0;

		manager.finishLoading();

		setScreen(new IntroScreen(this));

	}

	public static int getScreenID() {
		return screenID;
	}

	public static void setScreenID(int screenID) {
		MyGame.screenID = screenID;
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}
