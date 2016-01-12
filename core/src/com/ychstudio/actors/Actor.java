package com.ychstudio.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Actor {
    
    float x, y;
    float width, height;
    Body body;
    Sprite sprite;
    
    public Actor(Body body, Sprite sprite, float width, float height) {
        x = body.getPosition().x;
        y = body.getPosition().y;
        this.width = width;
        this.height = height;
        
        this.body = body;
        this.sprite = sprite;
        this.sprite.setBounds(x - width/2, y - height/2, width, height);
        this.sprite.setOriginCenter();
    }
    
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

}
