package ee.ut.math.tvt.Luna;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class testing {

	public static void main(String[] args) throws IOException {
		
		IntroUI test = new IntroUI();
		Properties testpropapp = test.getApplicationProperties();
		for (String key : testpropapp.stringPropertyNames()) {
			String value = testpropapp.getProperty(key);
			System.out.println("The property in " + key + " is: " + value);
		}

		Properties testpropver = test.getVersionProperties();
		for (String key : testpropver.stringPropertyNames()) {
			String value = testpropver.getProperty(key);
			System.out.println("The property in " + key + " is: " + value);
		}

	}

}
