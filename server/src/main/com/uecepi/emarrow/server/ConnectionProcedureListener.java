package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.network.GameState;
import com.uecepi.emarrow.network.account.PlayerDataPacket;
import com.uecepi.emarrow.network.game.update.ConnectionResultPacket;
import com.uecepi.emarrow.network.game.update.PlayerDisconnectedPacket;
import com.uecepi.emarrow.network.game.update.PlayerJoinedPacket;
import com.uecepi.emarrow.server.game.ConnectedPlayer;
import com.uecepi.emarrow.server.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ConnectionProcedureListener extends Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionProcedureListener.class);

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (object instanceof PlayerDataPacket) {
            onReceivePlayerDataPacket(connection, (PlayerDataPacket) object);
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
        Game game = GameServer.getInstance().getGame();
        ConnectionResultPacket conPacket;
        if (game.getState() != GameState.WAITING) {
            conPacket = new ConnectionResultPacket(ConnectionResultPacket.ResultMeaning.GAME_STARTED);
        } else if (game.isFull()) {
            conPacket = new ConnectionResultPacket(ConnectionResultPacket.ResultMeaning.GAME_FULL);
        } else if (game.getPlayers().stream().anyMatch(pl -> pl.getUuid().equals(packet.getUUID()))) {
            conPacket = new ConnectionResultPacket(ConnectionResultPacket.ResultMeaning.ALREADY_CONNECTED);
        } else {
            conPacket = new ConnectionResultPacket(ConnectionResultPacket.ResultMeaning.ACCEPTED);
        }
        LOGGER.debug("New connection! Result={}", conPacket.getResult().name());
        connection.sendTCP(conPacket);
        if (conPacket.getResult() != ConnectionResultPacket.ResultMeaning.ACCEPTED) {
            connection.close();
            return;
        }

        ConnectedPlayer new_player = game.addPlayer(connection, packet.getUUID(), packet.getNickname());
        game.sendTCPToAllPlayer(new PlayerJoinedPacket(new_player.getUuid(), new_player.getNickname(), new_player.getSkinId()));
        for (ConnectedPlayer player : game.getPlayers().subList(0, game.getPlayers().size() - 1)) {
            connection.sendTCP(new PlayerJoinedPacket(player.getUuid(), player.getNickname(), player.getSkinId()));
        }
        LOGGER.info("Player {} joined!", packet.getNickname());


        if (game.isFull()) {
            game.setState(GameState.PREPARING);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        Game game = GameServer.getInstance().getGame();
        Optional<ConnectedPlayer> optPlayer = game.getPlayerByConnection(connection);
        if (optPlayer.isPresent()) {
            ConnectedPlayer player = optPlayer.get();
            //game.removePlayer(player);
            game.sendTCPToAllPlayer(new PlayerDisconnectedPacket(player.getUuid()));
        }
        if (game.getPlayers().stream().filter(pl -> !pl.getServerCharacter().isDead()).count() <= 1
                && !game.getState().equals(GameState.END)) {
            game.setState(GameState.END);
        }
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }
}
