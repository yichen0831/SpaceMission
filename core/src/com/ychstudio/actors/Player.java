package com.ychstudio.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.ychstudio.gamesys.GM;

public class Player extends Actor {
    
    private Sprite flame;
    private boolean left_throttle;
    private boolean right_throttle;
    private float rotation;
    private float power = 0.8f;
    
    private static final float SQRT5 = (float) Math.sqrt(5.0);

    public Player(Body body, Sprite sprite, float width, float height) {
        super(body, sprite, width, height);
        flame = new Sprite(GM.getAssetManager().get("images/Flame.png", Texture.class));
        flame.setSize(width / 2f, height / 2f);
        
        left_throttle = false;
        right_throttle = false;
        
        rotation = 0;
    }

    @Override
    public void update(float delta) {
    	// TODO remove this code
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // reset player
            body.setLinearVelocity(0, 0);
            body.setTransform(10f, 2.8f, 0);
            body.setAngularVelocity(0);
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            left_throttle = true;
            float px = power * MathUtils.sin(-body.getAngle());
            float py = power * MathUtils.cos(-body.getAngle());
            float dx = x - (width / 4f * SQRT5) * MathUtils.sin(-body.getAngle() + MathUtils.PI / 8f);
            float dy = y - (height / 4f * SQRT5) * MathUtils.cos(-body.getAngle() + MathUtils.PI / 8f);
            
            body.applyLinearImpulse(px, py, dx, dy, true);
        }
        else {
            left_throttle = false;
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            right_throttle = true;
            float px = power * MathUtils.sin(-body.getAngle());
            float py = power * MathUtils.cos(-body.getAngle());
            float dx = x - (width / 4f * SQRT5) * MathUtils.sin(-body.getAngle() - MathUtils.PI / 8f);
            float dy = y - (height / 4f * SQRT5) * MathUtils.cos(-body.getAngle() - MathUtils.PI / 8f);
            
            body.applyLinearImpulse(px, py, dx, dy, true);
        }
        else {
            right_throttle = false;
        }
        
        x = body.getPosition().x;
        y = body.getPosition().y;
        sprite.setPosition(x - width / 2, y - height / 2);
        
        rotation = MathUtils.radiansToDegrees * body.getAngle();
        sprite.setRotation(rotation);
        
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
        if (left_throttle) {
            flame.setPosition(x - width / 2, y - height);
            flame.setOrigin(width / 2, height);
            flame.setRotation(rotation);
            flame.draw(batch);
        }
        if (right_throttle) {
            flame.setPosition(x, y - height);
            flame.setOrigin(0, height);
            flame.setRotation(rotation);
            flame.draw(batch);
        }
    }

}
