package com.uecepi.emarrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.uecepi.emarrow.network.game.player_action.PlayerDamagedPacket;
import com.uecepi.emarrow.network.game.player_action.ProjectileCollisionPacket;

public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Character self = GameEngine.getInstance().getSelfPlayer().getCharacter();

        if (isFixtureCombinationOneSens(fixtureA, fixtureB, "Ground", "GroundHitBox")
                || isFixtureCombinationOneSens(fixtureA, fixtureB, "GroundHitBox", "Character")) {
            self.setJumpLeft(2);
            self.setDashLeft(1);
            self.setGrounded(true);
        } else {
            self.setGrounded(false);
        }
        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            if (isFixtureCombination(fixtureA, fixtureB, "Character", "Projectile")) {
                Character character = getCharacterByFixture(fixtureA, fixtureB);
                Projectile projectile = getProjectileByFixture(fixtureA, fixtureB);

                if (!character.isAlive()) {
                    destroyProjectile(projectile);
                    return;
                }

                if (projectile.getShooter().equals(self) && character != self) {
                    GameEngine.getInstance().getSelfClient().sendTCP(new ProjectileCollisionPacket(projectile.getUuid()));
                    GameEngine.getInstance().getSelfClient().sendTCP(
                            new PlayerDamagedPacket(character.getInfo().getUuid(), projectile.getDamage()));
                    destroyProjectile(projectile);
                }
            } else if (isFixtureCombination(fixtureA, fixtureB, "Ground", "Projectile")) {
                destroyProjectile(getProjectileByFixture(fixtureA, fixtureB));
            }
        }
    }

    private void destroyProjectile(Projectile projectile) {
        GameEngine engine = GameEngine.getInstance();
        engine.removeProjectile(projectile);
        engine.killBody(projectile.getBody());
    }

    /**
     * @param fixA    first fixture
     * @param fixB    second fixture
     * @param typeOne expected type 1
     * @param typeTwo expected type 2
     * @return whether the fixtures are following the given combination with the order
     * Example: {@code isFixture(A, B, "Ground", "Projectile")} returns
     * A is Ground AND B is Projectile
     */
    private boolean isFixtureCombinationOneSens(Fixture fixA, Fixture fixB, String typeOne, String typeTwo) {
        return fixA.getUserData().toString().equals(typeOne) && fixB.getUserData().toString().equals(typeTwo);
    }

    /**
     * @param fixA    first fixture
     * @param fixB    second fixture
     * @param typeOne expected type 1
     * @param typeTwo expected type 2
     * @return whether the fixtures are following the given combinaison independently of the order
     * Example: {@code isFixture(A, B, "Ground", "Projectile")} returns
     * (A is Ground AND B is Projectile) OR (A is Projectile AND B is Ground)
     */
    private boolean isFixtureCombination(Fixture fixA, Fixture fixB, String typeOne, String typeTwo) {
        return isFixtureCombinationOneSens(fixA, fixB, typeOne, typeTwo)
                || isFixtureCombinationOneSens(fixA, fixB, typeTwo, typeOne);
    }

    /**
     * Casts fixtureA's or fixtureB's user data into Projectile
     *
     * @param fixtureA first fixture to try
     * @param fixtureB second fixture to try
     * @return the Projectile object or null
     */
    private Projectile getProjectileByFixture(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData().equals("Projectile")) {
            return (Projectile) fixtureA.getBody().getUserData();
        } else if (fixtureB.getUserData().equals("Projectile")) {
            return (Projectile) fixtureB.getBody().getUserData();
        }
        Gdx.app.error("collision_listener", "Cannot cast the fixture into a projectile!");
        return null;
    }

    /**
     * Casts fixtureA's or fixtureB's user data into Character
     *
     * @param fixtureA first fixture to try
     * @param fixtureB second fixture to try
     * @return the Character object or null
     */
    private Character getCharacterByFixture(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData().equals("Character")) {
            return ((Character) fixtureA.getBody().getUserData());
        } else if (fixtureB.getUserData().equals("Character")) {
            return (Character) fixtureB.getBody().getUserData();
        }
        Gdx.app.error("collision_listener", "Cannot cast the fixture into a character!");
        return null;
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