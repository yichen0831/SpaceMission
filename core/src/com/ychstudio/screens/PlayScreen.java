package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kotcrab.vis.ui.VisUI;
import com.ychstudio.SpaceRocket;


public class PlayScreen implements Screen{
    
    private SpaceRocket game;
    private SpriteBatch batch;
    
    private boolean paused;
    
    private Label textLabel;
    float x;
    float y;
    float vx = 100;
    float vy = 100;
    
    public PlayScreen(SpaceRocket game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        
        x = 100;
        y = 100;
        textLabel = new Label("TEST!!", VisUI.getSkin());
        textLabel.setPosition(x, y);

        paused = false;
        
    }
    
    public void update(float delta) {
        x += vx * delta;
        y += vy * delta;
        if (x > Gdx.graphics.getWidth() - textLabel.getWidth()) {
            x = Gdx.graphics.getWidth() - textLabel.getWidth();
            vx *= -1;
        }
        else if (x < 0) {
            x = 0;
            vx *= -1;
        }
        
        if (y > Gdx.graphics.getHeight() - textLabel.getHeight()) {
            y = Gdx.graphics.getHeight() - textLabel.getHeight();
            vy *= -1;
        }
        else if (y < 0) {
            y = 0;
            vy *= -1;
        }
        textLabel.setPosition(x, y);
    }
    
    public void inputHandle(float delta) {
    }

    @Override
    public void render(float delta) {
        inputHandle(delta);
        
        if (!paused) {
            update(delta);
        }
        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.25f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        textLabel.draw(batch, 1.0f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
    }
}
