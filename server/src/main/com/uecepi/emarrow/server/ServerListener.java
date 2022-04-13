package com.uecepi.emarrow.server;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.SkinIndexPacket;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;
import com.uecepi.emarrow.networking.game.actions.ForceAppliedPacket;

import java.util.List;

public class ServerListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (object instanceof PlayerDataPacket) {
            PlayerDataPacket packet = (PlayerDataPacket) object;
            ConnectionManager.getInstance().addConnection(connection, packet.getUUID(), packet.getNickname());
            ConnectionManager.getInstance().sendTCPToOthers(connection, object);
            List<ConnectedPlayer> players = ConnectionManager.getInstance().getPlayers();
            connection.sendTCP(new SkinIndexPacket(players.size()));
            players.stream().filter(player -> player.getConnection() != connection)
                    .forEach(player -> connection.sendTCP(
                            new PlayerDataPacket(player.getUuid().toString(), player.getNickname())));
        } else if (object instanceof ForceAppliedPacket) {
            ConnectionManager.getInstance().sendTCPToOthers(connection, object);
        }
    }


    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.getPlayerByConnection(connection).ifPresent(connectionManager.getPlayers()::remove);
    }
}
