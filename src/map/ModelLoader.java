package map;

import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class ModelLoader {
	
	public static void loadModel(Loader loader, RawModel model, String modelLocation, String textureLocation,
			TexturedModel staticModel, ModelTexture texture, int damper, int reflectivity){
		
		model = OBJLoader.loadObjModel(modelLocation, loader);
		staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture(textureLocation)));
		texture = staticModel.getTexture();
		texture.setShineDamper(damper);
		texture.setReflectivity(reflectivity);
	}
	
}
