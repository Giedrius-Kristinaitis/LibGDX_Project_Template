package com.template.game.ui.implementations;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.template.game.logic.GameWorld;
import com.template.game.ui.abstractions.StagedScreen;

/**
 * Game screen. Displays game world and ui
 */
public class GameScreen extends StagedScreen {

    // instance of the game-world
    private GameWorld game;

    /**
     * Called when the screen needs to be initialized
     */
    @Override
    public void initialize() {
        super.initialize();
        game = new GameWorld(resources);
    }

    /**
     * Performs ui setup
     * @param stage stage to put ui widgets in
     */
    @Override
    public void setupUI(Stage stage) {

    }

    /**
     * Draws the game
     * @param delta time elapsed since last render
     * @param batch batch used to draw sprites to
     * @param cam world's camera
     */
    @Override
    public void draw(SpriteBatch batch, OrthographicCamera cam, float delta) {
        cam.update();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        game.draw(batch, delta);
        batch.end();
    }

    /**
     * Updates the game state
     * @param delta time elapsed since last update
     */
    @Override
    public void update(float delta) {
        game.update((OrthographicCamera) port.getCamera(), delta);
    }

    /**
     * Called when the size of the window changes
     * @param width new width of the window
     * @param height new height of the window
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        port.update(width, height, true);
    }

    /**
     * Gets rid of heavy resources like textures and so on...
     */
    @Override
    public void dispose() {
        super.dispose();

        game.unloadResources();
    }
}
