package com.gasis.rts.ui.implementations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gasis.rts.ui.abstractions.BasicScreen;
import com.gasis.rts.resources.NotLoadedException;

import java.util.Map;

/**
 * Loading screen. Loads resources and switches to another screen
 */
public class LoadingScreen extends BasicScreen {

    // screen to switch to
    private BasicScreen screenToSwitch;

    // image to display while loading
    private String image;

    // image displayed while loading
    private Sprite sprite;

    // assets to load
    private Map<String, Class> assets;

    // tiled maps to load
    private String[] maps;

    // should the displayed image be unloaded when done loading
    private boolean unloadImage;

    /**
     * Default class constructor
     *
     * @param screenToSwitch screen to switch to when done loading
     * @param image image to display while loading
     * @param unloadImage should the image be unloaded once done
     * @param assets assets to load
     * @param maps tiled maps to load
     */
    public LoadingScreen(BasicScreen screenToSwitch, String image, boolean unloadImage, Map<String, Class> assets, String[] maps) {
        this.screenToSwitch = screenToSwitch;
        this.image = image;
        this.assets = assets;
        this.maps = maps;
        this.unloadImage = unloadImage;
    }

    /**
     * Called when the screen becomes the current screen
     */
    @Override
    public void show() {
        Texture texture;

        try {
            texture = resources.texture(image);
        } catch(NotLoadedException ex) {
            resources.load(image, Texture.class);
            resources.finishLoading();

            texture = resources.texture(image);
        }

        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        enqueueAssets();
    }

    /**
     * Adds assets to the loading queue
     */
    private void enqueueAssets() {
        if (assets != null) {
            loadAssets(assets);
        }

        if (maps != null) {
            loadMaps(maps);
        }
    }

    /**
     * Called when the screen should render itself
     *
     * @param delta time elapsed since last render
     * @param batch batch used to draw sprites to
     * @param cam world's camera
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera cam, float delta) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    /**
     * Called when the screen should update itself
     *
     * @param delta time elapsed since last update
     */
    @Override
    public void update(float delta) {
        if (resources.update() || (assets == null && maps == null)) {
            if (unloadImage) {
                resources.unload(image);
            }

            switchScreen();
        }
    }

    /**
     * Loads assets
     *
     * @param assets assets to load
     */
    private void loadAssets(Map<String, Class> assets) {
        for (Map.Entry<String, Class> asset: assets.entrySet()) {
            resources.load(asset.getKey(), asset.getValue());
        }
    }

    /**
     * Loads tiled maps
     *
     * @param maps maps to load
     */
    private void loadMaps(String[] maps) {
        for (String map: maps) {
            resources.loadMap(map);
        }
    }

    /**
     * Switches to another screen
     */
    private void switchScreen() {
        screenSwitcher.showScreen(screenToSwitch);
    }
}

