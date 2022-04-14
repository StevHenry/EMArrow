package com.uecepi.emarrow.server;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.SkinIndexPacket;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;
import com.uecepi.emarrow.networking.game.PlayerDisconnectedPacket;
import com.uecepi.emarrow.networking.game.actions.PlayerAssignedAction;

import java.util.List;
import java.util.Optional;

public class ServerListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (object instanceof PlayerDataPacket) {
            onReceivePlayerDataPacket(connection, (PlayerDataPacket) object);
        } else if (object instanceof SkinIndexPacket || object instanceof PlayerAssignedAction) {
            ConnectionManager.getInstance().sendTCPToOthers(connection, object);
        }
    }

    /**
     * Action performed when a PlayerDataPacket is received
     * Adds the ConnectedPlayer to the list
     * Sends it its skin value
     * Sends to already connected players the data
     *
     * @param connection sender connection
     * @param packet     data
     */
    private void onReceivePlayerDataPacket(Connection connection, PlayerDataPacket packet) {
        ConnectionManager manager = ConnectionManager.getInstance();
        List<ConnectedPlayer> players = manager.getPlayers();
        int skinId = players.size() + 1;
        manager.addConnection(connection, packet.getUUID(), packet.getNickname(), skinId);

        manager.sendTCPToOthers(connection, packet);
        manager.getPlayers().forEach(player ->
                player.getConnection().sendTCP(new SkinIndexPacket(packet.getUUID(), skinId)));

        manager.getOtherConnections(connection).forEach(player -> {
            connection.sendTCP(new PlayerDataPacket(player.getUuid(), player.getNickname()));
            connection.sendTCP(new SkinIndexPacket(player.getUuid(), player.getSkinId()));
        });
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        Optional<ConnectedPlayer> player = connectionManager.getPlayerByConnection(connection);
        if (player.isPresent()) {
            connectionManager.getOtherConnections(connection)
                    .forEach(connectedPlayer -> connectedPlayer.getConnection().sendTCP(
                            new PlayerDisconnectedPacket(player.get().getUuid())));
            connectionManager.getPlayers().remove(player.get());
        }
    }
}
