package ee.ut.math.tvt.Luna;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;
/*
 * TODO: Add LogoUrl / logo and add softwareVersion
 */



import sun.management.jdp.JdpGenericPacket;


public class IntroUI extends JFrame {
	
	private final static Logger log = Logger.getLogger(Intro.class);
	private static LinkedProperties applicationProperties = new LinkedProperties();
	private static LinkedProperties versionProperties = new LinkedProperties();

	public IntroUI() {
		this.applicationProperties = getApplicationPropertiesFromFile();
		this.versionProperties = getVersionPropertiesFromFile();
	}


	public static LinkedProperties getApplicationProperties() {
		return applicationProperties;
	}

	public static LinkedProperties getVersionProperties() {
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

	static void createandshowGUI() {

		// Deal with the properties to hashmap shit
		LinkedProperties applicationProperties = getApplicationProperties();
		LinkedProperties versionProperties = getVersionProperties();

		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();

		for (Object name: applicationProperties.keySet()) {
			map.put(name.toString(), applicationProperties.getProperty(name));
		}


		// Start with the boring graphic shit
		JFrame frame = new JFrame("Luna Properties");
		frame.getContentPane().add(new JLabel("Hello, world!"), BorderLayout.CENTER);
		
		for (String key: map.keySet()) {
			if (key.equals("Logo_url")) {
				try {
					ImageIcon img = new ImageIcon(map.get(key));
					JLabel imgView = new JLabel(img);
					frame.getContentPane().add(imgView);
					/*keskmine.add(imgView, 0, counter, 2, 1);
					keskmine.setAlignment(Pos.CENTER);
					GridPane.setHalignment(imgView, HPos.CENTER); //paneb logo keskele*/
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} 
			else {
				JLabel keyOutput = new JLabel(key.replace('_', ' '));
				JLabel keyValue = new JLabel(map.get(key));
				frame.getContentPane().add(keyOutput);
				frame.getContentPane().add(keyValue);
			}
		}

		//juur.set(keskmine);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();
		frame.setSize(500, 500);
		frame.setVisible(true);
//		BasicConfigurator.configure();
		log.info("Window opened!");
	}	
}