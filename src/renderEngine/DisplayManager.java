package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class DisplayManager {

	private static int WIDTH = 900;
	private static int HEIGHT = 510;
	
	private static int MAX_FPS = 120;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true)
				.withProfileCore(true);
		
		try {
			
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			Display.setTitle("LYSIN");
			Display.setResizable(true);
						
		} catch(LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
				
	}
	
	public static void updateDisplay() {
		
		Display.sync(MAX_FPS);
		Display.update();
		
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
}