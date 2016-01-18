package com.ychstudio.gamesys;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ychstudio.actors.Player;

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        short categoryBitsA = fixtureA.getFilterData().categoryBits;
        short categoryBitsB = fixtureB.getFilterData().categoryBits;
        
        if (categoryBitsA == GM.PLAYER_CATEGORY_BITS || categoryBitsB == GM.PLAYER_CATEGORY_BITS) {
            if (categoryBitsA == GM.PLAYER_CATEGORY_BITS) {
                Player player = (Player) fixtureA.getBody().getUserData();
                
                switch(categoryBitsB) {
                    case GM.GROUND_CATEGORY_BITS:
                        player.hitGround();
                        break;
                }
                
            }
            else {
                Player player = (Player) fixtureB.getBody().getUserData();
                
                switch(categoryBitsA) {
                    case GM.GROUND_CATEGORY_BITS:
                        player.hitGround();
                        break;
                }

            }
        }
        
    }

    @Override
    public void endContact(Contact contact) {
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }

}
