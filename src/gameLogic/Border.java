package gameLogic;

import org.lwjgl.util.vector.Vector3f;

import entities.Player;

public class Border {
	
	public static void borderLeft(float x, Player p) {
		if(p.getPosition().getX()>x) p.setPosition(new Vector3f(x, p.getPosition().getY(), -0.1f));
	}
	
	public static void borderRight(float x, Player p) {
		if(p.getPosition().getX()<x) p.setPosition(new Vector3f(x, p.getPosition().getY(), -0.1f));
	}
	
	public static void borderBottom(float y, Player p) {
		if(p.getPosition().getY()<y) p.setPosition(new Vector3f(p.getPosition().getX(), y, -0.1f));
	}
	
	public static void borderTop(float y, Player p) {
		if(p.getPosition().getY()>y) p.setPosition(new Vector3f(p.getPosition().getX(), y, -0.1f));
	}
	
}
