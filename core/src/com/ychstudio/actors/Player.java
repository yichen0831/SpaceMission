package com.ychstudio.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
    
    private ShapeRenderer shapeRenderer;
    private float lxx = 0;
    private float lyy = 0;
    private float rxx = 0;
    private float ryy = 0;

    public Player(Body body, Sprite sprite, float width, float height) {
        super(body, sprite, width, height);
        flame = new Sprite(GM.getAssetManager().get("images/Flame.png", Texture.class));
        flame.setSize(width / 2f, height / 2f);
        
        left_throttle = false;
        right_throttle = false;
        
        rotation = 0;
        
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // reset player
            body.setLinearVelocity(0, 0);
            body.setTransform(10f, 2.8f, 0);
            body.setGravityScale(0);
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            left_throttle = true;
            float px = power * MathUtils.sin(-body.getAngle());
            float py = power * MathUtils.cos(-body.getAngle());
            float dx = x - (width / 2f * SQRT5) * MathUtils.sin(-body.getAngle() + MathUtils.PI / 8f);
            float dy = y - (height / 2f * SQRT5) * MathUtils.cos(-body.getAngle() + MathUtils.PI / 8f);
            lxx = dx;
            lyy = dy;
            
            body.applyLinearImpulse(px, py, dx, dy, true);
        }
        else {
            left_throttle = false;
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            right_throttle = true;
            float px = power * MathUtils.sin(-body.getAngle());
            float py = power * MathUtils.cos(-body.getAngle());
            float dx = x - (width / 2f * SQRT5) * MathUtils.sin(-body.getAngle() - MathUtils.PI / 8f);
            float dy = y - (height / 2f * SQRT5) * MathUtils.cos(-body.getAngle() - MathUtils.PI / 8f);
            rxx = dx;
            ryy = dy;
            
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
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0.8f, 0.8f, 1, 1);
            shapeRenderer.circle(lxx, lyy, 0.2f);
            shapeRenderer.end();
        }
        if (right_throttle) {
            flame.setPosition(x, y - height);
            flame.setOrigin(0, height);
            flame.setRotation(rotation);
            flame.draw(batch);
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 0.8f, 0.8f, 1);
            shapeRenderer.circle(rxx, ryy, 0.2f);
            shapeRenderer.end();
        }
    }

}
