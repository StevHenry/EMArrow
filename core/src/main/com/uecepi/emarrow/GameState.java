package com.uecepi.emarrow;

public enum GameState {

    WAITING(),
    PREPARING(),
    PLAYING(),
    END();

    private static GameState current = WAITING;

    public static boolean isState(GameState state) {
        return current == state;
    }

    public static GameState getState() {
        return current;
    }

    public static void setState(GameState newState) {
        current = newState;
    }
}
