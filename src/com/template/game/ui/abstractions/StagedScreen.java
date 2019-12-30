package com.template.game.ui.abstractions;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Screen class with a stage and input handling
 */
public abstract class StagedScreen extends ScreenWithInput {

    // stage used by this screen
    private Stage stage;

    /**
     * Performs required initialization
     */
    @Override
    public void initialize() {
        super.initialize();

        stage = new Stage(new ScreenViewport());
    }
   
    /**
     * Called when the screen is shown
     */
    @Override
    public void show() {
        super.show();
        setupUI(stage);
    }

    /**
     * Called when the screen should render itself
     * @param delta time elapsed since last render
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when the window is resized
     * @param width new width of the window
     * @param height new height of the window
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Performs required ui initialization
     */
    public abstract void setupUI(Stage stage);

    /**
     * Gets this screen's input processor
     * @return input processor
     */
    @Override
    public InputProcessor getInputProcessor() {
        InputMultiplexer input = new InputMultiplexer();

        input.addProcessor(stage);
        input.addProcessor(super.getInputProcessor());

        return input;
    }

    /**
     * Cleans up resources
     */
    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();
    }
}
