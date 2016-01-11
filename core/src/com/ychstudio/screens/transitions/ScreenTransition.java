package com.ychstudio.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenTransition {
    
    public float getDuration();
    
    public void rander(SpriteBatch batch, Texture currentScreen, Texture nextScreen, float alpha);

}
