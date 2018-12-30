# LibGDX_Project_Template
A project template with a clear structure, built-in screen handling logic, resource manager that manages all heavy assets like textures and sounds... Why? To avoid writing the same code over and over again when creating a new project.

The template already has a loading screen implementation, game screen implementation and a game-world instance. 

To get started just go to com.template.game.Main.java, create a screen instance that extends one of the screens from com.template.game.ui.abstractions package or create already implemented screens from com.template.game.ui.implementations package, and call showScreen() with the created instance. 

Main class handles screen switching and initializes all fields that the screen needs, so there's no need to worry about field initialization when extending one of the screens from com.template.game.ui.abstractions package.

# When extending one of the screens from com.template.game.ui.abstractions package, perform all initialization inside the overriden show() method (super.show() is not required) instead of class constructor.

Normally, one would create a new com.template.game.ui.implementations.LoadingScreen inside Main.create(), pass asset names that it should load, and once done loading, switch to the com.template.game.ui.implementations.GameScreen. Inside GameScreen, create user interface in setupUI() method. GameScreen overrides show() method and creates a new instance of the game world (which has draw() and update() methods), passing required values to it.

Basically, only worry about showing a loading screen (only if required), creating the ui and creating game logic inside com.template.game.logic package. If more screens are needed, like main menu, just extend a screen from the com.template.game.ui.abstractions package.

And rename the com.template.game package to something else.
