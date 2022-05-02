package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.network.GameState;
import com.uecepi.emarrow.network.game.PlayerAssignedAction;
import com.uecepi.emarrow.network.game.player_action.CharacterFlipPacket;
import com.uecepi.emarrow.network.game.player_action.PlayerDamagedPacket;
import com.uecepi.emarrow.network.game.player_action.ProjectileCollisionPacket;
import com.uecepi.emarrow.network.game.player_action.TransformationPacket;
import com.uecepi.emarrow.network.game.update.TitleChangedPacket;

public class PlayerActionListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        GameServer server = GameServer.getInstance();

        if (object instanceof PlayerAssignedAction || object instanceof ProjectileCollisionPacket) {
            server.getGame().sendTCPToEveryoneElse(connection, object);
            if (object instanceof TransformationPacket) {
                TransformationPacket packet = (TransformationPacket) object;
                server.getGame().getPlayerByConnection(connection).ifPresent(player ->
                        player.getServerCharacter().setPosition(packet.getX(), packet.getY()));

            } else if (object instanceof CharacterFlipPacket) {
                CharacterFlipPacket packet = (CharacterFlipPacket) object;
                server.getGame().getPlayerByConnection(connection).ifPresent(player ->
                        player.getServerCharacter().setLookingLeft(packet.isFlippedLeft()));

            }
        } else if (object instanceof PlayerDamagedPacket) {
            PlayerDamagedPacket packet = (PlayerDamagedPacket) object;
            server.getGame().getPlayerByUUID(packet.getDamagedUid()).ifPresent(player -> {
                player.getServerCharacter().damage(packet.getDamage());
                if (player.getServerCharacter().isDead()) {
                    player.getConnection().sendTCP(new TitleChangedPacket("You lost!"));

                    // End game detection
                    if (GameServer.getInstance().getGame().getPlayers().stream().filter(pl -> !pl.getServerCharacter().isDead()).count() <= 1) {

                        GameServer.getInstance().getGame().setState(GameState.END);
                    }
                }
            });
            server.getGame().sendTCPToAllPlayer(packet);
        }
    }
}

