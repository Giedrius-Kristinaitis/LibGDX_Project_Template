package com.template.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.template.game.resources.Resources;
import com.template.game.ui.abstractions.BasicScreen;
import com.template.game.ui.abstractions.ScreenSwitcher;
import com.template.game.ui.abstractions.ScreenWithInput;
import com.template.game.utils.Constants;

/**
 * Main class of the game
 */
public class Main extends Game implements ScreenSwitcher {

	// current visible ui
	private BasicScreen currentScreen;

	// game's resources
	private Resources resources;

	// viewport used by all screens
	private final Viewport port = new FillViewport(Constants.WIDTH, Constants.HEIGHT);

	/**
	 * Performs initialization. Called automatically by libGDX
	 */
	@Override
	public void create () {
		resources = new Resources();
	}

	/**
	 * Game loop render method. Called automatically by libGDX
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Disposes of all heavy resources. Called automatically by libGDX
	 */
	@Override
	public void dispose () {
		if (currentScreen != null) {
			currentScreen.dispose();
		}

		resources.dispose();
	}

	/**
	 * Sets the current screen to the specified one
	 * @param screen screen to be shown
	 */
	@Override
    	public void showScreen(BasicScreen screen) {
        	if (currentScreen != null) {
            		currentScreen.dispose();
        	}

        	this.currentScreen = screen;
        	this.currentScreen.setViewport(port);
        	this.currentScreen.setResources(resources);
        	this.currentScreen.initialize();
        	this.currentScreen.setScreenSwitcher(this);

        	this.setScreen(currentScreen);

        	// if the screen has input handling, register it
        	if (currentScreen instanceof ScreenWithInput) {
        	    	Gdx.input.setInputProcessor(((ScreenWithInput) currentScreen).getInputProcessor());
        	}
    	}
}
