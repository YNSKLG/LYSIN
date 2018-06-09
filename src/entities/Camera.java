package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;

public class Camera {
	
	private float GROUND_DISTANCE = 9;
	
	public static float distanceFromPlayer = 20;
	private static float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0,1.5f,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private Player player;
	
	public Camera(Player player) {
		this.player = player;
	}
	
	public void move(Terrain terrain) {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance, terrain);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance, Terrain terrain) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + GROUND_DISTANCE;
		if(position.y <= terrain.getHeightOfTerrain(position.x, position.z) + 0.5f) 
			position.y = terrain.getHeightOfTerrain(position.x, position.z) + 0.5f;
		if(pitch >= 45) pitch = 45;
		if(pitch <= -35) pitch = -35;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.025f;
		distanceFromPlayer -= zoomLevel;
		if(distanceFromPlayer <= 6) distanceFromPlayer = 6;
		if(distanceFromPlayer >= 30) distanceFromPlayer = 30;
	}
	
	private void calculatePitch() {
		float pitchChange = Mouse.getDY() * 0.1f;
		pitch += pitchChange;						//change += to -= to invert Mouse-Y
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(1)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	public static void setAngleAroundPlayer(int value) {
		angleAroundPlayer = value;
	}
	
}
