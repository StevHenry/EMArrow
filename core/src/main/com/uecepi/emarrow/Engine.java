package com.uecepi.emarrow;

import com.badlogic.gdx.ApplicationListener;

public class Engine {

    private static Engine engine;

    private GameEngine gameEngine;

    public static Engine getEngine() {
        return engine;
    }

    public static void start(ApplicationListener app) {
        engine = new Engine();
        engine.setGameEngine(new GameEngine());
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}