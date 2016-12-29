package root.settings;

public class Settings {
	
	//TODO: make settings
	
	//TODO: override toString

	protected synchronized static byte[] getDefault() {
		Settings defaultS = new Settings();
		
		//TODO: create default settings
		
		return defaultS.toString().getBytes();
	}
	
	@Override
	public Settings clone(){
		Settings s = new Settings();
		
		//TODO: make real copy;
		
		return s;
	}

}
