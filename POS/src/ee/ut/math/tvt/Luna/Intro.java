package ee.ut.math.tvt.Luna;

import java.io.IOException;
import java.util.LinkedHashMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Intro extends Application {
		public static void main(String[] args) throws IOException {
			launch(args);
		}

		public void start(Stage primaryStage) {
			// Deal with the properties to hashmap shit
			IntroUI props = new IntroUI();
			LinkedProperties applicationProperties = props.getApplicationProperties();
			LinkedProperties versionProperties = props.getVersionProperties();
			
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
	        		Text keyOutput = new Text(key.replace('_', ' '));
	            	keskmine.add(keyOutput, 0, counter);
	        		Image img = new Image(map.get(key));
	        		ImageView imgView = new ImageView(img);
	                imgView.setFitWidth(300);
	                imgView.setPreserveRatio(true);
	                imgView.setSmooth(true);
	                imgView.setCache(true);
	        		keskmine.add(imgView, 1, counter);
	        		counter += 1;
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
		}

}
