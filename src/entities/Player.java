package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import renderEngine.DisplayManager;
import toolbox.InputHandler;

public class Player extends Entity {
	
	private static final float WALK_SPEED = 0.05f;
	private static final float GRAVITY = -9.80665f;
	private static final float JUMP_POWER = 4;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move() {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);		
		if(this.getPosition().y < 0) this.setPosition(new Vector3f(this.getPosition().x, 0, this.getPosition().z));
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
				
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			increasePosition(WALK_SPEED, 0, 0);
		} else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			increasePosition(-WALK_SPEED, 0, 0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)||Keyboard.isKeyDown(Keyboard.KEY_W)) {
			jump();
			if(this.getPosition().y==0) {
				isInAir = false;
			}
		}
		
		Controller joystick = null;
		
		for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if(c.getType() == Controller.Type.STICK) {
				joystick = c;
			}
		}
		
		joystick.poll();
		
		for(Component c : joystick.getComponents()) {
			
			if(c.getName().equals("x")) {
				currentSpeed = -(c.getPollData()*WALK_SPEED);
			}

			if(c.getName().equals("Top") && c.getPollData() == 1.0) {
				jump();
			} 

			if(c.getName().equals("Base 4") && c.getPollData() == 1.0) {
				InputHandler.pauseGame();
			}

			if(c.getName().equals("Base 3") && c.getPollData() == 1.0) {
				if(!DisplayManager.isFullscreen()) {
					DisplayManager.setFullscreen(true);
				} else {
					DisplayManager.setFullscreen(false);
				}
			}
		}
	}
}