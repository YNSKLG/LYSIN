package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import renderEngine.DisplayManager;

public class Player extends Entity {
	
	private static final float WALK_SPEED = 0.05f;
	private static final float GRAVITY = -9.80665f;
	private static final float JUMP_POWER = 4.5f;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private float floor = 0;
	
	private float speedModifier = 1;
	private float jumpModifier = 1;
	
	private int highscore, meterRun, timePlayed, avatar, xp, hp, star, death, coin; 
	private String name;
	
	private boolean isInAir = false;
	
	public static boolean facesRight = true;
	
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
		if(distance<=radius) return true;
		else return false;
	}
	
	private void jump() {
		if(!isInAir) {
			this.upwardsSpeed = JUMP_POWER+jumpModifier;	
			isInAir = true;
		}
	}
	
	private void checkInputs() {
				
		if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			increasePosition(WALK_SPEED * speedModifier, 0, 0);
			setRotY(180);
			facesRight = false;
			Camera.setAngleAroundPlayer(180);
		} else if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			increasePosition(-WALK_SPEED * speedModifier, 0, 0);
			setRotY(0);
			facesRight=true;
			Camera.setAngleAroundPlayer(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)||Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			jump();
			if(this.getPosition().y == floor) isInAir = false;
		}
		
		Controller joystick = null;
		
		for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if(c.getType() == Controller.Type.STICK) {
				joystick = c;
			}
		}
		
		if(joystick != null) {
			joystick.poll();
			
			for(Component c : joystick.getComponents()) {
				
				if(c.getName().equals("x")) {
					increasePosition(-WALK_SPEED*c.getPollData()*speedModifier, 0, 0);
					if(c.getPollData() > 0) {
						setRotY(0);
						Camera.setAngleAroundPlayer(0);
					} else if(c.getPollData() < 0) {
						setRotY(180);
						Camera.setAngleAroundPlayer(180);
					}
				}
	
				if(c.getName().equals("Thumb 2") && c.getPollData() == 1.0) {
					jump();
					if(this.getPosition().y == 0) isInAir = false;
				} 
	
				/*if(c.getName().equals("Base 4") && c.getPollData() == 1.0) {
					InputHandler.pauseGame();
				}*/
			}
		}
	}
	
	public void setJumpModifier(float modifier) {
		jumpModifier = modifier;
	}
	
	public void setSpeedModifier(float modifier) {
		speedModifier = modifier;
	}
		
	public void setFloor(float floor) {
		this.floor = floor;
	}
	
	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

	public int getMeterRun() {
		return meterRun;
	}

	public void setMeterRun(int meterRun) {
		this.meterRun = meterRun;
	}

	public int getTimePlayed() {
		return timePlayed;
	}

	public void setTimePlayed(int timePlayed) {
		this.timePlayed = timePlayed;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getDeath() {
		return death;
	}

	public void setDeath(int death) {
		this.death = death;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
