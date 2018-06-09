package toolbox;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import entities.Camera;
import renderEngine.DisplayManager;


public class InputHandler {
	
	public static boolean paused = false;
	
	public static void testKeyboard() {
		
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				
				switch(Keyboard.getEventKey()) {
					case 1: 	if(!paused) Mouse.setGrabbed(false);		//ESC		
								else Mouse.setGrabbed(true);
								pauseGame();
								break;
					case 46: 	Camera.setAngleAroundPlayer(0);				//C
								break;
					case 87:	try {										//F11
									if(!DisplayManager.isFullscreen()) {
										Display.setDisplayMode(new DisplayMode(SysInf.SCREEN_WIDTH, SysInf.SCREEN_HEIGHT));
										DisplayManager.setFullscreen(true);
									} else {
										Display.setDisplayMode(new DisplayMode(900, 510));
										DisplayManager.setFullscreen(false);
									}
								} catch(LWJGLException e) {
									e.printStackTrace();
								}
								break;
					case 88:	System.out.println(Keyboard.KEY_F11);		//F12
								break;
				}
			}
		}
	}
	
	public static void pauseGame() {
		if(!paused) {
			paused = true;
		} else {
			paused = false;
		}
	}

}
