package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Integer mondoTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;

    private Label countdownLabel;
    private static Label punteggioLabel;
    private Label tempoLabel;
    private Label livelloLabel;
    private Label mondoLabel;
    private Label playerLabel;

    public Hud(SpriteBatch sb){

        mondoTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();

        table.top();

        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", mondoTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        punteggioLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tempoLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livelloLabel = new Label("1-3", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mondoLabel = new Label("MONDO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerLabel = new Label("PLAYER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(playerLabel).expandX().padTop(10);
        table.add(mondoLabel).expandX().padTop(10);
        table.add(tempoLabel).expandX().padTop(10);
        table.row();
        table.add(punteggioLabel).expandX();
        table.add(livelloLabel).expandX();
        table.add(countdownLabel).expandX();
        stage.addActor(table);

    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (mondoTimer > 0) {
                mondoTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", mondoTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        punteggioLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}
