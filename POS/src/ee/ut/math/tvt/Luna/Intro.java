package ee.ut.math.tvt.Luna;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

public class Intro extends IntroUI {

	public static void main(String[] args) throws IOException {
		try {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createandshowGUI();
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}  
}
