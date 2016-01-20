package com.ychstudio.gamesys;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.ychstudio.actors.Asteroid;
import com.ychstudio.actors.Ground;
import com.ychstudio.actors.Player;
import com.ychstudio.screens.PlayScreen;

public class ActorBuilder {
    private static ActorBuilder instance = new ActorBuilder();
    
    private World world;
    private AssetManager assetManager;
    
    private ActorBuilder() {
        assetManager = GM.getAssetManager();
    }
    
    public static void setWorld(World world) {
        instance.world = world;
    }
    
    public static Ground createGround(float x, float y) {
        World world = instance.world;
        AssetManager assetManager = instance.assetManager;
        Texture earthTexture = assetManager.get("images/Earth.png", Texture.class);
        Sprite sprite = new Sprite(earthTexture);
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        
        float width = 20f;
        float height = 2f;
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;
        fixtureDef.filter.categoryBits = GM.GROUND_CATEGORY_BITS;
        fixtureDef.filter.maskBits = GM.GROUND_MASK_BITS;
        body.createFixture(fixtureDef);
        shape.dispose();
        
        Ground ground = new Ground(body, sprite, width, height);
        body.setUserData(ground);
        
        return ground;
    }

    public static Asteroid createAsteroid(PlayScreen playScreen, float x, float y, String color, int size) {
        size = MathUtils.clamp(size, 1, 6);
        float asteroid_size = MathUtils.clamp(size, 2, 6) / 2f;
        color = color.toLowerCase().startsWith("r") ? "r" : "b";
        World world = instance.world;
        AssetManager assetManager = instance.assetManager;
        TextureAtlas textureAtlas = assetManager.get("images/actors.pack", TextureAtlas.class);
        TextureRegion textureRegion = textureAtlas.findRegion("asteroid_" + color + size);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.gravityScale = 0;

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(asteroid_size / 3f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.filter.categoryBits = GM.ASTEROID_CATEGORY_BITS;
        fixtureDef.filter.maskBits = GM.ASTEROID_MASK_BITS;

        body.createFixture(fixtureDef);

        shape.dispose();

        Asteroid asteroid = new Asteroid(playScreen, body, textureRegion, asteroid_size, asteroid_size);
        body.setUserData(asteroid);
        return asteroid;
    }

    public static Player createPlayer(PlayScreen playScreen, float x, float y) {
        World world = instance.world;
        AssetManager assetManager = instance.assetManager;
        TextureAtlas textureAtlas = assetManager.get("images/actors.pack", TextureAtlas.class);
        TextureRegion spaceShipTextureRegion = textureAtlas.findRegion("SpaceShip");

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.angularDamping = 0.6f;
        
        Body body = world.createBody(bodyDef);
        
        float width = 64f / GM.PPM;
        float height = 64f / GM.PPM;
        
        PolygonShape shape = new PolygonShape();
        shape.set(new float[] {
                -width / 2, -height / 2,
                width / 2, -height / 2,
                width / 4, height / 2,
                -width / 4, height / 2
        });
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4.8f;
        fixtureDef.friction = 0.5f;
        fixtureDef.filter.categoryBits = GM.PLAYER_CATEGORY_BITS;
        fixtureDef.filter.maskBits = GM.PLAYER_MASK_BITS;
        
        body.createFixture(fixtureDef);
        shape.dispose();
        
        Player player = new Player(playScreen, body, spaceShipTextureRegion, width ,height);
        body.setUserData(player);
        
        return player;
    }
    
    public static ParticleEffect createExplodeEffect(float x, float y, Array<ParticleEffect> particleEffects) {
        ParticleEffect effect = GM.getAssetManager().get("particles/explode.particle", ParticleEffect.class);
        effect.setPosition(x, y);
        effect.reset();
        if (particleEffects != null) {
            particleEffects.add(effect);
        }
        return effect;
    }

    public static ParticleEffect createAsteroidExplodeEffect(float x, float y, Array<ParticleEffect> particleEffects, int size) {
        ParticleEffect effect;

        switch(size) {
            case 1:
                effect = GM.getAssetManager().get("particles/asteroid_explode_small.particle", ParticleEffect.class);
                break;
            case 2:
                effect = GM.getAssetManager().get("particles/asteroid_explode_medium.particle", ParticleEffect.class);
                break;
            case 3:
            default:
                effect = GM.getAssetManager().get("particles/asteroid_explode.particle", ParticleEffect.class);
                break;
        }

        effect.setPosition(x, y);
        effect.reset();
        if (particleEffects != null) {
            particleEffects.add(effect);
        }
        return effect;
    }
}
