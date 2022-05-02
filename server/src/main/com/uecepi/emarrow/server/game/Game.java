package com.uecepi.emarrow.server.game;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.uecepi.emarrow.network.GameState;
import com.uecepi.emarrow.network.game.update.GameStateChangedPacket;
import com.uecepi.emarrow.network.game.update.SubtitleChangedPacket;
import com.uecepi.emarrow.network.game.update.TitleChangedPacket;
import com.uecepi.emarrow.server.GameServer;

import java.util.*;
import java.util.stream.Collectors;

import static com.uecepi.emarrow.network.Constants.*;

public class Game {

    private final static Random RANDOM = new Random();
    private final List<ConnectedPlayer> players;
    private final int maxPlayersCount;
    private GameState state = GameState.WAITING;

    /**
     * @param maxPlayersCount Players count needed to start the game
     */
    public Game(int maxPlayersCount) {
        this.players = new ArrayList<>();
        this.maxPlayersCount = maxPlayersCount;
    }

    /**
     * @return the next available skinId
     */
    private int getNextSkinId() {
        List<Integer> unavailable = players.stream().map(ConnectedPlayer::getSkinId).collect(Collectors.toList());
        for (int i = 1; i <= players.size(); i++) {
            if (!unavailable.contains(i))
                return i;
        }
        return players.size() + 1;
    }

    /**
     * Adds a Player to the players' list
     *
     * @param connection client connection
     * @param uuid       unique identifier
     * @param nickname   name
     * @return the created {@link ConnectedPlayer}
     */
    public ConnectedPlayer addPlayer(Connection connection, UUID uuid, String nickname) {
        ConnectedPlayer player = new ConnectedPlayer(connection, uuid, nickname, getNextSkinId());
        players.add(player);
        return player;
    }

    /**
     * Removes a player from the players' list
     *
     * @param player player to remove
     */
    public void removePlayer(ConnectedPlayer player) {
        players.remove(player);
    }

    /**
     * Gets a ConnectedPlayer from the players' list
     *
     * @param connection client connection
     */
    public Optional<ConnectedPlayer> getPlayerByConnection(Connection connection) {
        return players.stream().filter(connectedPlayer -> connectedPlayer.getConnection() == connection).findFirst();
    }

    /**
     * Gets a ConnectedPlayer from the players' list
     *
     * @param uuid unique identifier of the player
     */
    public Optional<ConnectedPlayer> getPlayerByUUID(UUID uuid) {
        return players.stream().filter(connectedPlayer -> connectedPlayer.getUuid().equals(uuid)).findFirst();
    }

    public List<ConnectedPlayer> getPlayers() {
        return players;
    }

    /**
     * Sends to all connected players a packet
     *
     * @param packet object to send
     */
    public void sendTCPToAllPlayer(Object packet) {
        players.forEach(player -> player.getConnection().sendTCP(packet));
    }

    /**
     * Sends to all connected players except the specified connected a packet
     *
     * @param excepted connection not to send the packet
     * @param packet   object to send
     */
    public void sendTCPToEveryoneElse(Connection excepted, Object packet) {
        players.stream().filter(player -> player.getConnection() != excepted)
                .forEach(player -> player.getConnection().sendTCP(packet));
    }

    /**
     * @return the current {@link GameState} of the Game
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the new GameState
     * Sends the update to each player
     *
     * @param newState new state
     */
    public void setState(GameState newState) {
        this.state = newState;
        sendTCPToAllPlayer(new GameStateChangedPacket(state));
        switch (newState) {
            case PREPARING:
                List<Vector2> inUse = new ArrayList<>();
                for (ConnectedPlayer connectedPlayer : players) {
                    Vector2 chosen;
                    do {
                        if (inUse.size() == INITIAL_POSITIONS.length) {
                            chosen = FALLBACK_POSITION;
                        } else {
                            chosen = INITIAL_POSITIONS[RANDOM.nextInt(INITIAL_POSITIONS.length)];
                        }
                    } while (inUse.contains(chosen) && inUse.size() != INITIAL_POSITIONS.length);
                    inUse.add(chosen);
                    connectedPlayer.setPosition(chosen.x, chosen.y);
                }
                new Thread(() -> {
                    sendTCPToAllPlayer(new TitleChangedPacket("Game starting in:"));
                    for (int i = PREPARING_STATE_DURATION; i > 0; i--) {
                        sendTCPToAllPlayer(new SubtitleChangedPacket("%d".formatted(i)));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    sendTCPToAllPlayer(new TitleChangedPacket(null));
                    sendTCPToAllPlayer(new SubtitleChangedPacket(null));
                    setState(GameState.PLAYING);
                }).start();
                break;
            case END:
                GameServer.getInstance().getGame().getPlayers().stream().filter(pl -> !pl.getServerCharacter().isDead())
                        .forEach(pl -> pl.getConnection().sendTCP(new TitleChangedPacket("You won!")));
                for (int i = TIME_BEFORE_DISCONNECTING; i > 0; i--) {
                    sendTCPToAllPlayer(new SubtitleChangedPacket("Game finished ! Disconnecting in %ds".formatted(i)));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                players.stream().filter(pl -> pl.getConnection().isConnected()).forEach(pl -> pl.getConnection().close());
                GameServer.getInstance().nextGame();
            default:
                break;
        }

    }

    public boolean isFull() {
        return players.size() == maxPlayersCount;
    }

    public int getMaxPlayersCount() {
        return maxPlayersCount;
    }
}
