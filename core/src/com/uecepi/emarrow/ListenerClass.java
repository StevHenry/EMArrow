package com.uecepi.emarrow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


import com.badlogic.gdx.physics.box2d.*;
import com.uecepi.emarrow.audio.MusicManager;

import javax.swing.*;

public class ListenerClass implements ContactListener {

    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() == "Ground"){
            if(fixtureB.getUserData() == "GroundHitBox") {
                GameEngine.getInstance().getPlayers().get(0).setJumpLeft(2);
                GameEngine.getInstance().getPlayers().get(0).setDashLeft(1);
                GameEngine.getInstance().getPlayers().get(0).setGrounded(true);
            }
        } else if (fixtureA.getUserData() == "GroundHitBox"){
            if(fixtureB.getUserData() == "Player") {
                GameEngine.getInstance().getPlayers().get(0).setJumpLeft(2);
                GameEngine.getInstance().getPlayers().get(0).setDashLeft(1);
                GameEngine.getInstance().getPlayers().get(0).setGrounded(true);
            }
        } else {
            GameEngine.getInstance().getPlayers().get(0).setGrounded(false);
        }

        if (fixtureA.getUserData() == "TPLEFT"){
            if(fixtureB.getUserData() == "Player") {
                System.out.println("TPLEFT");
                Body body = GameEngine.getInstance().getPlayers().get(0).getBody();
                //body.setTransform(435,body.getPosition().y,0);
            }
        } else if (fixtureA.getUserData() == "TPRIGHT"){
            if(fixtureB.getUserData() == "Player") {
                System.out.println("TPRIGHT");
                //GameEngine.getInstance().getPlayers().get(0).setPosition(-10f,GameEngine.getInstance().getPlayers().get(0).getBody().getPosition().y);
            }
        } else if (fixtureA.getUserData() == "TPUP"){
            if(fixtureB.getUserData() == "Player") {
                System.out.println("TPUP");
                //GameEngine.getInstance().getPlayers().get(0).setPosition(-10f,GameEngine.getInstance().getPlayers().get(0).getBody().getPosition().y);
            }
        } else if (fixtureA.getUserData() == "TPDOWN"){
            if(fixtureB.getUserData() == "Player") {
                System.out.println("TPDOWN");
                //GameEngine.getInstance().getPlayers().get(0).setPosition(-10f,GameEngine.getInstance().getPlayers().get(0).getBody().getPosition().y);
            }
        }

        if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Player")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Projectile")) {
            Character character = (Character) fixtureA.getBody().getUserData();
            Projectile projectile = (Projectile) fixtureB.getBody().getUserData();

            if (!projectile.getShooter().equals(character)) {
                MusicManager.playSE(MusicManager.TOUCHED_SE);
                character.getHealthBar().setValue(character.getHealthBar().getValue() - projectile.damage);
                if (character.getHealthBar().getValue()<=0)
                    character.die();
                projectile.getShooter().getProjectilesShooted().remove(projectile);
                GameEngine.getInstance().getDeadBodies().add(projectile.getBody());
            }
        } else if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Projectile")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Player")) {
            Character character = (Character) fixtureB.getBody().getUserData();
            Projectile projectile = (Projectile) fixtureA.getBody().getUserData();

            if (!projectile.getShooter().equals(character)) {
                MusicManager.playSE(MusicManager.TOUCHED_SE);

                character.getHealthBar().setValue(character.getHealthBar().getValue() - projectile.damage);
                if (character.getHealthBar().getValue()<=0)
                    character.die();
                projectile.getShooter().getProjectilesShooted().remove(projectile);
                GameEngine.getInstance().getDeadBodies().add(projectile.getBody());
                System.out.println("dead bodies" + GameEngine.getInstance().getDeadBodies());

            }
        } else if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Projectile")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Ground")) {
            Projectile projectile = (Projectile) fixtureA.getBody().getUserData();
            projectile.getShooter().getProjectilesShooted().remove(projectile);
            GameEngine.getInstance().getDeadBodies().add(projectile.getBody());

        } else if (fixtureA.getUserData() != null
                && fixtureA.getUserData().equals("Ground")
                && fixtureB.getUserData() != null
                && fixtureB.getUserData().equals("Projectile")) {
            Projectile projectile = (Projectile) fixtureB.getBody().getUserData();
            projectile.getShooter().getProjectilesShooted().remove(projectile);
            GameEngine.getInstance().getDeadBodies().add(projectile.getBody());
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