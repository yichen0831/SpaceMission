package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ychstudio.SpaceRocket;

public class MenuScreen implements Screen {
    
    private SpaceRocket game;
    private SpriteBatch batch;
    
    private Label startGameLabel;
    private Label exitGameLabel;
    
    private FitViewport viewport;
    private Stage stage;
    
    private Color selectedColor = new Color(0.8f, 0.8f, 0.5f, 1.0f);
    private Color unselectedColor = new Color(0.6f, 0.6f, 0.6f, 1.0f);
    private int selected = 0;
    private int preselected = 0;
    private Array<Label> options;
    
    private boolean paused;
    
    public MenuScreen(SpaceRocket game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/MONOFONT.TTF"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        BitmapFont monoFont30 = generator.generateFont(parameter);
        generator.dispose();
        
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, batch);
        
        startGameLabel = new Label("Start", new Label.LabelStyle(monoFont30, Color.WHITE));
        startGameLabel.setPosition((Gdx.graphics.getWidth() - startGameLabel.getWidth()) / 2, (Gdx.graphics.getHeight() - startGameLabel.getHeight()) / 2);
        
        exitGameLabel = new Label("Exit", new Label.LabelStyle(monoFont30, Color.WHITE));
        exitGameLabel.setPosition((Gdx.graphics.getWidth() - exitGameLabel.getWidth()) / 2, (Gdx.graphics.getHeight() - exitGameLabel.getHeight()) / 2 - 28);
        
        options = new Array<>();
        options.add(startGameLabel);
        options.add(exitGameLabel);
        
        stage.addActor(startGameLabel);
        stage.addActor(exitGameLabel);
        
        startGameLabel.addAction(Actions.color(selectedColor));
        exitGameLabel.addAction(Actions.color(unselectedColor));
        
        paused = false;
        
    }
    
    public void update(float delta) {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            preselected = selected;
            selected = Math.max(0, selected - 1);
            options.get(preselected).addAction(Actions.color(unselectedColor, 0.2f));
            options.get(selected).addAction(Actions.color(selectedColor, 0.2f));
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            preselected = selected;
            selected = Math.min(options.size-1, selected + 1);
            options.get(preselected).addAction(Actions.color(unselectedColor, 0.2f));
            options.get(selected).addAction(Actions.color(selectedColor, 0.2f));
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selected == 0) {
                game.startGame();
            }
            else if (selected == 1) {
                game.exitGame();
            }
        }
        
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            update(delta);
        }
        
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.6f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        
    }

}
