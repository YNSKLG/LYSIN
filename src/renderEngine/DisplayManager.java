package renderEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static int WIDTH = 900;
	private static int HEIGHT = 510;
	
	static int MAX_FPS = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
	private static boolean fullscreen = false;
			
	private static ByteBuffer icon[] = new ByteBuffer[5];
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true)
				.withProfileCore(true);
		
		icon[0] = decode(readImage("res/icons/16.png"));
		icon[1] = decode(readImage("res/icons/32.png"));
		icon[2] = decode(readImage("res/icons/64.png"));
		icon[3] = decode(readImage("res/icons/128.png"));
		icon[4] = decode(readImage("res/icons/256.png"));
		
		try {
			
			if(!fullscreen) Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			else Display.setFullscreen(true);
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("[LYSIN]");
			Display.setIcon(icon);
			Display.setResizable(true);
			Mouse.setGrabbed(true);
		
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
		
	}
	
	public static void updateDisplay() {
		
		Display.sync(MAX_FPS);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
		
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {		
		Display.destroy();
	}
		
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	private static ByteBuffer decode(BufferedImage image) {
		
		int[] pixels = new int[image.getWidth()*image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()*image.getHeight()*4);
		
		for(int y=0;y<image.getHeight();y++) {
			for(int x=0;x<image.getWidth();x++) {
				int pixel = pixels[y*image.getWidth()+x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();
		return buffer;
	}
	
	private static BufferedImage readImage(String path) {
		
		try {
			return ImageIO.read(new File(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setFullscreen(boolean fullscreen) {
		DisplayManager.fullscreen = fullscreen;
	}
	
	public static boolean isFullscreen() {
		return DisplayManager.fullscreen;
	}
	
}
