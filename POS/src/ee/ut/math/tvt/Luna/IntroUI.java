package ee.ut.math.tvt.Luna;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import javafx.scene.layout.ColumnConstraints;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.log4j.BasicConfigurator;
/*
 * TODO: Add LogoUrl / logo and add softwareVersion
 */











import com.sun.prism.Image;

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

//		for (Object name: applicationProperties.keySet()) {
//			map.put(name.toString(), applicationProperties.getProperty(name));
//		}

		map.put("Team_name", "Luna");
		map.put("Team_leader", "Andre");
		map.put("Team_leader_email", "b02166@ut.ee");
		map.put("Team_members", "Andre T\u00e4ttar, Annika Laumets, Viktoria Plemakova, Henri R\u00e4stas");
		map.put("Software_version", "0.0.54");
		map.put("Logo_url", "http://www.upload.ee/image/4305361/LunaLogo.jpg");
		
		System.out.println(map);

		// Start with the boring graphic shit
		JFrame frame = new JFrame("Luna Properties");
		GridBagLayout gridpane = new GridBagLayout(); //teeb niimoodi, et me saaksime key'd panna �hte veergu ja value'd teisse
		GridBagConstraints c = new GridBagConstraints();
		frame.setLayout(gridpane);
		c.ipadx = 10;
		c.ipady = 10;
		
		int counter = 0;
		for (String key: map.keySet()) {
			if (key.equals("Logo_url")) {
				try {
					URL url = new URL(map.get(key));
					BufferedImage image = ImageIO.read(url); //loeb pildi URL'ist sisse
					JLabel imgView = new JLabel(new ImageIcon(image));
					c.gridx = 0;
					c.gridy = counter;
					c.gridwidth = 2;					
					frame.getContentPane().add(imgView, c);
					counter += 1;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} 
			else {
				JLabel keyOutput = new JLabel(key.replace('_', ' '));
				JLabel keyValue = new JLabel(map.get(key));
				c.gridx = 0;
				c.gridy = counter;
				frame.getContentPane().add(keyOutput, c);
				c.gridx = 1;
				frame.getContentPane().add(keyValue, c);
				counter += 1;
			}
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane();
		frame.setSize(600, 500);
		frame.setVisible(true);
//		BasicConfigurator.configure();
		log.info("Window opened!");
	}	
}