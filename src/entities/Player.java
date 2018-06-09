package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.InputHandler;

public class Player extends Entity {
	
	private static final float RUN_SPEED = 10;
	private static final float SPRINT_SPEED = 25;
	private static final float GRAVITY = -25;
	private static final float JUMP_POWER = 11;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;
	private boolean sprinting = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		
		
	}
	
	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeight) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
		
	}
	
	public boolean isAtLocation(Vector3f location, float radius) {
		float distance = (float) Math.sqrt(Math.pow(location.x - this.getPosition().x, 2)+
				Math.pow(location.y - this.getPosition().y, 2)+Math.pow(location.z - 
				this.getPosition().z, 2));
		if(distance<=radius*3) return true;
		else return false;
	}
	
	private void jump() {
		if(!isInAir) {
		this.upwardsSpeed = JUMP_POWER;	
		isInAir = true;
		}
	}
	
	private void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				this.currentSpeed = SPRINT_SPEED;
			}
		} else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			increasePosition(0.25f, 0, 0);
		}
		
		if(!Mouse.isButtonDown(1)) {
			this.currentTurnSpeed = -(Mouse.getDX() * 25);
		} else {
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
		
		Controller joystick = null;
		
		for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if(c.getType() == Controller.Type.STICK) {
				joystick = c;
			}
		}
		
		joystick.poll();
		
		for(Component c : joystick.getComponents()) {
			
			if(c.getName().equals("y") && !sprinting) {
				currentSpeed = -(c.getPollData()*10);
			} else if(c.getName().equals("x")) {
				this.currentTurnSpeed = -(c.getPollData()*75);
			} else if(c.getName().equals("Top")) {
				if(c.getPollData() == 1.0) {
					jump();
				}
			} else if(c.getName().equals("Thumb 2")) {
				if(c.getPollData() == 1.0) {
					sprinting = true;
					currentSpeed = SPRINT_SPEED;
				} else if(c.getPollData() == 0.0) {
					sprinting = false;
				}
			} else if(c.getName().equals("Base 4")) {
				if(c.getPollData() == 1.0) {
					InputHandler.pauseGame();
				}
			} else if(c.getName().equals("Base 3")) {
				if(c.getPollData() == 1.0) {
					DisplayManager.setFullscreen(true);
				}
			}
			
		}
		
		
		/*if(Keyboard.isKeyDown(Keyboard.KEY_W) && isInAir) {
			this.currentSpeed = RUN_SPEED;
			this.currentTurnSpeed = 0;
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				this.currentSpeed = SPRINT_SPEED;
			}
		} else if(Keyboard.isKeyDown(Keyboard.KEY_S) && isInAir) {
			this.currentSpeed = 0;
			this.currentTurnSpeed = 0;
		}*/
	}

}
