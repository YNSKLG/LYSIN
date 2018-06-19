package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;

public class MasterRenderer {
	
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000;
	
	private static final float RED = 1f;
	private static final float GREEN = 1f;
	private static final float BLUE = 1f;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
		
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	
	public MasterRenderer(Loader loader, Camera camera) {
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(List<Light> lights, Camera camera) {
		prepare();
		shader.start();
		shader.loadSkyColor(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
		
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void renderShadowMap(List<Entity> entityList, Light sun) {
		for(Entity entity:entityList) {
			processEntity(entity);
		}
		entities.clear();
	}
	
	public void cleanUp() {
		
		shader.cleanUp();
		
	}

	public void prepare() {
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);	//Sky Color
		
	}
	
	 private void createProjectionMatrix(){
	    	projectionMatrix = new Matrix4f();
			float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
			float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
			float x_scale = y_scale / aspectRatio;
			float frustum_length = FAR_PLANE - NEAR_PLANE;

			projectionMatrix.m00 = x_scale;
			projectionMatrix.m11 = y_scale;
			projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
			projectionMatrix.m23 = -1;
			projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
			projectionMatrix.m33 = 0;
	    }
	
}
