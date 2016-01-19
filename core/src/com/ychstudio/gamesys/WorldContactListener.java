package com.ychstudio.gamesys;

import com.badlogic.gdx.physics.box2d.*;
import com.ychstudio.actors.Asteroid;
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
                    case GM.ASTEROID_CATEGORY_BITS:
                        Asteroid asteroid = (Asteroid) fixtureB.getBody().getUserData();
                        asteroid.explode();
                        player.getDamaged(asteroid.getDamage());
                        break;
                }
                
            }
            else {
                Player player = (Player) fixtureB.getBody().getUserData();
                
                switch(categoryBitsA) {
                    case GM.GROUND_CATEGORY_BITS:
                        player.hitGround();
                        break;
                    case GM.ASTEROID_CATEGORY_BITS:
                        Asteroid asteroid = (Asteroid) fixtureA.getBody().getUserData();
                        asteroid.explode();
                        player.getDamaged(asteroid.getDamage());
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
