package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Options implements Screen {


    private Viewport viewport;
    public Stage stage;
    private Game game;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundONOffLabel;

    public Options (Game game) {
        this.game = game;

        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGame) game).batch);

    }

    @Override
    public void show() {
        final AppPreferences preferences = new AppPreferences();
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        titleLabel = new Label("OPTIONS", font);
        volumeMusicLabel = new Label("Music Volume", font);
        musicOnOffLabel = new Label("Music Enabled", font);
        volumeSoundLabel = new Label("Sound Volume", font);
        soundONOffLabel = new Label("Sound Enabled", font);
        Label returnLabel = new Label ("Press back to return", font);

        // creo lo slider per il volume

        final Slider musicSlider = new Slider(0.1f,1f,0.05f,false, skin);
        musicSlider.setValue(preferences.getMusicVolume());

        musicSlider.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                MyGame.music.setVolume(musicSlider.getValue());
                MyGame.musicLevel1.setVolume(musicSlider.getValue());
                preferences.setMusicVolume(musicSlider.getValue());
            }
        });

        // creo la checkbox per l'enable del volume

        final CheckBox musicCheck = new CheckBox(null, skin);
        musicCheck.setChecked(preferences.isMusicEnabled());

        musicCheck.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(musicCheck.isChecked() == false) {
                    if(MyGame.musicLevel1.isPlaying())
                        MyGame.musicLevel1.pause();
                    if(MyGame.music.isPlaying())
                        MyGame.music.stop();

                    preferences.setMusicEnabled(false);
                }
                else {
                    if(MyGame.getScreenID() == 0)
                        MyGame.music.play();
                    else if(MyGame.getScreenID() == 1)
                        MyGame.musicLevel1.play();

                    preferences.setMusicEnabled(true);
                }
            }
        });

        final Slider soundSlider = new Slider(0.1f,1f,0.05f,false, skin);
        soundSlider.setValue(preferences.getSoundVolume());

        soundSlider.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                MyGame.audio.setVolume(soundSlider.getValue());
                preferences.setSoundVolume(soundSlider.getValue());
            }
        });

        final CheckBox soundCheck = new CheckBox(null, skin);
        soundCheck.setChecked(preferences.isSoundEnabled());

        soundCheck.addListener(new ClickListener(){
           public void clicked(InputEvent event, float x, float y){
               if(soundCheck.isChecked() == false) {
                   preferences.setSoundEnabled(false);
               }
               else {
                   preferences.setSoundEnabled(true);
               }
           }
        });

        table.add(titleLabel).center().size(-45,50);
        table.row();
        table.add(volumeMusicLabel).center().width(120);
        table.add(musicSlider).left();
        table.row();
        table.add(musicOnOffLabel).center().width(120);
        table.add(musicCheck).left();
        table.row();
        table.add(volumeSoundLabel).center().width(120);
        table.add(soundSlider).left();
        table.row();
        table.add(soundONOffLabel).center().width(120);
        table.add(soundCheck).left();
        table.row();
        table.add(returnLabel).center().width(30);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
            game.setScreen(new IntroScreen((MyGame) game));
            dispose();
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
