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

import java.util.HashMap;
import java.util.Map;

/**
 * Manages game's resources (Textures and so on...). Stores some assets in maps so that
 * AssetManager.get() doesn't have to be called every frame
 */
public class Resources {

    // asset manager to load, retrieve and dispose of assets
    private AssetManager assetManager;

    // all of the texture atlases loaded by the asset manager
    private Map<String, TextureAtlas> textureAtlases = new HashMap<String, TextureAtlas>();

    // all of the individual textures loaded by the asset manager
    private Map<String, Texture> textures = new HashMap<String, Texture>();

    // all of the fonts loaded by the asset manager
    private Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();

    // all of the music files loaded by the asset manager
    private Map<String, Music> music = new HashMap<String, Music>();

    // all of the short sound files loaded by the asset manager
    private Map<String, Sound> sounds = new HashMap<String, Sound>();

    // all of the tiled maps loaded by the asset manager
    private Map<String, TiledMap> maps = new HashMap<String, TiledMap>();

    // all of the skins loaded by the asset manager
    private Map<String, Skin> skins = new HashMap<String, Skin>();

    // have all of the queued assets been loaded
    private boolean finishedLoading = false;

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
        if (finishedLoading) {
            return true;
        }

        finishedLoading = assetManager.update();

        if (finishedLoading) {
            fillTextureAtlasMap();
            fillTextureMap();
            fillFontMap();
            fillMusicMap();
            fillSoundMap();
            fillMapsMap();
            fillSkinsMap();
        }

        return finishedLoading;
    }

    /**
     * Fills the tiled maps map with assets
     */
    private void fillMapsMap() {
        for (String map: maps.keySet()) {
            maps.put(map, assetManager.get(map, TiledMap.class));
        }
    }

    /**
     * Fills the skins map with assets
     */
    private void fillSkinsMap() {
        for (String skin: skins.keySet()) {
            skins.put(skin, assetManager.get(skin, Skin.class));
        }
    }

    /**
     * Fills the fonts map with assets
     */
    private void fillFontMap() {
        for (String font: fonts.keySet()) {
            fonts.put(font, assetManager.get(font, BitmapFont.class));
        }
    }

    /**
     * Fills the music map with assets
     */
    private void fillMusicMap() {
        for (String music: this.music.keySet()) {
            this.music.put(music, assetManager.get(music, Music.class));
        }
    }

    /**
     * Fills the sounds map with assets
     */
    private void fillSoundMap() {
        for (String sound: sounds.keySet()) {
            sounds.put(sound, assetManager.get(sound, Sound.class));
        }
    }

    /**
     * Fills the textureAtlases map with assets
     */
    private void fillTextureAtlasMap() {
        for (String textureAtlas: textureAtlases.keySet()) {
            textureAtlases.put(textureAtlas, assetManager.get(textureAtlas, TextureAtlas.class));
        }
    }

    /**
     * Fills textures map with assets
     */
    private void fillTextureMap() {
        for (String texture: textures.keySet()) {
            textures.put(texture, assetManager.get(texture, Texture.class));
        }
    }

    /**
     * Forces to load all queued assets. Blocks calling thread
     */
    public void finishLoading() {
        assetManager.finishLoading();

        fillTextureAtlasMap();
        fillTextureMap();
        fillFontMap();
        fillMapsMap();
        fillMusicMap();
        fillSkinsMap();
        fillSoundMap();
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
     *
     * @param assets map containing asset file names and types
     */
    public void load(Map<String, Class> assets) {
        for (Map.Entry<String, Class> asset: assets.entrySet()) {
            load(asset.getKey(), asset.getValue());
        }
    }

    /**
     * Loads a single asset
     *
     * @param name file name of the asset
     * @param type class type of the asset
     */
    public void load(String name, Class type) {
        assetManager.load(name, type);

        if (type == TextureAtlas.class) {
            textureAtlases.put(name, null);
        } else if (type == Texture.class) {
            textures.put(name, null);
        } else if (type == BitmapFont.class) {
            fonts.put(name, null);
        } else if (type == Music.class) {
            music.put(name, null);
        } else if (type == Sound.class) {
            sounds.put(name, null);
        } else if (type == Skin.class) {
            skins.put(name, null);
        }
    }

    /**
     * Loads specified tiled maps
     *
     * @param maps maps to load
     */
    public void loadMaps(String... maps) {
        for (String map: maps) {
            loadMap(map);
        }
    }

    /**
     * Loads a tiled map
     *
     * @param name file name of the map
     */
    public void loadMap(String name) {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(name, TiledMap.class);

        maps.put(name, null);
    }

    /**
     * Gets a loaded tiled map from the asset manager
     * @param name file name of the map
     * @return
     */
    public TiledMap map(String name) {
        if (!maps.containsKey(name)) {
            throw new NotLoadedException("Map '" + name + "' is not loaded");
        }

        return maps.get(name);
    }

    /**
     * Gets a loaded texture from the asset manager
     * @param name file name of the texture
     * @return loaded texture
     */
    public Texture texture(String name) {
        if (!textures.containsKey(name)) {
            throw new NotLoadedException("Texture '" + name + "' is not loaded");
        }

        return textures.get(name);
    }

    /**
     * Gets a loaded texture atlas from the asset manager
     * @param name file name of the atlas
     * @return loaded texture atlas
     */
    public TextureAtlas atlas(String name) {
        if (!textureAtlases.containsKey(name)) {
            throw new NotLoadedException("TextureAtlas '" + name + "' is not loaded");
        }

        return textureAtlases.get(name);
    }

    /**
     * Gets a loaded bitmap font from the asset manager
     * @param name file name of the font
     * @return loaded bitmap font
     */
    public BitmapFont font(String name) {
        if (!fonts.containsKey(name)) {
            throw new NotLoadedException("Font '" + name + "' is not loaded");
        }

        return fonts.get(name);
    }

    /**
     * Gets a loaded music instance from the asset manager
     * @param name file name of the music instance
     * @return loaded music instance
     */
    public Music music(String name) {
        if (!music.containsKey(name)) {
            throw new NotLoadedException("Music '" + name + "' is not loaded");
        }

        return music.get(name);
    }

    /**
     * Gets a loaded sound instance from the asset manager
     *
     * @param name file name of the sound instance
     * @return loaded sound instance
     */
    public Sound sound(String name) {
        if (!sounds.containsKey(name)) {
            throw new NotLoadedException("Sound '" + name + "' is not loaded");
        }

        return sounds.get(name);
    }

    /**
     * Gets a loaded skin from the asset manager
     *
     * @param name file name of the skin
     * @return loaded skin
     */
    public Skin skin(String name) {
        if (!skins.containsKey(name)) {
            throw new NotLoadedException("Skin '" + name + "' is not loaded");
        }

        return skins.get(name);
    }

    /**
     * Unloads all specified assets
     *
     * @param assets assets to unload
     * @param classes class types of the same index assets
     */
    public void unload(String[] assets, Class[] classes) {
        for (int i = 0; i < assets.length; i++) {
            unload(assets[i], classes[i]);
        }
    }

    /**
     * Unloads a single asset
     *
     * @param name file name of the asset
     * @param clazz class type of the asset
     */
    public void unload(String name, Class clazz) {
        assetManager.unload(name);

        if (clazz == TextureAtlas.class) {
            textureAtlases.remove(name);
        } else if (clazz == Texture.class) {
            textures.remove(name);
        } else if (clazz == BitmapFont.class) {
            fonts.remove(name);
        } else if (clazz == Music.class) {
            music.remove(name);
        } else if (clazz == Sound.class) {
            sounds.remove(name);
        } else if (clazz == TiledMap.class) {
            maps.remove(name);
        } else if (clazz == Skin.class) {
            skins.remove(name);
        }
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
