package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOptions implements Screen {

    public Stage stage;
    private Viewport viewport;

    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private TiledDrawable region;
    private TextureRegion background;
    private Texture img;
    private AppPreferences preferences;
    public static TextButton menu;

    public GameOptions(SpriteBatch sb){

        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        preferences = new AppPreferences();
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();

        table.left();
        table.setSize(300,150);
        table.setPosition(50,29);

        //table.setFillParent(true);

        img = new Texture("NERO.jpg");
        background = new TextureRegion(img);
        region = new TiledDrawable(new TextureRegion(background));
        table.setBackground(region);

        volumeMusicLabel = new Label("Music Volume", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        volumeSoundLabel = new Label("Sound Volume", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        musicOnOffLabel = new Label("Music Enabled", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        soundOnOffLabel = new Label("Sound Enabled", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        final Slider musicSlider = new Slider(0.1f,1f,0.05f,false, skin);
        musicSlider.setValue(preferences.getMusicVolume());

        musicSlider.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                preferences.setMusicVolume(musicSlider.getValue());
            }
        });

        final CheckBox musicCheck = new CheckBox(null, skin);
        musicCheck.setChecked(preferences.isMusicEnabled());

        musicCheck.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(musicCheck.isChecked() == false) {
                    preferences.setMusicEnabled(false);
                }
                else {
                    preferences.setMusicEnabled(true);
                }
            }
        });

        final Slider soundSlider = new Slider(0.1f,1f,0.05f,false, skin);
        soundSlider.setValue(preferences.getSoundVolume());

        soundSlider.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
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

        menu = new TextButton("Menu", skin);


        table.add(volumeMusicLabel).expandX().padTop(10);
        table.add(musicSlider).padTop(10).padRight(30);
        table.row();
        table.add(musicOnOffLabel).expandX();
        table.add(musicCheck).padRight(150);
        table.row();
        table.add(volumeSoundLabel).expandX();
        table.add(soundSlider).padRight(30);
        table.row();
        table.add(soundOnOffLabel).expandX();
        table.add(soundCheck).padRight(150);
        table.row();
        table.add(menu).padBottom(10);


        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
