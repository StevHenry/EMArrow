package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Connection;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConnectionManager {

    private final static ConnectionManager instance = new ConnectionManager();
    private final List<ConnectedPlayer> players;

    public ConnectionManager() {
        players = new ArrayList<>();
    }

    public static ConnectionManager getInstance() {
        return instance;
    }

    /**
     * Adds a Player to the
     * @param client connection
     * @param uuid unique identifier
     * @param nickname name
     */
    public void addConnection(Connection client, UUID uuid, String nickname, int skinId) {
        players.add(new ConnectedPlayer(client, uuid, nickname, skinId));
    }

    /**
     * Sends a packet to all players different from the connection given
     * @param from initial sender where packet shouldn't be sent
     * @param object packet to send
     */
    public void sendTCPToOthers(Connection from, Object object) {
        getOtherConnections(from).forEach(player -> player.getConnection().sendTCP(object));
    }

    public List<ConnectedPlayer> getPlayers(){
        return players;
    }

    /**
     * @param cnct Connection linked to the ConnectedPlayer
     * @return an Optional of ConnectedPlayer which is supposed to be linked to the given connection
     */
    public Optional<ConnectedPlayer> getPlayerByConnection(Connection cnct){
        return players.stream().filter(player -> player.getConnection() == cnct).findAny();
    }

    /**
     * @param connection Connection to exclude
     * @return A list of {@link ConnectedPlayer} without the player linked to the specified connection
     */
    public List<ConnectedPlayer> getOtherConnections(Connection connection){
        return players.stream().filter(player -> player.getConnection() != connection).collect(Collectors.toList());
    }
}
