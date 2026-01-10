package utils;

import java.util.Properties;
import java.io.FileInputStream;


public class ConfigReader {

	private static Properties prop;
	
	public static void loadProperties() {
		prop = new Properties();
		
		try {
			// Load config.properties first
			
			FileInputStream fis = new FileInputStream("src/test/resources/config.properties" );
			
			prop.load(fis);
			
			String envFromJenkins = System.getProperty("env");
			String env = (envFromJenkins!=null && !envFromJenkins.isBlank())?envFromJenkins:prop.getProperty("env");
			
			env= env.trim().toLowerCase();
			
			
			
			// Read env After Loading 
			//String env = prop.getProperty("env").trim().toLowerCase();
			
			// Load env specific file
			FileInputStream envfis = new FileInputStream("src/test/resources/" + env + ".properties" );
			prop.load(envfis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		if(prop==null) {
			loadProperties();
		}
		return prop.getProperty(key);
	}
	
}
