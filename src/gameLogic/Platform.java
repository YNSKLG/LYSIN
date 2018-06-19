package gameLogic;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.Player;
import renderEngine.MasterRenderer;

public class Platform {
	
	private static Entity model;
	
	public Platform(Entity model, Player player, Vector3f pos) {
		
		Platform.model = model;
		
		model.setPosition(pos);
		Border.borderBottom(pos.getY(), player);
		
	}
	
	public static void render(MasterRenderer renderer) {
		renderer.processEntity(model);
	}
	
}
