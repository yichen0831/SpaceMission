package com.ychstudio.screens.huds;

import com.badlogic.gdx.Gdx;
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
    }
    
    public void render() {
        batch.begin();
        hpIconSprite.draw(batch);
        speedIconSprite.draw(batch);
        targetIconSprite.draw(batch);
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
        batch.dispose();
    }
}
