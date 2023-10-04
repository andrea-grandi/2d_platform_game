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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

public class IntroScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Game game;
    private AppPreferences preferences;

    public IntroScreen(Game game){

        this.game = game;
        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGame) game).batch);
        preferences = new AppPreferences();

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label nameLabel = new Label("NOME GIOCO", font);
        Label playLabel = new Label("PRESS ENTER TO PLAY", font);
        Label aboutLabel = new Label("PRESS A FOR ABOUT", font);
        Label OptionsLabel = new Label("PRESS Z FOR OPTIONS", font);

        table.add(nameLabel).expandX();
        table.row();
        table.add(playLabel).expandX().padTop(10f);
        table.row();
        table.add(aboutLabel).expandX();
        table.row();
        table.add(OptionsLabel).expandX();

        stage.addActor(table);

        nameLabel.setVisible(true);
        playLabel.setVisible(true);
        aboutLabel.setVisible(true);

        MyGame.setScreenID(0);


    }
