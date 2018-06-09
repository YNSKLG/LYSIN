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
		
		ModelData dataPlayer = OBJFileLoader.loadOBJ("char/fox");
		RawModel rawPlayer = loader.loadToVAO(dataPlayer.getVertices(), dataPlayer.getTextureCoords(),
				dataPlayer.getNormals(), dataPlayer.getIndices());
		TexturedModel playermodel = new TexturedModel(rawPlayer, new ModelTexture(loader.loadTexture("char/uvFox")));
		ModelTexture texturePlayer = playermodel.getTexture();
		texturePlayer.setReflectivity(0);
		texturePlayer.setShineDamper(0);
		texturePlayer.setHasTransparency(true);
		
		Player player = new Player(playermodel, new Vector3f(400,0.5f,-400),0,0,0,3);
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		entities.add(player);
		
		
		// ***** NEW MODEL LOADER *****
		
		System.out.println(LocalDateTime.now() + " *** Loading Models...");
		
		/*ModelData dataTree = OBJFileLoader.loadOBJ("structure/lamp");
		RawModel modelTree = loader.loadToVAO(dataTree.getVertices(), dataTree.getTextureCoords(), 
				dataTree.getNormals(), dataTree.getIndices());
		TexturedModel tree = new TexturedModel(modelTree, new ModelTexture(loader.loadTexture("structure/uvLamp")));
		ModelTexture textureTree = tree.getTexture();
		textureTree.setShineDamper(0);
		textureTree.setReflectivity(0);*/
		
						
		// ***** GUI *****
		
		List<GuiTexture> pauseGuis = new ArrayList<GuiTexture>();
		
		GuiTexture backToGame = new GuiTexture(loader.loadTexture("gui/BackToGame"), new Vector2f(0,0),
				new Vector2f(0.6f, 0.1f));
		
		pauseGuis.add(backToGame);
				
		
		// ***** ENTITY GENERATION *****
		
		System.out.println(LocalDateTime.now() + " *** Generating Entities...");
		
		/*Entity entityTree = new Entity(tree, new Vector3f(382, 
				terrain.getHeightOfTerrain(382, -336) + 0.1f,-336),0,140,0,3);*/
		
		//entities.add(entityTree);
		
		
		// ***** LIGHTS *****
		
		Light sun = new Light(new Vector3f(300,500,0), new Vector3f(1,1,1));
		Light lamp = new Light(new Vector3f(382, 
				10 + 6.1f,-336), new Vector3f(1,1,1));
		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);
		lights.add(lamp);
		
				
		// ***** GAME LOOP *****
		
		System.out.println(LocalDateTime.now() + " *** Starting Game...");
		
		while(!Display.isCloseRequested()) {
			
			InputHandler.testKeyboard();
				
			if(!InputHandler.paused) {
			
				camera.move();
				player.move();
			
				//---RENDERER---
			
				renderer.renderShadowMap(entities, sun);
			
				//renderer.processEntity(entityTree);
			
				renderer.processEntity(player);
			
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
