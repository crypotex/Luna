package ee.ut.math.tvt.Luna;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;


public class IntroUI extends JFrame {
	JFrame frame; 
	
	protected final static Logger log = Logger.getLogger(Intro.class);
	private static LinkedProperties applicationProperties = new LinkedProperties();
	private static LinkedProperties versionProperties = new LinkedProperties();

	public IntroUI() {
		this.applicationProperties = getApplicationPropertiesFromFile();
		this.versionProperties = getVersionPropertiesFromFile();

		// Deal with the properties to hashmap shit
		LinkedProperties applicationProperties = getApplicationProperties();
		LinkedProperties versionProperties = getVersionProperties();

		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();

		for (Object name: applicationProperties.keySet()) {
			map.put(name.toString(), applicationProperties.getProperty(name));
		}

		// Start with the boring graphic shit
		
		GridBagLayout gridpane = new GridBagLayout(); //teeb niimoodi, et me saaksime key'd panna yhte veergu ja value'd teisse
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridpane);
		setName("Luna Properties");
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
					add(imgView, c);
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
				add(keyOutput, c);
				c.gridx = 1;
				add(keyValue, c);
				counter += 1;
			}
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
//		BasicConfigurator.configure();
		log.info("Window opened!");
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