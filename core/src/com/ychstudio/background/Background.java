package com.ychstudio.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ychstudio.gamesys.GM;

public class Background {

    private SpriteBatch batch;
    
    private Sprite earthSprite;
    private Texture spaceTexture;
    
    private float width;
    private float height;
    
    private float previousPlayerX;
    private float previousPlayerY = 0;
    
    private float offsetX = 0; // space texture offset of x
    private float offsetY = 0; // space texture offset of y
    
    public Background(SpriteBatch batch, float width, float height) {
        this.batch = batch;
        this.width = width * 2;
        this.height = height;
        
        AssetManager assetManager = GM.getAssetManager();
        earthSprite = new Sprite(assetManager.get("images/EarthBackground.png", Texture.class));
        earthSprite.setBounds(-width, 0, width * 2, height);
        
        spaceTexture = assetManager.get("images/SpaceBackground.png", Texture.class);
        spaceTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        spaceTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
    }
    
    private float getOffset(float value, float min, float max) {
        float total = max - min;
        if (value < min) {
            while (value < min) {
                value += total;
            }
            return value;
        }
        else if (value > max) {
            while (value > max) {
                value -= total;
            }
            return value;
        }
        return value;
    }
    
    public void update(Vector2 playerPos) {
        
        float currentPlayerX = playerPos.x;
        float currentPlayerY = playerPos.y;
        
        offsetX += -(currentPlayerX - previousPlayerX) / 6f;
        offsetY += -(currentPlayerY - previousPlayerY) / 6f;

        offsetY = getOffset(offsetY, 0, height);
        
        previousPlayerX = currentPlayerX;
        previousPlayerY = currentPlayerY;
    }
    
    public void render() {
        
        float offsetPercentY = offsetY / height;
        
        batch.draw(spaceTexture, -width/4+offsetX, previousPlayerY-height/2, width, offsetY, 0, 1-offsetPercentY, 1, 1);
        batch.draw(spaceTexture, -width/4+offsetX, previousPlayerY-height/2+offsetY, width, height-offsetY, 0, 0, 1, 1-offsetPercentY);
        
        earthSprite.draw(batch);
    }
    
}
