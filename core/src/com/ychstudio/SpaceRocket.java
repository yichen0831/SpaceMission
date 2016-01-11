package com.ychstudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.ychstudio.screens.MenuScreen;
import com.ychstudio.screens.PlayScreen;
import com.ychstudio.screens.transitions.ScreenTransition;
import com.ychstudio.screens.transitions.SlideLeftTransition;

public class SpaceRocket extends Game {
	private SpriteBatch batch;
	private FitViewport viewport;
	private Stage stage;
	private Label fpsLabel;
	
	private int w;
	private int h;
	
	private Screen nextScreen;
	
	private FrameBuffer currentFbo;
	private FrameBuffer nextFbo;
	
	private float transitionTime = 0;
	
	private ScreenTransition screenTransition;

	@Override
	public void create () {
	    VisUI.load();
		batch = new SpriteBatch();
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		viewport = new FitViewport(w, h);
		stage = new Stage(viewport, batch);
		fpsLabel = new Label("FPS:", VisUI.getSkin());
		fpsLabel.setPosition(6f, 6f);
		stage.addActor(fpsLabel);
		
		setScreen(new MenuScreen(this));
	}
	
	public void startGame() {
	    setScreen(new PlayScreen(this), new SlideLeftTransition(1f));
	}
	
	public void backToMenu() {
	    setScreen(new MenuScreen(this), new SlideLeftTransition(1f));
	}
	
	@Override
	public void setScreen(Screen screen) {
	    setScreen(screen, null);
	}
	
	public void setScreen(Screen screen, ScreenTransition screenTransition) {
	    if (currentFbo != null) {
	        currentFbo.dispose();
	    }
	    if (nextFbo != null) {
	        nextFbo.dispose();
	    }
	    
	    currentFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
	    nextFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
	    
	    nextScreen = screen;
	    nextScreen.show();
	    nextScreen.resize(w, h);
	    nextScreen.render(0);
	    
	    if (this.screen != null) {
	        this.screen.pause();
	    }
	    nextScreen.pause();
	    
	    transitionTime = 0;
	    this.screenTransition = screenTransition;
	}
	
	public void exitGame() {
	    Gdx.app.exit();
	}
	
	public void update(float delta) {
	    fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void render () {
	    float delta = Math.min(Gdx.graphics.getDeltaTime(), 1f / 60.0f);
	    update(delta);
	    
	    if (nextScreen == null) {
	        if (screen != null) {
	            screen.render(delta);
	        }
	    }
	    else {
	        float duration = 0;
	        if (screenTransition != null) {
	            duration = screenTransition.getDuration();
	        }
	        transitionTime = Math.min(transitionTime + delta, duration);
	        
	        if (screenTransition == null || transitionTime >= duration) {
	            if (screen != null) {
	                screen.hide();
	            }
	            nextScreen.resume();
	            screen = nextScreen;
	            nextScreen = null;
	            screenTransition = null;
	        }
	        else {
	            currentFbo.begin();
	            if (screen != null) {
	                screen.render(delta);
	            }
	            currentFbo.end();
	            
	            nextFbo.begin();
	            nextScreen.render(delta);
	            nextFbo.end();
	            
	            float alpha = transitionTime / duration;
	            screenTransition.rander(batch, currentFbo.getColorBufferTexture(), nextFbo.getColorBufferTexture(), alpha);
	        }
	    }
	    
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	    w = width;
	    h = height;
	    viewport.update(width, height);
		super.resize(width, height);
	}

    @Override
	public void dispose() {
	    if (currentFbo != null) {
	        currentFbo.dispose();
	    }
	    if (nextFbo != null) {
	        nextFbo.dispose();
	    }
	    
		batch.dispose();
		VisUI.dispose();
	}

}
