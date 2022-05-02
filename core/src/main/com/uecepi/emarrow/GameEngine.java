package com.uecepi.emarrow;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.uecepi.emarrow.map.Map;
import com.uecepi.emarrow.network.GameState;
import com.uecepi.emarrow.networking.GameClient;

import java.util.*;
import java.util.stream.Collectors;

public class GameEngine {

    private static final GameEngine instance = new GameEngine();

    private final InputManager inputManager;
    private final List<Body> deadBodies;
    private final List<PlayerInfo> players;
    private final List<Projectile> projectiles;
    private final GameClient gameClient;
    private final Map map;
    private GameState state;
    private PlayerInfo self;

    public GameEngine() {
        this.inputManager = new InputManager();
        this.map = new Map("map1");
        this.projectiles = new ArrayList<>();
        this.deadBodies = new ArrayList<>();
        this.players = new ArrayList<>();
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
            //player.getClientConnection().close();
            map.getWorld().destroyBody(player.getCharacter().getBody());
        }
        self.resetCharacter();
        deadBodies.clear();
        projectiles.clear();
        players.clear();
        players.add(self);
    }

    public Map getMap() {
        return map;
    }

    public World getWorld() {
        return map.getWorld();
    }

    public void addPlayer(PlayerInfo newPlayer) {
        players.add(newPlayer);
    }

    public void removePlayer(PlayerInfo player) {
        deadBodies.add(player.getCharacter().getBody());
        players.remove(player);
    }

    public List<Character> getCharacters() {
        return players.stream().map(PlayerInfo::getCharacter).collect(Collectors.toList());
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * Queues the kill of the specified body
     *
     * @param body Body to kill
     */
    public void killBody(Body body) {
        deadBodies.add(body);
    }

    /**
     * Sets inactive the bodies from the {@link #deadBodies}
     */
    public void disableBodies() {
        deadBodies.forEach(body -> body.setActive(false));
    }

    /**
     * Empties the {@link #deadBodies}'s list
     */
    public void clearDeadBodies() {
        deadBodies.clear();
    }

    /**
     * Adds a projectile to the {@link #projectiles} list
     *
     * @param projectile projectile to add
     */
    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }


    /**
     * Removes a projectile to the {@link #projectiles} list
     *
     * @param projectile projectile to remove
     */
    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    /**
     * @return An unmodifiable list of the projectiles
     */
    public List<Projectile> getProjectiles() {
        return Collections.unmodifiableList(projectiles);
    }


    /**
     * @param entityUUID UUID of the projectile
     * @return an Optional of the {@link Projectile} linked to the specified UUID
     */
    public Optional<Projectile> getProjectileByUUID(UUID entityUUID) {
        return projectiles.stream().filter(projectile -> projectile.getUuid().equals(entityUUID)).findFirst();
    }

    /**
     * @return the self player
     */
    public PlayerInfo getSelfPlayer() {
        return self;
    }

    /**
     * Sets the self player
     *
     * @param info self player info
     */
    public void setSelfPlayer(PlayerInfo info) {
        this.self = info;
    }

    /**
     * @param uuid UUID of the Player
     * @return An Optional of {@link PlayerInfo} linked to the specified UUID
     */
    public Optional<PlayerInfo> getPlayerByUUID(UUID uuid) {
        return players.stream().filter(playerInfo -> playerInfo.getUuid().equals(uuid)).findFirst();
    }

    /**
     * @return the {@link GameClient} of the self player
     */
    public GameClient getSelfClient() {
        return gameClient;
    }

    /**
     * @return the unmodifiable {@link #players} attribute
     */
    public List<PlayerInfo> getPlayers(){
        return Collections.unmodifiableList(players);
    }

    /**
     * Sets the state of the current game
     *
     * @param newState new state
     */
    public void setState(GameState newState) {
        this.state = newState;
    }

    /**
     * @param compared compared state
     * @return whether the current state matches the specified one
     */
    public boolean isState(GameState compared) {
        return state == compared;
    }
}
