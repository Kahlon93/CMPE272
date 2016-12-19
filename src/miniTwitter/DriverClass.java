package miniTwitter;

//This is the main class that runs the program. It uses the Singleton Design 
// pattern to get an instance of the Admin Control Panel, and then displays it.
public class DriverClass {

	public static void main(String[] args) {
		AdminControlPanel.getInstance().run();
	}
}
