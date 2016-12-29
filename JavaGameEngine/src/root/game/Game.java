package root.game;

import root.render.engine.DisplayManager;

public class Game {

	public void mainGame() {
		DisplayManager.createDisplay();
		
		while(!DisplayManager.isCloseRequested()){
			
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.closeDisplay();
	}

}
