package com.ychstudio.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class SlideLeftTransition implements ScreenTransition {
    
    private float duration;
    
    public SlideLeftTransition(float duration) {
        this.duration = duration;
    }

    @Override
    public float getDuration() {
        return duration;
    }

    @Override
    public void rander(SpriteBatch batch, Texture currentScreenTexture, Texture nextScreenTexture, float alpha) {
        float w = Gdx.graphics.getWidth();
        alpha = Interpolation.fade.apply(alpha);
        
        Sprite currentSprite = new Sprite(currentScreenTexture);
        currentSprite.flip(false, true);
        currentSprite.setPosition(w * alpha, 0);
        Sprite nextSprite = new Sprite(nextScreenTexture);
        nextSprite.flip(false, true);
        nextSprite.setPosition(-w + w * alpha, 0);
        
        batch.begin();
        currentSprite.draw(batch);
        nextSprite.draw(batch);
        batch.end();
    }

}
