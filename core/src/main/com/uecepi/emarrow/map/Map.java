package com.uecepi.emarrow.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Map {

    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private final World world;

    public Map(String mapName) {
        this.world = new World(new Vector2(0, -150), true);
        create(mapName);
        createGround();
    }

    private void create(String mapName) {
        camera = new OrthographicCamera();

        camera.zoom = 0.51f;
        camera.setToOrtho(false, 1740, 950);
        camera.update();

        tiledMap = new TmxMapLoader().load("maps/" + mapName + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void createGround() {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(2);  // assuming the layer at index on contains tiles
        for (int i = 0; i < layer.getWidth(); i++) {
            for (int j = 0; j < layer.getHeight(); j++) {
                if (layer.getCell(i, j) != null) {
                    // Create our body definition
                    BodyDef groundBodyDef = new BodyDef();
                    // Set its world position
                    groundBodyDef.position.set(i * layer.getTileWidth() + layer.getTileWidth() * .5f, j * layer.getTileHeight() + layer.getTileHeight() * .5f);
                    Body groundBody = world.createBody(groundBodyDef);

                    // Create a polygon shape
                    PolygonShape groundBox = new PolygonShape();
                    // (setAsBox takes half-width and half-height as arguments)
                    groundBox.setAsBox(layer.getTileWidth() / 2f, layer.getTileHeight() / 2f);
                    FixtureDef groundFixture = new FixtureDef();
                    groundFixture.shape = groundBox;
                    // Create a fixture from our polygon shape and add it to our ground body
                    groundBody.createFixture(groundFixture).setUserData("Ground");
                    // Clean up
                    groundBox.dispose();
                }
            }
        }

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(187, 230);

        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        FixtureDef groundFixture = new FixtureDef();

        groundBox.setAsBox(72f, 10f, new Vector2(-67f, 20f), 0f);
        groundFixture.shape = groundBox;

        groundBody.createFixture(groundFixture).setUserData("Ground");

        groundBox.setAsBox(72f, 10f, new Vector2(141f, 20f), 0f);
        groundFixture.shape = groundBox;

        groundBody.createFixture(groundFixture).setUserData("Ground");
    }

    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public World getWorld() {
        return world;
    }

}
