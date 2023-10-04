package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.*;

public class PlayState implements Screen {

    private MyGame game;
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Player player;
    //private Music music;

    private AppPreferences preferences;

    private static final int GAME_PAUSED = 0;
    private static final int GAME_RUNNING = 1;

    private int gameStatus;
    private GameOptions options;
    private Texture img;

    public PlayState(MyGame game){

        atlas = new TextureAtlas("player_zombie.pack");

        this.game = game;

        preferences = new AppPreferences();
        gamecam = new OrthographicCamera();

        gamePort = new FitViewport(MyGame.V_WIDTH / MyGame.PPM, MyGame.V_HEIGHT / MyGame.PPM, gamecam);

        hud = new Hud(game.batch);
        options = new GameOptions(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / MyGame.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        player = new Player(this);

        world.setContactListener(new WorldContactListener());

        gameStatus = GAME_RUNNING;

        img = new Texture("NERO.jpg");
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){

        MyGame.music.stop();
        MyGame.setScreenID(1);
        MyGame.musicLevel1.setVolume(preferences.getMusicVolume());
        MyGame.audio.setVolume(preferences.getSoundVolume());

        if(player.currentState != Player.State.DEAD) {
            if(preferences.isMusicEnabled()) {
                MyGame.musicLevel1.play();
                MyGame.musicLevel1.setLooping(true);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.W))
                player.jump();
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                player.fire();
                if(preferences.isSoundEnabled() == true)
                    MyGame.audio.play();

            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                MyGame.musicLevel1.pause();
                gamePaused();

            }

        }
        else{
            if(preferences.isMusicEnabled()) {
                MyGame.musicLevel1.stop();
                MyGame.dead.setVolume(preferences.getMusicVolume());
                MyGame.dead.play();
            }
        }

    }

    public void update(float dt){

        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 230 / MyGame.PPM) {
                enemy.b2body.setActive(true);
            }
        }

        hud.update(dt);

        //setta la gamecam sul nostro player

        if(player.currentState != Player.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        //aggiorno la gamecam
        gamecam.update();
        renderer.setView(gamecam);

    }


    @Override
    public void render(float delta) {

        if(gameStatus == GAME_RUNNING) {
            update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renderizza il mondo di gioco

        renderer.render();
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);

        game.batch.end();

        //disegna quello che la cam di hud vede
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.stage.draw();

        if(gameStatus == GAME_PAUSED){
            if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
                gameRunning();
            }

            GameOptions.menu.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    MyGame.musicLevel1.stop();
                    game.setScreen(new IntroScreen(game));
                    dispose();
                }
            });

            // disegno il pannello per le opzioni di gioco (GameOptions)

            game.batch.setProjectionMatrix(options.stage.getCamera().combined);
            options.stage.draw();

        }

        if(gameOver()){

            game.setScreen(new GameOverScreen(game));
            dispose();
        }

    }

    public void gamePaused(){
        gameStatus = GAME_PAUSED;
    }

    public void gameRunning(){
        gameStatus = GAME_RUNNING;
    }

    public boolean gameOver(){
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {

        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        options.dispose();
    }

    public Hud getHud(){ return hud; }

}
