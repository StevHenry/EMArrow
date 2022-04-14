package com.uecepi.emarrow.networking.game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.*;
import com.uecepi.emarrow.networking.SkinIndexPacket;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;
import com.uecepi.emarrow.networking.game.actions.*;

public class GameListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        GameEngine engine = GameEngine.getInstance();
        if (object instanceof PlayerDataPacket) {
            PlayerDataPacket packet = (PlayerDataPacket) object;
            Gdx.app.postRunnable(() -> {
                engine.addPlayer(new PlayerInfo(new Character(), packet.getUUID(), packet.getNickname()));
                if (engine.getPlayersCount() >= 3) {
                    GameState.setState(GameState.PREPARING);
                    GameEngine.getInstance().preparingProcedure();
                }
            });
        } else if (object instanceof SkinIndexPacket) {
            SkinIndexPacket packet = (SkinIndexPacket) object;
            Gdx.app.postRunnable(() ->
                    engine.getPlayerByUUID(packet.getUUID()).ifPresent(playerInfo ->
                            playerInfo.getCharacter().getAnimator().setCharacterNumber(packet.getSkinIndex())));
        } else if (object instanceof PlayerAssignedAction) {
            actionPacket(object);
        }
    }

    private void actionPacket(Object object) {
        GameEngine engine = GameEngine.getInstance();
        if (object instanceof ForceAppliedPacket) {
            ForceAppliedPacket packet = (ForceAppliedPacket) object;
            engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl -> pl.getCharacter().getBody()
                    .applyForce(packet.getForce(), packet.getApplicationPoint(), packet.isWake()));

        } else if (object instanceof TransformationPacket) {
            TransformationPacket packet = (TransformationPacket) object;
            Gdx.app.postRunnable(() -> Gdx.app.postRunnable(() -> engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(pl ->
                    pl.getCharacter().getBody().setTransform(packet.getX(), packet.getY(), packet.getAngle()))));

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
                    pl.getCharacter().shoot(new Projectile(pl.getCharacter(), packet.getDirection()))));

        } else if (object instanceof PlayerDisconnectedPacket) {
            PlayerDisconnectedPacket packet = (PlayerDisconnectedPacket) object;
            Gdx.app.postRunnable(() -> engine.removePlayer(engine.getPlayerByUUID(packet.getPlayerUid()).orElse(null)));
        }
    }
}
