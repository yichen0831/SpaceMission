package com.ychstudio.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends Actor {

    public Player(Body body, Sprite sprite, float width, float height) {
        super(body, sprite, width, height);
    }

    @Override
    public void update(float delta) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
