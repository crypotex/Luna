package ee.ut.math.tvt.Luna;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

/*
 * TODO: Add LogoUrl / logo and add softwareVersion
 */

public class IntroUI {
	
	private LinkedProperties applicationProperties = new LinkedProperties();
	private LinkedProperties versionProperties = new LinkedProperties();
	
	public IntroUI() {
		this.applicationProperties = getApplicationPropertiesFromFile();
		this.versionProperties = getVersionPropertiesFromFile();
	}
	
	public LinkedProperties getApplicationProperties() {
		return applicationProperties;
	}

	public LinkedProperties getVersionProperties() {
		return versionProperties;
	}

	private LinkedProperties getApplicationPropertiesFromFile(){
		LinkedProperties prop = new LinkedProperties();
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
	
	private LinkedProperties getVersionPropertiesFromFile(){
		LinkedProperties prop = new LinkedProperties();
		String propFileName = "version.properties";
		try {
			FileInputStream input = new FileInputStream(propFileName);
			prop.load(input);
			FileOutputStream output = new FileOutputStream(propFileName);
			//prop.setProperty("build.revision.number", 
					//Integer.toString(Integer.parseInt(prop.getProperty("build.revision.number"))+1) );
			//prop.setProperty("build.number", ( prop.getProperty("build.major.number") + "." +
					//prop.getProperty("build.minor.number") + "." + prop.getProperty("build.revision.number") ));
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
