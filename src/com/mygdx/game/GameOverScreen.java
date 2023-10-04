package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Game game;
    private int i = 1;

    public GameOverScreen(Game game){

        this.game = game;
        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("PRESS ENTER TO PLAY AGAIN", font);
        Label returnMenuLabel = new Label("PRESS BACK TO RETURN MENU", font);
        Label exitLabel = new Label("PRESS ESC TO EXIT THE GAME", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);
        table.row();
        table.add(returnMenuLabel).expandX();
        table.row();
        table.add(exitLabel).expandX();

        stage.addActor(table);

        gameOverLabel.setVisible(true);
        playAgainLabel.setVisible(true);
        returnMenuLabel.setVisible(true);
        exitLabel.setVisible(true);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            game.setScreen(new PlayState((MyGame) game, i));
            dispose();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){

            game.setScreen(new IntroScreen((MyGame) game));
            dispose();

        }


        /*
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayState((MyGame) game));
            dispose();
        }

         */
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

