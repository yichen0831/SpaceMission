package com.ychstudio.gamesys;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
    
    public static Player createPlayer(float x, float y) {
        World world = instance.world;
        AssetManager assetManager = instance.assetManager;
        Texture spaceRocketTexture = assetManager.get("images/SpaceRocket.png", Texture.class);
        
        Sprite sprite = new Sprite(spaceRocketTexture);
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        
        Body body = world.createBody(bodyDef);
        
        float width = 64f / GM.PPM;
        float height = 128f / GM.PPM;
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        
        body.createFixture(fixtureDef);
        shape.dispose();
        
        Player player = new Player(body, sprite, width ,height);
        body.setUserData(player);
        
        return player;
    }

}
