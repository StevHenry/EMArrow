package com.uecepi.emarrow;

import com.badlogic.gdx.physics.box2d.*;

public class ListenerClass implements ContactListener {

    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() == "Ground"){
            if(fixtureB.getUserData() == "GroundHitBox") {
                GameEngine.getInstance().getPlayers().get(0).setJumpLeft(2);
            }
        }
        if(fixtureA.getUserData() == "GroundHitBox"){
            if(fixtureB.getUserData() == "Player") {
                GameEngine.getInstance().getPlayers().get(0).setJumpLeft(2);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
