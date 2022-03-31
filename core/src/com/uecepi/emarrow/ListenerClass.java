package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


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
        if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Player")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Projectile")) {
            Character character = (Character) fixtureA.getBody().getUserData();
            Projectile projectile = (Projectile) fixtureB.getBody().getUserData();

            if (!character.getProjectilesShooted().contains(projectile)) {
                character.getHealthBar().setValue(character.getHealthBar().getValue() - 10f);
                projectile.getShooter().getProjectilesShooted().remove(projectile);
                //GameEngine.getInstance().getWorld().destroyBody(projectile.getBody());
            }
        } else if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Projectile")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Player")) {
            Character character = (Character) fixtureB.getBody().getUserData();
            Projectile projectile = (Projectile) fixtureA.getBody().getUserData();

            if (!character.getProjectilesShooted().contains(projectile)) {
                character.getHealthBar().setValue(character.getHealthBar().getValue() - 10f);
                projectile.getShooter().getProjectilesShooted().remove(projectile);
                //GameEngine.getInstance().getWorld().destroyBody(projectile.getBody());
            }
        } else if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Projectile")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Ground")) {
            Projectile projectile = (Projectile) fixtureA.getBody().getUserData();
            projectile.getShooter().getProjectilesShooted().remove(projectile);
            //GameEngine.getInstance().getWorld().destroyBody(projectile.getBody());

        } else if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Ground")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Projectile")) {
            Projectile projectile = (Projectile) fixtureB.getBody().getUserData();
            projectile.getShooter().getProjectilesShooted().remove(projectile);
            //GameEngine.getInstance().getWorld().destroyBody(projectile.getBody());
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