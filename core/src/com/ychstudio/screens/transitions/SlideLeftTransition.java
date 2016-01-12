package com.ychstudio.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
        
        batch.begin();
        batch.draw(currentScreenTexture, w * alpha, 0, w, currentScreenTexture.getHeight(), 0, 0, (int)w, currentScreenTexture.getHeight(), false, true);
        batch.draw(nextScreenTexture, -w + w * alpha, 0, w, nextScreenTexture.getHeight(), 0, 0, (int)w, nextScreenTexture.getHeight(), false, true);
        batch.end();
    }

}
