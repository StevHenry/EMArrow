package com.uecepi.emarrow.network.game.update;

import com.uecepi.emarrow.network.GameState;

public class GameStateChangedPacket {

    private String newState;

    public GameStateChangedPacket() {
    }

    public GameStateChangedPacket(GameState newState) {
        this.newState = newState.name();
    }

    public GameState getNewGameState() {
        return GameState.valueOf(newState);
    }
}
