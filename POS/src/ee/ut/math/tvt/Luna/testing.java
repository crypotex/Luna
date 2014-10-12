package ee.ut.math.tvt.Luna;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class testing extends Application{

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	public void start(Stage primaryStage) {
		// Deal with the properties to hashmap shit
		IntroUI props = new IntroUI();
		LinkedProperties applicationProperties = props.getApplicationProperties();
		LinkedProperties versionProperties = props.getVersionProperties();
		
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		
        for (final String name: applicationProperties.stringPropertyNames())
            map.put(name, applicationProperties.getProperty(name));
       
		
		// Start with the boring graphic shit
        BorderPane juur = new BorderPane();
        
        GridPane keskmine = new GridPane();
        keskmine.setHgap(20);
        keskmine.setVgap(10);
        keskmine.setPadding(new Insets(0, 10, 0, 10));
        
        int counter = 0;
        System.out.println(map);
        for (String key: map.keySet()) {
        	if (key.equals("LogoUrl")) {
        		Image pilt = new Image(map.get(key), 100, 0, false, false);
        		ImageView iv1 = new ImageView();
                iv1.setImage(pilt);
        		keskmine.add(iv1, 1, counter);
        	} else {
        		Text voti = new Text(key.replace('_', ' '));
            	Text v‰‰rtus = new Text(map.get(key));
            	keskmine.add(voti, 0, counter);
            	keskmine.add(v‰‰rtus, 1, counter);
            	counter += 1;
        	}
        	
        	//tˆˆtav kood
//        	Text voti = new Text(key.replace('_', ' '));
//        	Text v‰‰rtus = new Text(map.get(key));
//        	keskmine.add(voti, 0, counter);
//        	keskmine.add(v‰‰rtus, 1, counter);
//        	counter += 1;
		}
        juur.setCenter(keskmine);
        
        Scene appScene = new Scene(juur,500,500); 
        primaryStage.setTitle("Luna Properties");
        primaryStage.setScene(appScene);
        primaryStage.show();
	}

}
