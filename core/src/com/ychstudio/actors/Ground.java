package com.ychstudio.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Ground extends Actor{

    public Ground(Body body, Sprite sprite, float width, float height) {
        super(body, sprite, width, height);
        
        this.width = 40f;
        this.height = 8f;
        
        sprite.setBounds(x - this.width / 2, y - this.height / 2 - 2.4f, this.width, this.height);
    }

    @Override
    public void update(float delta) {
        // no need to update
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
