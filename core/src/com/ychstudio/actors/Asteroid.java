package com.ychstudio.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.ychstudio.gamesys.ActorBuilder;
import com.ychstudio.screens.PlayScreen;

public class Asteroid extends Actor {

    private PlayScreen playScreen;
    private float damage;

    public Asteroid(PlayScreen playScreen, Body body, TextureRegion textureRegion, float width, float height) {
        this(playScreen, body, new Sprite(textureRegion), width, height);
    }

    public Asteroid(PlayScreen playScreen, Body body, Sprite sprite, float width, float height) {
        super(body, sprite, width, height);
        this.playScreen = playScreen;
        damage = width;
    }

    public float getDamage() {
        return damage;
    }

    public void explode() {
        ActorBuilder.createAsteroidExplodeEffect(body.getPosition().x, body.getPosition().y, playScreen.getParticleEffectArray(), MathUtils.ceil(damage));
        toBeRemoved = true;
    }

    @Override
    public void update(float delta) {
        x = body.getPosition().x;
        y = body.getPosition().y;

        float rotation = body.getAngle() * MathUtils.radDeg;
        sprite.setRotation(rotation);

        sprite.setPosition(x - width / 2, y - width / 2);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
