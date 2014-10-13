package ee.ut.math.tvt.Luna;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
/*
 * TODO: Add LogoUrl / logo and add softwareVersion
 */


public class IntroUI extends Application {
	
	private final Logger log = Logger.getLogger(Intro.class);
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

	public void start(Stage primaryStage) {

		// Deal with the properties to hashmap shit
		LinkedProperties applicationProperties = getApplicationProperties();
		LinkedProperties versionProperties = getVersionProperties();

		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();

		for (Object name: applicationProperties.keySet()) {
			map.put(name.toString(), applicationProperties.getProperty(name));
		}



		// Start with the boring graphic shit
		BorderPane juur = new BorderPane();

		GridPane keskmine = new GridPane();
		keskmine.setHgap(20);
		keskmine.setVgap(10);
		keskmine.setPadding(new Insets(0, 10, 0, 10));

		int counter = 0;
		for (String key: map.keySet()) {
			if (key.equals("Logo_url")) {
				try {
					Image img = new Image(map.get(key));
					ImageView imgView = new ImageView(img);
					imgView.setFitWidth(300);
					imgView.setPreserveRatio(true);
					imgView.setSmooth(true);
					imgView.setCache(true);
					keskmine.add(imgView, 0, counter, 2, 1);
					keskmine.setAlignment(Pos.CENTER);
					GridPane.setHalignment(imgView, HPos.CENTER); //paneb logo keskele
					counter += 1;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} 
			else {
				Text keyOutput = new Text(key.replace('_', ' '));
				Text keyValue = new Text(map.get(key));
				keskmine.add(keyOutput, 0, counter);
				keskmine.add(keyValue, 1, counter);
				counter += 1;
			}
		}

		juur.setCenter(keskmine);

		Scene appScene = new Scene(juur,500,500); 
		primaryStage.setTitle("Luna Properties");
		primaryStage.setScene(appScene);
		primaryStage.show();
//		BasicConfigurator.configure();
		log.info("Window opened!");
	}	
}