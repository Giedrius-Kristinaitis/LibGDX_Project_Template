package com.template.game.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Map;

/**
 * Manages game's resources (Textures and so on...)
 */
public class Resources {

    // asset manager to load, retrieve and dispose of assets
    private AssetManager assetManager;

    /**
     * Default class constructor
     */
    public Resources() {
        assetManager = new AssetManager();
    }

    /**
     * Updates the asset manager. Must be called inside game loop to load assets
     *
     * @return true if finished loading, false otherwise
     */
    public boolean update() {
        return assetManager.update();
    }

    /**
     * Forces to load all queued assets. Blocks calling thread
     */
    public void finishLoading() {
        assetManager.finishLoading();
    }

    /**
     * Returns a number between 0 and 1 indicating loading percentage
     * @return progress
     */
    public float getProgress() {
        return assetManager.getProgress();
    }

    /**
     * Loads all specified assets
     * @param assets map containing asset file names and types
     */
    public void load(Map<String, Class> assets) {
        for (Map.Entry<String, Class> asset: assets.entrySet()) {
            load(asset.getKey(), asset.getValue());
        }
    }

    /**
     * Loads a single asset
     * @param name file name of the asset
     * @param type class type of the asset
     */
    public void load(String name, Class type) {
        assetManager.load(name, type);
    }

    /**
     * Loads a tiled map
     * @param name file name of the map
     */
    public void loadMap(String name) {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(name, TiledMap.class);
    }

    /**
     * Gets a loaded tiled map from the asset manager
     * @param name file name of the map
     * @return
     */
    public TiledMap map(String name) {
        if (!assetManager.isLoaded(name)) {
            throw new NotLoadedException("Map '" + name + "' is not loaded");
        }

        return assetManager.get(name, TiledMap.class);
    }

    /**
     * Gets a loaded texture from the asset manager
     * @param name file name of the texture
     * @return loaded texture
     */
    public Texture texture(String name) {
        if (!assetManager.isLoaded(name)) {
            throw new NotLoadedException("Texture '" + name + "' is not loaded");
        }

        return assetManager.get(name, Texture.class);
    }

    /**
     * Gets a loaded texture atlas from the asset manager
     * @param name file name of the atlas
     * @return loaded texture atlas
     */
    public TextureAtlas atlas(String name) {
        if (!assetManager.isLoaded(name)) {
            throw new NotLoadedException("TextureAtlas '" + name + "' is not loaded");
        }

        return assetManager.get(name, TextureAtlas.class);
    }

    /**
     * Gets a loaded bitmap font from the asset manager
     * @param name file name of the font
     * @return loaded bitmap font
     */
    public BitmapFont font(String name) {
        if (!assetManager.isLoaded(name)) {
            throw new NotLoadedException("Font '" + name + "' is not loaded");
        }

        return assetManager.get(name, BitmapFont.class);
    }

    /**
     * Gets a loaded music instance from the asset manager
     * @param name file name of the music instance
     * @return loaded music instance
     */
    public Music music(String name) {
        if (!assetManager.isLoaded(name)) {
            throw new NotLoadedException("Music '" + name + "' is not loaded");
        }

        return assetManager.get(name, Music.class);
    }

    /**
     * Gets a loaded sound instance from the asset manager
     * @param name file name of the sound instance
     * @return loaded sound instance
     */
    public Sound sound(String name) {
        if (!assetManager.isLoaded(name)) {
            throw new NotLoadedException("Sound '" + name + "' is not loaded");
        }

        return assetManager.get(name, Sound.class);
    }

    /**
     * Gets a loaded skin from the asset manager
     * @param name file name of the skin
     * @return loaded skin
     */
    public Skin skin(String name) {
        if (!assetManager.isLoaded(name)) {
            throw new NotLoadedException("Skin '" + name + "' is not loaded");
        }

        return assetManager.get(name, Skin.class);
    }

    /**
     * Unloads all specified assets
     * @param assets assets to unload
     */
    public void unload(String... assets) {
        for (String asset: assets) {
            unload(asset);
        }
    }

    /**
     * Unloads a single asset
     * @param name file name of the asset
     */
    public void unload(String name) {
        assetManager.unload(name);
    }

    /**
     * Gets rid of all loaded assets
     */
    public void clear() {
        assetManager.clear();
    }

    /**
     * Disposes of the asset manager
     */
    public void dispose() {
        assetManager.dispose();
    }
}
