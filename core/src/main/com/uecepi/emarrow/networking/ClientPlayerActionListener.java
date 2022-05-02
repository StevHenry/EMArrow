package com.uecepi.emarrow.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.GameEngine;
import com.uecepi.emarrow.Projectile;
import com.uecepi.emarrow.audio.MusicManager;
import com.uecepi.emarrow.network.game.player_action.*;

public class ClientPlayerActionListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        GameEngine engine = GameEngine.getInstance();

        if (object instanceof TransformationPacket) {
            TransformationPacket packet = (TransformationPacket) object;
            Gdx.app.postRunnable(() -> engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl -> {
                pl.getCharacter().getBody().setTransform(packet.getX(), packet.getY(), packet.getAngle());
            }));
        } else if (object instanceof ForceAppliedPacket) {
            ForceAppliedPacket packet = (ForceAppliedPacket) object;
            engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl -> pl.getCharacter().getBody()
                    .applyForce(packet.getForce(), packet.getApplicationPoint(), packet.isWake()));

        } else if (object instanceof LinearImpulsePacket) {
            LinearImpulsePacket packet = (LinearImpulsePacket) object;
            engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl ->
                    pl.getCharacter().getBody().applyLinearImpulse(packet.getImpulse(), packet.getApplicationPoint(), packet.isWake()));

        } else if (object instanceof ForceToCenterPacket) {
            ForceToCenterPacket packet = (ForceToCenterPacket) object;
            engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl ->
                    pl.getCharacter().getBody().applyForceToCenter(packet.getForceX(), packet.getForceY(), packet.isWake()));

        } else if (object instanceof CharacterFlipPacket) {
            CharacterFlipPacket packet = (CharacterFlipPacket) object;
            engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl ->
                    pl.getCharacter().getAnimator().setFlippedToLeft(packet.isFlippedLeft()));

        } else if (object instanceof PlayerShootPacket) {
            PlayerShootPacket packet = (PlayerShootPacket) object;
            Gdx.app.postRunnable(() -> engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl ->
                    pl.getCharacter().shoot(new Projectile(pl.getCharacter(), packet.getDirection(), packet.getEntityUid()))));

        } else if (object instanceof ProjectileCollisionPacket) {
            ProjectileCollisionPacket packet = (ProjectileCollisionPacket) object;
            engine.getProjectileByUUID(packet.getEntityUid()).ifPresent(projectile -> {
                engine.removeProjectile(projectile);
                engine.killBody(projectile.getBody());
                MusicManager.playSE(MusicManager.TOUCHED_SE);
            });

        } else if (object instanceof PlayerDamagedPacket) {
            PlayerDamagedPacket packet = (PlayerDamagedPacket) object;
            engine.getPlayerByUUID(packet.getDamagedUid()).ifPresent(playerInfo ->
                    playerInfo.getCharacter().damage(packet.getDamage()));
        }
    }
}
