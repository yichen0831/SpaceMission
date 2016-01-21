package com.ychstudio.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Array;
import com.ychstudio.gamesys.ActorBuilder;
import com.ychstudio.gamesys.GM;
import com.ychstudio.screens.PlayScreen;

public class Player extends Actor {
    
    private PlayScreen playScreen;
    private Sprite flame;
    private boolean left_throttle;
    private boolean right_throttle;
    private float rotation;
    private float torque = 2.4f;
    private float force = 1.6f;

    private float maxSpeed = 20f;
    private float speed;
    private float maxHp = 10f;
    private float hp;
    
    private float goalY = 1000f; // the y position of the goal
    
    private boolean alive;
    
    private final Vector2 tmpV = new Vector2();

    public Player(PlayScreen playScreen, Body body, TextureRegion textureRegion, float width, float height) {
        this(playScreen, body, new Sprite(textureRegion), width, height);
    }

    public Player(PlayScreen playScreen, Body body, Sprite sprite, float width, float height) {
        super(body, sprite, width, height);
        this.playScreen = playScreen;
        TextureRegion textureRegion = GM.getAssetManager().get("images/actors.pack", TextureAtlas.class).findRegion("Flame");
        flame = new Sprite(textureRegion);
        flame.setSize(width / 2f, height);
        
        left_throttle = false;
        right_throttle = false;
        
        rotation = 0;

        hp = maxHp;
        speed = 0;
        alive = true;
    }

    @Override
    public void update(float delta) {
        
        // player controls
        if (alive) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                right_throttle = true;
            }
            else {
                right_throttle = false;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                left_throttle = true;
            }
            else {
                left_throttle = false;
            }

            if (left_throttle && right_throttle) {
                float px = force * MathUtils.sin(-body.getAngle());
                float py = force * MathUtils.cos(-body.getAngle());
                body.applyLinearImpulse(tmpV.set(px, py), body.getWorldCenter(), true);
            }
            else if (left_throttle) {
                body.applyTorque(-torque, true);
            }
            else if (right_throttle) {
                body.applyTorque(torque, true);
            }
        }
        else {
            // player is dead
            float vx = body.getLinearVelocity().x / 2f;
            float vy = body.getLinearVelocity().y / 2f;
            body.setLinearVelocity(vx, vy);
        }
        
        // limit player's speed
        if (body.getLinearVelocity().len2() > maxSpeed * maxSpeed) {
            body.setLinearVelocity(body.getLinearVelocity().nor().scl(maxSpeed));
        }
        
        // when player enters the space
        if (body.getPosition().y > GM.SKY_LINE) {
            body.setGravityScale(0);
            body.setLinearDamping(0.25f);
        }
        else {
            body.setGravityScale(1);
            body.setLinearDamping(0);
        }
        
        // player explodes
        if (alive) {
            if (isOutOfBound() || hp < 0) {
                explode();
            }
        }
        
        x = body.getPosition().x;
        y = body.getPosition().y;
        sprite.setPosition(x - width / 2, y - height / 2);
        
        rotation = MathUtils.radiansToDegrees * body.getAngle();
        sprite.setRotation(rotation);
        
        float vx = body.getLinearVelocity().x;
        float vy = body.getLinearVelocity().y;
        speed = (float) Math.sqrt(vx * vx + vy * vy);
        
    }

    @Override
    public void render(SpriteBatch batch) {
        
        if (!alive) {
            return;
        }
        
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
        sprite.draw(batch);
    }
    
    public boolean isPlayerAlive() {
        return alive;
    }

    public boolean isOutOfBound() {
        return (body.getPosition().y < GM.SKY_LINE && (body.getPosition().x < 5.5f || body.getPosition().x > 14.5f))
                || (body.getPosition().x < 0.2 || body.getPosition().x > 19.8);
    }
    
    public void explode() {
        hp = 0;
        alive = false;
        Filter filter = body.getFixtureList().get(0).getFilterData();
        filter.categoryBits = GM.NOTHING_CATEGORY_BITS;
        body.getFixtureList().get(0).setFilterData(filter);
        body.setGravityScale(0);
        body.setLinearVelocity(0, 0);
        Array<ParticleEffect> particleEffects = playScreen.getParticleEffectArray();
        ActorBuilder.createExplodeEffect(x, y, particleEffects);
    }
    
    public void restart() {
        // reset player
        Filter filter = body.getFixtureList().get(0).getFilterData();
        filter.categoryBits = GM.PLAYER_CATEGORY_BITS;
        body.getFixtureList().get(0).setFilterData(filter);
        body.setLinearVelocity(0, 0);
        body.setTransform(10f, 2.5f, 0);
        body.setAngularVelocity(0);
        hp = maxHp;
        alive = true;
    }
    
    public void hitGround() {
        if (Math.abs(MathUtils.radDeg * body.getAngle()) >= 90f || getSpeed() > 10f) {
            explode();
        }
    }

    public float getHp() {
        return hp;
    }
    
    public float getHpRatio() {
        return hp / maxHp;
    }
    
    public float getSpeedRatio() {
        return speed / maxSpeed;
    }
    
    public float getProgress() {
        return y / goalY;
    }

    public void getDamaged(float damage) {
        hp -= damage;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public Vector2 getPosition() {
        return body.getPosition();
    }
    
    public boolean goal(float goal) {
        return alive && y >= goal;
    }

}
