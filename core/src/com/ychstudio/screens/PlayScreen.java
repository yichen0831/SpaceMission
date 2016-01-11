package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ychstudio.SpaceRocket;
import com.ychstudio.actors.Actor;
import com.ychstudio.actors.Player;
import com.ychstudio.gamesys.ActorBuilder;


public class PlayScreen implements Screen{
    
    private SpaceRocket game;
    private SpriteBatch batch;
    
    private FitViewport viewport;
    private OrthographicCamera camera;
    
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    
    private boolean paused;
    
    private Array<Actor> actors;
    
    public PlayScreen(SpaceRocket game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(20f, 30f, camera);
        camera.translate(10, 15);
        world = new World(new Vector2(0, -9.8f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        
        actors = new Array<>();
        
        ActorBuilder.setWorld(world);
        
        Player player = ActorBuilder.createPlayer(10f, 30f);
        actors.add(player);
        
        paused = false;
        
    }
    
    public void update(float delta) {
        world.step(1f / 60.0f, 8, 3);
        
        for (Actor actor : actors) {
            actor.update(delta);
        }
    }
    
    public void inputHandle(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            camera.zoom += 0.1f;
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            camera.zoom -= 0.1f;
        }
    }

    @Override
    public void render(float delta) {
        inputHandle(delta);
        
        if (!paused) {
            update(delta);
        }
        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.25f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Actor actor : actors) {
            actor.render(batch);
        }
        batch.end();
        
        box2DDebugRenderer.render(world, camera.combined);
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
        world.dispose();
        box2DDebugRenderer.dispose();
        batch.dispose();
    }
}
