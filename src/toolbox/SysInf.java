package toolbox;

import java.awt.Toolkit;

public class SysInf {

	public static int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static final String OS = System.getProperty("os.name");
	public static final String OS_VERSION = System.getProperty("os.version");
	public static final String OS_DESKTOP = System.getProperty("sun.desktop");
	public static final String USER_LANGUAGE = System.getProperty("user.language");
	public static final String USER_NAME = System.getProperty("user.name");
	
	public static final String JRE_NAME = System.getProperty("java.runtime.name");
	public static final String JRE_VERSION = System.getProperty("java.vm.version");
	public static final String JAVA_VERSOPN = System.getProperty("java.version");	
	
	public static final String GAME_PATH = System.getProperty("user.dir");
	
}
