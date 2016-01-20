package com.ychstudio.screens.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.ychstudio.gamesys.GM;

public class StatusHud implements Disposable {

    private float width;
    private float height;
    
    private SpriteBatch batch;
    
    private Sprite hpIconSprite;
    private Sprite speedIconSprite;
    private Sprite targetIconSprite;
    
    private Texture barTexture;
    private Texture barFrameTexture;
    private float barWidth = 60f;
    private float barHeight = 20f;
    
    private float playerHp; // a ratio value between 0 and 1
    private float playerSpeed; // a ratio value between 0 and 1 
    private float progress; // a ratio value between 0 and 1
    
    public StatusHud() {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        TextureAtlas textureAtlas = GM.getAssetManager().get("images/actors.pack", TextureAtlas.class);
        hpIconSprite = new Sprite(textureAtlas.findRegion("Shield"));
        speedIconSprite = new Sprite(textureAtlas.findRegion("Lightning"));
        targetIconSprite = new Sprite(textureAtlas.findRegion("Target"));
        
        hpIconSprite.setBounds(6f, height - 36f, 20f, 20f);
        speedIconSprite.setBounds(6f, height - 60f, 20f, 20f);
        targetIconSprite.setBounds(width - 26f, height - 36f, 20f, 20f);
        
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        barTexture = new Texture(pixmap);
        pixmap.dispose();
        
        pixmap = new Pixmap((int) barWidth, (int) barHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        barFrameTexture = new Texture(pixmap);
        pixmap.dispose();
        
    }
    
    public void render() {
        batch.begin();
        hpIconSprite.draw(batch);
        speedIconSprite.draw(batch);
        targetIconSprite.draw(batch);
        
        // draw HP bar
        batch.draw(barTexture, 30f, height - 36f, barWidth * playerHp, barHeight);
        batch.draw(barFrameTexture, 30f, height - 36f, barWidth, barHeight);
        
        // draw speed bar
        batch.draw(barTexture, 30f, height - 60f, barWidth * playerSpeed, barHeight);
        batch.draw(barFrameTexture, 30f, height - 60f, barWidth, barHeight);
        
        // draw progress bar
        batch.draw(barTexture, width - 30f - barWidth * progress, height - 36f, barWidth * progress, barHeight);
        batch.draw(barFrameTexture, width - 30f - barWidth, height - 36f, barWidth, barHeight);
        
        batch.end();
    }
    
    public void setPlayerHp(float hp) {
        playerHp = MathUtils.clamp(hp, 0, 1f);
    }
    
    public void setPlayerSpeed(float speed) {
        playerSpeed = MathUtils.clamp(speed, 0, 1f);
    }
    
    public void setProgress(float progress) {
        this.progress = MathUtils.clamp(progress, 0, 1f);
    }
    
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void dispose() {
        barTexture.dispose();
        barFrameTexture.dispose();
        batch.dispose();
    }
}
