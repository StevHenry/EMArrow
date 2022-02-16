package com.uecepi.emarrow;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameEngine {
    private World world;

    public GameEngine() {
        this.world = new World(new Vector2(0, -10), true);
    }
}
