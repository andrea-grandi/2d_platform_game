package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music enabled";
    private static final String PREF_SOUND_ENABLED = "sound enalbed";
    private static final String PREF_SOUND_VOLUME = "sound";
    private static final String PREF_NAME = "nome audio";
    private static final String PREF_LEVEL = "livello";

    protected com.badlogic.gdx.Preferences getPrefs(){
        return Gdx.app.getPreferences(PREF_NAME);
    }

    public int getLevel() {
        return getPrefs().getInteger(PREF_LEVEL, 1);
    }

    public void setLevel(int level){
        getPrefs().putInteger(PREF_LEVEL, level);
        getPrefs().flush();
    }

    public float getMusicVolume(){
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume){
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public boolean isMusicEnabled(){
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled){
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getSoundVolume(){
        return getPrefs().getFloat(PREF_SOUND_VOLUME, 0.5f);
    }

    public void setSoundVolume(float soundVolume){
        getPrefs().putFloat(PREF_SOUND_VOLUME, soundVolume);
        getPrefs().flush();
    }

    public boolean isSoundEnabled(){
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEnabled(boolean soundEnabled){
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEnabled);
        getPrefs().flush();
    }
}
