package com.ychstudio.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Actor {
    
    Body body;
    Sprite sprite;
    
    public Actor(Body body, Sprite sprite, float width, float height) {
        this.body = body;
        this.sprite = sprite;
        this.sprite.setOriginCenter();
        this.sprite.setBounds(body.getPosition().x - width/2, body.getPosition().y - height/2, width, height);
    }
    
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

}
