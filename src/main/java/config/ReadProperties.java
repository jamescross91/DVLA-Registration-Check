package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
	private static Properties trProps = null;
		
	//Load static properties from the properties file
	private static void load() {
		try {
            InputStream inputStream = ReadProperties.class.getResourceAsStream("/reg.properties");

			trProps = new Properties();
			trProps.load(inputStream);
			
			inputStream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Go get the property we want
	public static String getProperty(String property) {
		if(trProps == null)
			load();
				
		return(trProps.getProperty(property));
	}

}
