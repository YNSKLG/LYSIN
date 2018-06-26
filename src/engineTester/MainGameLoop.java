package engineTester;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Pill;
import entities.Player;
import gameLogic.Border;
import guis.GuiRenderer;
import guis.GuiTexture;
import level.Level;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;
import toolbox.InputHandler;

public class MainGameLoop {
	
	private static Player player;
	
	public static void main(String[] args) {
		
		System.out.println(LocalDateTime.now() + " *** Loading Game...");
		
		boolean isMenue = true;
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		List<Entity> entities = new ArrayList<Entity>();
		
		ModelData dataLysander = OBJFileLoader.loadOBJ("char/lysander");
		RawModel rawLysander = loader.loadToVAO(dataLysander.getVertices(), dataLysander.getTextureCoords(),
				dataLysander.getNormals(), dataLysander.getIndices());
		TexturedModel lysander = new TexturedModel(rawLysander, new ModelTexture(loader.loadTexture("char/lysander")));
		ModelTexture textureLysander = lysander.getTexture();
		textureLysander.setHasTransparency(true);
		
		ModelData dataMabel = OBJFileLoader.loadOBJ("char/mabel");
		RawModel rawMabel = loader.loadToVAO(dataMabel.getVertices(), dataMabel.getTextureCoords(),
				dataMabel.getNormals(), dataMabel.getIndices());
		TexturedModel mabel = new TexturedModel(rawMabel, new ModelTexture(loader.loadTexture("char/mabel")));
		ModelTexture textureMabel = mabel.getTexture();
		textureMabel.setHasTransparency(true);
		
		player = new Player(lysander, new Vector3f(0,0,-0.1f),0,0,0,0.4f);
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		entities.add(player);
		
		
		// ***** NEW MODEL LOADER *****
		
		System.out.println(LocalDateTime.now() + " *** Loading Models...");
		
		ModelData dataBackground = OBJFileLoader.loadOBJ("structure/background");
		RawModel modelBackground = loader.loadToVAO(dataBackground.getVertices(), dataBackground.getTextureCoords(), 
				dataBackground.getNormals(), dataBackground.getIndices());
		TexturedModel background = new TexturedModel(modelBackground, new ModelTexture(loader.loadTexture("enviroment/backgrounds/bg1")));
		
		ModelData dataBackgroundf = OBJFileLoader.loadOBJ("structure/background");
		RawModel modelBackgroundf = loader.loadToVAO(dataBackgroundf.getVertices(), dataBackgroundf.getTextureCoords(), 
				dataBackgroundf.getNormals(), dataBackgroundf.getIndices());
		TexturedModel backgroundf = new TexturedModel(modelBackgroundf, new ModelTexture(loader.loadTexture("enviroment/backgrounds/bg1")));
		
						
		// ***** GUI *****
		
		List<GuiTexture> pauseGuis = new ArrayList<GuiTexture>();
		List<GuiTexture> menu = new ArrayList<GuiTexture>();
		
		GuiTexture backToGame = new GuiTexture(loader.loadTexture("gui/BackToGame"), new Vector2f(0,0),
				new Vector2f(0.6f, 0.1f));
		
		pauseGuis.add(backToGame);
		
		GuiTexture startMenu = new GuiTexture(loader.loadTexture("gui/BackToGame"), new Vector2f(0,0),
				new Vector2f(0.6f, 0.1f));
		
		GuiTexture startGame = new GuiTexture(loader.loadTexture("gui/BackToGame"), new Vector2f(0,0.5f),
				new Vector2f(0.6f, 0.1f));
		menu.add(startGame);
		menu.add(startMenu);
		
		// ***** ENTITY GENERATION *****
		
		System.out.println(LocalDateTime.now() + " *** Generating Entities...");
		
		Entity entityBackground0 = new Entity(background, new Vector3f(0,2f,0),0,180,0,3);
		Entity entityBackground1 = new Entity(backgroundf, new Vector3f(-9.6f,2f,0),0,180,0,3);
		Entity entityBackground2 = new Entity(background, new Vector3f(-19.2f,2f,0),0,180,0,3);
		Entity entityBackground3 = new Entity(backgroundf, new Vector3f(-28.8f,2f,0),0,180,0,3);
		
		Entity entityMabel = new Entity(mabel, new Vector3f(-4.5f,0,-0.1f),0,0,0,0.6f);
		
		Entity entityPill = new Entity(mabel, new Vector3f(0,0,0),0,0,0,0.4f);
		
		entities.add(entityBackground0);
		entities.add(entityBackground1);
		entities.add(entityBackground2);
		entities.add(entityBackground3);
		Pill pill = new Pill(Pill.BLUE, new Vector2f(-10,0), entityPill);
		
		entities.add(pill.getEntity());
		
		entities.add(entityMabel);
		
		// ***** LIGHTS *****

		List<Light> lights = new ArrayList<Light>();
		
		Light sun = new Light(new Vector3f(300,500,0), new Vector3f(1,1,1));
		
		lights.add(sun);
		
				
		// ***** GAME LOOP *****
		
		System.out.println(LocalDateTime.now() + " *** Starting Game...");
		
		while(!Display.isCloseRequested()) {
			InputHandler.testKeyboard();
			
			while(isMenue) {
				guiRenderer.render(menu);
				Mouse.setGrabbed(false);
				Display.update();
				if(Mouse.isButtonDown(0)) {
					Mouse.setGrabbed(true);
					isMenue = false;
					break;
				}
			}	
			
			if(!InputHandler.paused) {
				
				System.out.println(player.getPosition().getX() + " " + player.getPosition().getY());
				
				camera.move();
				player.move();
				
				Border.setBorderLeft(1, player);
				Border.setBorderRight(-25, player);
				
				if(player.isAtLocation(new Vector3f(-10,0,-0.1f), 0.5f)) {
					entities.remove(entityPill);
					pill.pickedUp();
				}
			
				if(Keyboard.getEventKeyState()) {
					if(Keyboard.getEventKey() == Keyboard.KEY_Q && pill.isPickedUp() && !pill.isDumped()) {
						pill.enable();
					}
				}

				if(System.currentTimeMillis()-pill.getEnabledTime()>=5000) {
					pill.disable();
				}
				
			
				//---RENDERER---
			
				for(Entity e : entities) {
					renderer.processEntity(e);
				}
			
				renderer.render(lights, camera);
						
			} else {
			
				guiRenderer.render(pauseGuis);
				
				if((Mouse.getX()>=176 && Mouse.getX()<=543) && ((Mouse.getY()>=246 && Mouse.getY()<=285)) && (Mouse.isButtonDown(0))) {
					InputHandler.paused = false;
					Mouse.setGrabbed(true);

				}
			
				
			}
				
			DisplayManager.updateDisplay();
		}
		
		
		// ***** CLEANUP *****
		
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
				
	}
	
	public static Player getPlayer() {
		return player;
	}
}
