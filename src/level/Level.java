package level;

import org.lwjgl.util.vector.Vector3f;

import entities.Player;
import gameLogic.Border;

public class Level {

	public static void loadLevel1(Player p) {
		
		if(p.isAtLocation(new Vector3f(-4.85f, 1.0f, -0.1f), 0.9f)) {
			Border.borderBottom(1, p);
			p.setFloor(1);
		} else {
			p.setFloor(0);
		}
		
	}
	
}
