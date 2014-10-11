package ee.ut.math.tvt.Luna;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * TODO: Add LogoUrl / logo and add softwareVersion
 * java util properties
 */

public class IntroUI {
	private Properties applicationProperties = new Properties();
	private Properties versionProperties = new Properties();
	
	
	public IntroUI() {
		this.applicationProperties = getApplicationPropertiesFromFile();
		this.versionProperties = getVersionPropertiesFromFile();
	}
	
	public Properties getApplicationProperties() {
		return applicationProperties;
	}

	public Properties getVersionProperties() {
		return versionProperties;
	}

	private Properties getApplicationPropertiesFromFile(){
		Properties prop = new Properties();
		String propFileName = "application.properties";
		try {
			FileInputStream input = new FileInputStream(propFileName);
			prop.load(input);
			System.out.println("Applicationproperties have been read");
			input.close();
			return prop;
		} catch (IOException e) {
			throw new RuntimeException("Method : getApplicationProperties has failed ! ");
		}
	}
	
	private Properties getVersionPropertiesFromFile(){
		Properties prop = new Properties();
		String propFileName = "version.properties";
		try {
			FileInputStream input = new FileInputStream(propFileName);
			prop.load(input);
			FileOutputStream output = new FileOutputStream(propFileName);
			prop.setProperty("build.revision.number", 
					Integer.toString(Integer.parseInt(prop.getProperty("build.revision.number"))+1) );
			prop.setProperty("build.number", ( prop.getProperty("build.major.number") + "." +
					prop.getProperty("build.minor.number") + "." + prop.getProperty("build.revision.number") ));
			prop.store(output, null);
			input.close();
			output.close();
			System.out.println("Versionproperties have been read");
			return prop;
		} catch (IOException e) {
			throw new RuntimeException("Method : getVersionProperties has failed ! ");
		}
	}
	
}
