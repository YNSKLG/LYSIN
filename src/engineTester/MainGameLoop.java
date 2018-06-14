package engineTester;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
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
	
	public static void main(String[] args) {
		
		System.out.println(LocalDateTime.now() + " *** Loading Game...");
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		List<Entity> entities = new ArrayList<Entity>();
		
		ModelData dataPlayer = OBJFileLoader.loadOBJ("char/char1");
		RawModel rawPlayer = loader.loadToVAO(dataPlayer.getVertices(), dataPlayer.getTextureCoords(),
				dataPlayer.getNormals(), dataPlayer.getIndices());
		TexturedModel playermodel = new TexturedModel(rawPlayer, new ModelTexture(loader.loadTexture("char/char1")));
		ModelTexture texturePlayer = playermodel.getTexture();
		texturePlayer.setHasTransparency(true);
		
		Player player = new Player(playermodel, new Vector3f(0,0,-0.1f),0,0,0,0.3f);
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
		
						
		// ***** GUI *****
		
		List<GuiTexture> pauseGuis = new ArrayList<GuiTexture>();
		
		GuiTexture backToGame = new GuiTexture(loader.loadTexture("gui/BackToGame"), new Vector2f(0,0),
				new Vector2f(0.6f, 0.1f));
		
		pauseGuis.add(backToGame);
				
		
		// ***** ENTITY GENERATION *****
		
		System.out.println(LocalDateTime.now() + " *** Generating Entities...");
		
		Entity entityBackground0 = new Entity(background, new Vector3f(0,0.8f,0),0,180,0,3);
		Entity entityBackground1 = new Entity(background, new Vector3f(-9.6f,0.8f,0),0,180,0,3);
		Entity entityBackground2 = new Entity(background, new Vector3f(-19.2f,0.8f,0),0,180,0,3);
		Entity entityBackground3 = new Entity(background, new Vector3f(-28.8f,0.8f,0),0,180,0,3);
		
		entities.add(entityBackground0);
		entities.add(entityBackground1);
		entities.add(entityBackground2);
		entities.add(entityBackground3);
				
		
		// ***** LIGHTS *****

		List<Light> lights = new ArrayList<Light>();
		
		Light sun = new Light(new Vector3f(300,500,0), new Vector3f(1,1,1));
		
		lights.add(sun);
		
				
		// ***** GAME LOOP *****
		
		System.out.println(LocalDateTime.now() + " *** Starting Game...");
		
		while(!Display.isCloseRequested()) {
			
			InputHandler.testKeyboard();
				
			if(!InputHandler.paused) {
				
				camera.move();
				player.move();
			
				//---RENDERER---
			
				for(Entity e : entities) {
					renderer.processEntity(e);
				}
			
				renderer.render(lights, camera);
						
			} else {
			
				guiRenderer.render(pauseGuis);
				
			}
				
			DisplayManager.updateDisplay();
		}
		
		
		// ***** CLEANUP *****
		
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}
}
