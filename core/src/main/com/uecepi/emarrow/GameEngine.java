package com.uecepi.emarrow;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.uecepi.emarrow.map.Map;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;
import com.uecepi.emarrow.networking.game.GameClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameEngine {

    private static final GameEngine instance = new GameEngine();
    private static final Vector2[] initialPositions = {new Vector2(25, 110), new Vector2(225, 150)
            , new Vector2(425, 110), new Vector2(50, 100f)};
    private final InputManager inputManager;
    private final ArrayList<Body> deadBodies;
    private final List<PlayerInfo> players;
    private final GameClient gameClient;
    private final Map map;
    private PlayerInfo self;

    public GameEngine() {
        this.inputManager = new InputManager();
        this.deadBodies = new ArrayList<>();
        this.players = new ArrayList<>();
        this.map = new Map("map1");
        this.gameClient = new GameClient();
    }

    public static GameEngine getInstance() {
        return instance;
    }

    /**
     * Creates the self player
     */
    public void startGame() {
        for (PlayerInfo player : players) {
            player.getClientConnection().close();
            map.getWorld().destroyBody(player.getCharacter().getBody());
        }
        deadBodies.clear();
        players.clear();
        players.add(self);
        //players.add(new PlayerInfo(new Character(), UUID.randomUUID(), "second"));
    }

    /**
     * @return whether the round is finished or not
     */
    public boolean isRoundFinished() {
        int playersAlive = 0;
        for (PlayerInfo player : players) {
            if (player.getCharacter().getLife() > 0) playersAlive++;
        }
        return GameState.isState(GameState.PLAYING) && playersAlive <= 1;
    }

    public Map getMap() {
        return map;
    }

    public World getWorld() {
        return map.getWorld();
    }

    public Optional<PlayerInfo> seekWinner() {
        if (players.stream().filter(pl -> pl.getCharacter().getLife() > 0).count() == 1) {
            return players.stream().filter(pl -> pl.getCharacter().getLife() > 0).findFirst();
        } else {
            return Optional.empty();
        }
    }

    public void addPlayer(PlayerInfo newPlayer) {
        players.add(newPlayer);
    }

    public void removePlayer(PlayerInfo player) {
        deadBodies.add(player.getCharacter().getBody());
        players.remove(player);
    }

    public int getPlayersCount() {
        return players.size();
    }

    public List<Character> getCharacters() {
        return players.stream().map(PlayerInfo::getCharacter).collect(Collectors.toList());
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ArrayList<Body> getDeadBodies() {
        return deadBodies;
    }

    public PlayerInfo getSelfPlayer() {
        return self;
    }

    public void setSelfPlayer(PlayerInfo info) {
        this.self = info;
    }

    public Optional<PlayerInfo> getPlayerByUUID(UUID uuid) {
        return players.stream().filter(playerInfo -> playerInfo.getUuid().equals(uuid)).findFirst();
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public void gameClientProcedure() {
        gameClient.sendTCP(new PlayerDataPacket(self.getUuid(), self.getName()));
    }

    public void preparingProcedure() {
        for (int i = 0; i < players.size(); i++) {
            if (i < initialPositions.length) {
                int index = players.get(i).getCharacter().getAnimator().getCharacterNumber() - 1;
                players.get(i).getCharacter().setTransform(initialPositions[index].x, initialPositions[index].y, 0);
            } else {
                int last = initialPositions.length - 1;
                players.get(i).getCharacter().setTransform(initialPositions[last].x, initialPositions[last].y, 0);
            }
        }
        GameState.setState(GameState.PLAYING);
    }
}
