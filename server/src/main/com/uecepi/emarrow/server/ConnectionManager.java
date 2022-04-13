package com.uecepi.emarrow.server;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConnectionManager {

    private final static ConnectionManager instance = new ConnectionManager();
    private final List<ConnectedPlayer> players;


    public ConnectionManager() {
        players = new ArrayList<>();
    }

    public static ConnectionManager getInstance() {
        return instance;
    }

    public void addConnection(Connection client, UUID uuid, String nickname) {
        players.add(new ConnectedPlayer(client, uuid, nickname));
    }

    public void sendTCPToOthers(Connection from, Object object) {
        players.stream().map(ConnectedPlayer::getConnection).filter(connection -> connection != from)
                .forEach(connection -> connection.sendTCP(object));
    }

    public List<ConnectedPlayer> getPlayers(){
        return players;
    }

    public Optional<ConnectedPlayer> getPlayerByConnection(Connection cnct){
        return players.stream().filter(player -> player.getConnection() == cnct).findAny();
    }
}
