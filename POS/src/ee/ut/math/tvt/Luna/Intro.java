package ee.ut.math.tvt.Luna;

import java.io.IOException;
import java.util.LinkedHashMap;

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

import org.apache.log4j.Logger;

public class Intro extends IntroUI {

	public static void main(String[] args) throws IOException {
		try {
			launch(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}  
}
