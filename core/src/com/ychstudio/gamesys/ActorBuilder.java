package com.ychstudio.gamesys;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ychstudio.actors.Ground;
import com.ychstudio.actors.Player;

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
        // TODO requires texture
        Sprite sprite = new Sprite();
        
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
    
    public static Player createPlayer(float x, float y) {
        World world = instance.world;
        AssetManager assetManager = instance.assetManager;
        Texture spaceRocketTexture = assetManager.get("images/SpaceShip.png", Texture.class);
        
        Sprite sprite = new Sprite(spaceRocketTexture);
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        
        Body body = world.createBody(bodyDef);
        
        float width = 64f / GM.PPM;
        float height = 64f / GM.PPM;
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4f;
        fixtureDef.friction = 0.5f;
        fixtureDef.filter.categoryBits = GM.PLAYER_CATEGORY_BITS;
        fixtureDef.filter.maskBits = GM.PLAYER_MASK_BITS;
        
        body.createFixture(fixtureDef);
        shape.dispose();
        
        Player player = new Player(body, sprite, width ,height);
        body.setUserData(player);
        
        return player;
    }

}
