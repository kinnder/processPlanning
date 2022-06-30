package application;

import org.slf4j.LoggerFactory;

public class Main {

	public static void main(String[] args) {
		try {
			Application application = new Application();
			application.start(args);
		} catch (Exception e) {
			LoggerFactory.getLogger(Main.class).error("", e);
		}
	}
}
