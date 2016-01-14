package com.ychstudio.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ychstudio.gamesys.GM;

public class Background {

    private SpriteBatch batch;
    
    private Sprite earthSprite;
    private Texture starsTexture;
    
    private Sprite blueDustSprite1;
    private Sprite blueDustSprite2;
    private Sprite coldNebulaSprite;
    private Sprite hotNebulaSprite;
    private Sprite violetDustSprite;
    private Sprite yellowDustSprite1;
    private Sprite yellowDustSprite2;
    private Sprite moonSprite;
    
    private float width;
    private float height;
    
    private float previousPlayerX;
    private float previousPlayerY = 0;
    
    private float offsetX = 0; // space texture offset of x
    private float offsetY = 0; // space texture offset of y
    
    public Background(SpriteBatch batch, float width, float height) {
        this.batch = batch;
        this.width = width * 2 * 2.5f;
        this.height = height * 2.5f;
        
        AssetManager assetManager = GM.getAssetManager();
        earthSprite = new Sprite(assetManager.get("images/EarthBackground.png", Texture.class));
        earthSprite.setBounds(-width, 0, width * 2, height);
        
        Texture blueDustTexture = assetManager.get("images/BlueDust.png", Texture.class);
        Texture coldNebulaTexture = assetManager.get("images/ColdNebula.png", Texture.class);
        Texture hotNebulaTexture = assetManager.get("images/HotNebula.png", Texture.class);
        Texture violetDustTexture = assetManager.get("images/VioletDust.png", Texture.class);
        Texture yellowDustTexture = assetManager.get("images/YellowDust.png", Texture.class);
        Texture moonTexture = assetManager.get("images/Moon.png", Texture.class);
        
        blueDustTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        coldNebulaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        hotNebulaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        violetDustTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        yellowDustTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        moonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        blueDustSprite1 = new Sprite(blueDustTexture);
        blueDustSprite2 = new Sprite(blueDustTexture);
        coldNebulaSprite = new Sprite(coldNebulaTexture);
        hotNebulaSprite = new Sprite(hotNebulaTexture);
        violetDustSprite = new Sprite(violetDustTexture);
        yellowDustSprite1 = new Sprite(yellowDustTexture);
        yellowDustSprite2 = new Sprite(yellowDustTexture);
        
        blueDustSprite1.setBounds(-10f, 60f, 45f, 30f);
        yellowDustSprite1.setBounds(-28f, 180f, 62f, 32f);
        coldNebulaSprite.setBounds(-20f, 300f, 60f, 65f);
        hotNebulaSprite.setBounds(-28f, 450f, 72f, 90f);
        yellowDustSprite2.setBounds(-6f, 630f, 62f, 32f);
        violetDustSprite.setBounds(-28f, 760f, 72f, 42f);
        blueDustSprite2.setBounds(-16f, 880f, 45f, 30f);
        
        moonSprite = new Sprite(moonTexture);
        moonSprite.setBounds(-2f, 980f, 52f, 52f);
        
        starsTexture = assetManager.get("images/Stars.png", Texture.class);
        starsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
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
        
        batch.draw(starsTexture, -width/4+offsetX, previousPlayerY-height/2, width, offsetY, 0, 1-offsetPercentY, 1, 1);
        batch.draw(starsTexture, -width/4+offsetX, previousPlayerY-height/2+offsetY, width, height-offsetY, 0, 0, 1, 1-offsetPercentY);
        
        earthSprite.draw(batch);
        blueDustSprite1.draw(batch);
        blueDustSprite2.draw(batch);
        coldNebulaSprite.draw(batch);
        hotNebulaSprite.draw(batch);
        violetDustSprite.draw(batch);
        yellowDustSprite1.draw(batch);
        yellowDustSprite2.draw(batch);
        
        moonSprite.draw(batch);
    }
    
}
