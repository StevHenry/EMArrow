package com.uecepi.emarrow;

import com.badlogic.gdx.physics.box2d.World;
import com.uecepi.emarrow.map.Map;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;
import com.uecepi.emarrow.networking.game.GameClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameEngine {

    private static final GameEngine instance = new GameEngine();
    private final InputManager inputManager;
    private final ArrayList<Projectile> deadBodies;
    private final List<PlayerInfo> players;
    private final GameClient gameClient;
    private PlayerInfo self;

    private Map map;

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
        //players.add(new PlayerInfo(new Character(2), UUID.randomUUID(), "second"));
    }

    /**
     * @return whether the round is finished or not
     */
    public boolean isRoundFinished() {
        int playersAlive = 0;
        for (PlayerInfo player : players) {
            if (player.getCharacter().getLife() > 0) playersAlive++;
        }
        return players.size() > 1 && playersAlive <= 1;
    }

    public Map getMap() {
        return map;
    }

    public World getWorld() {
        return map.getWorld();
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public void addPlayer(PlayerInfo newPlayer) {
        players.add(newPlayer);
    }

    public List<Character> getCharacters() {
        return players.stream().map(PlayerInfo::getCharacter).collect(Collectors.toList());
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ArrayList<Projectile> getDeadBodies() {
        return deadBodies;
    }

    public void setSelfPlayer(PlayerInfo info) {
        this.self = info;
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public void gameClientProcedure() {
        gameClient.sendTCP(new PlayerDataPacket(self.getUuid().toString(), self.getName()));
    }
}
